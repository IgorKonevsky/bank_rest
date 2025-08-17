package com.example.bank_rest.config;

import com.example.bank_rest.security.JwtFilter;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;

/**
 * Класс конфигурации Spring Security.
 * <p>
 * Этот класс настраивает механизмы безопасности для REST API, включая
 * управление доступом к конечным точкам, аутентификацию на основе JWT,
 * кодирование паролей и CORS.
 * <p>
 * Аннотирован {@link Configuration}, чтобы Spring обнаружил его как класс конфигурации.
 * Аннотирован {@link EnableWebSecurity} для включения поддержки веб-безопасности Spring.
 * Аннотирован {@link RequiredArgsConstructor} для автоматической инъекции зависимостей.
 */
@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtFilter jwtFilter;
    private final UserDetailsService userDetailsService;

    /**
     * Конфигурирует цепочку фильтров безопасности (SecurityFilterChain).
     * <p>
     *
     * @param http Объект {@link HttpSecurity}, используемый для настройки безопасности.
     * @return {@link SecurityFilterChain} настроенную цепочку фильтров.
     * @throws Exception если произошла ошибка при настройке.
     */
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .httpBasic(AbstractHttpConfigurer::disable)
                .cors(Customizer.withDefaults())
                .authorizeHttpRequests(requests -> requests
                        .requestMatchers("/api/v1/auth").permitAll()
                        .requestMatchers("/api/v1/signup").permitAll()
                        .requestMatchers(
                                "/v3/api-docs/**",
                                "/swagger-ui/**",
                                "/swagger-ui.html",
                                "/webjars/**"
                        ).permitAll()
                        .requestMatchers("/api/v1/customer/**").hasRole("CUSTOMER")
                        .requestMatchers("/api/v1/admin/**").hasRole("ADMIN")
                        .anyRequest().authenticated()

                )
                .userDetailsService(userDetailsService)
                .exceptionHandling(exceptionHandling -> exceptionHandling
                        .authenticationEntryPoint((request, response, authException) ->
                                response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized")))
                .sessionManagement(sessionManagement -> sessionManagement
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        http.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }

    /**
     * Создает бин {@link PasswordEncoder} для кодирования паролей.
     * <p>
     * В этом приложении используется {@link BCryptPasswordEncoder}, который является
     * надежным алгоритмом для хеширования паролей.
     *
     * @return Экземпляр {@link BCryptPasswordEncoder}.
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * Создает бин {@link AuthenticationManager} для управления аутентификацией.
     * <p>
     * Настраивает {@link AuthenticationManagerBuilder}, чтобы использовать
     * {@link UserDetailsService} для загрузки пользовательских данных и
     * {@link PasswordEncoder} для проверки паролей.
     *
     * @param http Объект {@link HttpSecurity}.
     * @return Экземпляр {@link AuthenticationManager}.
     * @throws Exception если произошла ошибка при настройке.
     */
    @Bean
    public AuthenticationManager authenticationManagerBean(HttpSecurity http) throws Exception {
        AuthenticationManagerBuilder authenticationManagerBuilder = http.getSharedObject(AuthenticationManagerBuilder.class);
        authenticationManagerBuilder
                .userDetailsService(userDetailsService)
                .passwordEncoder(passwordEncoder());

        return authenticationManagerBuilder.build();
    }


    /**
     * Создает бин {@link CorsConfigurationSource} для настройки CORS.
     * <p>
     * Настраивает политики CORS, разрешая доступ с определенных источников
     * (`http://localhost:8080`), а также разрешая определенные HTTP-методы и заголовки.
     *
     * @return Экземпляр {@link CorsConfigurationSource}.
     */
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Arrays.asList("http://localhost:8080"));
        configuration.setAllowedMethods(Arrays.asList(
                HttpMethod.GET.name(),
                HttpMethod.POST.name(),
                HttpMethod.PUT.name(),
                HttpMethod.DELETE.name(),
                HttpMethod.OPTIONS.name()
        ));
        configuration.setAllowedHeaders(Arrays.asList(
                "Authorization",
                "Content-Type",
                "Accept",
                "X-Requested-With"
        ));
        configuration.setAllowCredentials(true);
        configuration.setMaxAge(3600L);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}