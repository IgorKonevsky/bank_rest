package com.example.bank_rest.security;

import com.auth0.jwt.exceptions.JWTVerificationException;
import com.example.bank_rest.exception.handler.ErrorResponse;
import com.example.bank_rest.repository.InvalidTokenRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.time.LocalDateTime;

/**
 * Фильтр для обработки JWT-токенов в каждом HTTP-запросе.
 * <p>
 * Этот фильтр перехватывает все входящие запросы, проверяет наличие и валидность
 * JWT-токена в заголовке "Authorization". Если токен действителен и не аннулирован,
 * он аутентифицирует пользователя и устанавливает его в контекст безопасности
 * Spring Security. Фильтр пропускает запросы к публичным конечным точкам,
 * таким как аутентификация и документация.
 */
@Component
@RequiredArgsConstructor
@Slf4j
public class JwtFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;
    private final UserDetailsService userDetailsService;
    private final InvalidTokenRepository invalidTokenRepository;
    private final ObjectMapper objectMapper;

    /**
     * Основной метод фильтрации, выполняемый для каждого запроса.
     * <p>
     * Метод выполняет следующие шаги:
     * 1. Пропускает запросы `OPTIONS` (preflight-запросы) и запросы к публичным URL.
     * 2. Извлекает JWT-токен из заголовка "Authorization".
     * 3. Проверяет, что токен не пуст, имеет правильный формат и не находится в списке аннулированных токенов.
     * 4. Если токен валиден, извлекает имя пользователя, загружает его данные
     * из {@link UserDetailsService} и устанавливает аутентификацию в {@link SecurityContextHolder}.
     * 5. В случае ошибки валидации токена или отсутствия пользователя, отправляет
     * стандартизированный JSON-ответ с ошибкой.
     *
     * @param request       Текущий HTTP-запрос.
     * @param response      HTTP-ответ, в который можно записать ошибку.
     * @param filterChain   Цепочка фильтров для передачи запроса.
     * @throws ServletException в случае ошибки сервлета.
     * @throws IOException      в случае ошибки ввода-вывода.
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        String path = request.getRequestURI();
        log.info("Class: JwtFilter, method: doFilterInternal, path: {}", path);

        if ("OPTIONS".equalsIgnoreCase(request.getMethod())) {
            log.debug("Skipping JWT check for OPTIONS preflight request");
            filterChain.doFilter(request, response);
            return;
        }

        if (path.startsWith("/api/v1/auth")
                || path.startsWith("/api/v1/signup")
                || path.startsWith("/v3/api-docs")
                || path.startsWith("/swagger-ui")
                || path.startsWith("/swagger-ui.html")
                || path.startsWith("/webjars")
        ) {
            log.info("Skipping JWT check for public endpoint: {}", path);
            filterChain.doFilter(request, response);
            return;
        }

        String authHeader = request.getHeader("Authorization");
        if (authHeader == null || authHeader.isBlank() || !authHeader.startsWith("Bearer ")) {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Missing or invalid JWT");
            return;
        }

        String jwt = authHeader.substring(7);
        try {

            String username = jwtUtil.validateTokenAndRetrieveSubject(jwt);

            if (invalidTokenRepository.existsByToken(jwt)) {
                log.warn("Token is invalidated: {}", jwt);
                sendErrorResponse(response, HttpStatus.UNAUTHORIZED, "Token is expired", path);
                return;
            }
            UserDetails userDetails = userDetailsService.loadUserByUsername(username);
            UsernamePasswordAuthenticationToken authToken =
                    new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(authToken);
            log.info("Successfully authenticated user: {}", username);
            filterChain.doFilter(request, response);
        } catch (JWTVerificationException exc) {
            log.error("Invalid JWT: {}", exc.getMessage());
            sendErrorResponse(response, HttpStatus.UNAUTHORIZED, "Invalid JWT", path);
        } catch (UsernameNotFoundException exc) {
            log.error("User not found: {}", exc.getMessage());
            sendErrorResponse(response, HttpStatus.UNAUTHORIZED, "User not found", path);
        }
    }

    /**
     * Вспомогательный метод для отправки стандартизированного JSON-ответа об ошибке.
     *
     * @param response HTTP-ответ, в который будет записан JSON.
     * @param status   Статус HTTP-ответа.
     * @param message  Сообщение об ошибке.
     * @param path     Путь запроса.
     * @throws IOException если произошла ошибка при записи в поток ответа.
     */
    private void sendErrorResponse(HttpServletResponse response, HttpStatus status, String message, String path)
            throws IOException {
        response.setStatus(status.value());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        ErrorResponse errorResponse = new ErrorResponse(
                status,
                message,
                null,
                path,
                LocalDateTime.now()
        );
        response.getWriter().write(objectMapper.writeValueAsString(errorResponse));
    }

}