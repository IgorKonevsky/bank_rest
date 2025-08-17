package com.example.bank_rest.security;

import com.example.bank_rest.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;


/**
 * Пользовательский сервис для загрузки данных пользователя,
 * который реализует интерфейс {@link UserDetailsService} из Spring Security.
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class MyUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    /**
     * Находит пользователя в базе данных по его имени пользователя.
     * <p>
     * Этот метод является частью контракта {@link UserDetailsService}.
     * Если пользователь не найден, выбрасывается исключение {@link UsernameNotFoundException}.
     *
     * @param username Имя пользователя (логин), по которому нужно найти пользователя.
     * @return Объект {@link UserDetails}, представляющий найденного пользователя.
     * @throws UsernameNotFoundException если пользователь с указанным именем не найден в базе данных.
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        return Optional.ofNullable(userRepository.findByUsername(username)).orElseThrow(() -> new UsernameNotFoundException("User not found"));

    }
}
