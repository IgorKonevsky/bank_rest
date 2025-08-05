package com.example.bank_rest.service;

import com.example.bank_rest.dto.auth.AuthRequestDto;
import com.example.bank_rest.repository.UserRepository;
import com.example.bank_rest.security.JwtUtil;
import com.example.bank_rest.service.impl.AuthServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AuthServiceImplTest {

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private JwtUtil jwtUtil;

    @InjectMocks
    private AuthServiceImpl authService;

    @Test
    void handleAuth_ShouldReturnToken_WhenAuthenticationIsSuccessful() {
        // Arrange
        String username = "testuser";
        String password = "testpass";
        AuthRequestDto authRequestDto = new AuthRequestDto(username, password);
        String expectedToken = "mocked-jwt-token";

        when(authenticationManager.authenticate(any()))
                .thenReturn(mock(Authentication.class));
        when(jwtUtil.generateToken(username))
                .thenReturn(expectedToken);

        Map<String, Object> result = authService.handleAuth(authRequestDto);

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(expectedToken, result.get("jwt-token"));
        verify(authenticationManager).authenticate(any());
        verify(jwtUtil).generateToken(username);
    }

    @Test
    void handleAuth_ShouldThrowException_WhenAuthenticationFails() {
        String username = "testuser";
        String password = "wrongpass";
        AuthRequestDto authRequestDto = new AuthRequestDto(username, password);

        when(authenticationManager.authenticate(any()))
                .thenThrow(new BadCredentialsException("Invalid username or password"));

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            authService.handleAuth(authRequestDto);
        });

        assertEquals("Invalid username/password.", exception.getMessage());
        verify(authenticationManager).authenticate(any());
    }
}
