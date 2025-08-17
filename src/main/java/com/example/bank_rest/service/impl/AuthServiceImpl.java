package com.example.bank_rest.service.impl;

import com.example.bank_rest.dto.auth.AuthRequestDto;
import com.example.bank_rest.repository.UserRepository;
import com.example.bank_rest.security.JwtUtil;
import com.example.bank_rest.service.AuthService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Map;

@Service
@Slf4j
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    @Override
    public Map<String, Object> handleAuth(AuthRequestDto authRequestDto) {
        try {
            UsernamePasswordAuthenticationToken authInputToken =
                    new UsernamePasswordAuthenticationToken(authRequestDto.username(), authRequestDto.password());
            log.info("Class: AuthControllerImpl, method: authHandler, authInputToken = {}", authInputToken.toString());
            authenticationManager.authenticate(authInputToken);

            String token = jwtUtil.generateToken(authRequestDto.username());
            return Collections.singletonMap("jwt-token", token);
        } catch (AuthenticationException authExc) {
            log.error("Authentication failed for username: {}", authRequestDto.username(), authExc);
            throw new AuthenticationException("Invalid username or password") {};
        }
    }
}
