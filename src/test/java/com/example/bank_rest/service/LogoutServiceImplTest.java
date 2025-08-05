package com.example.bank_rest.service;

import com.example.bank_rest.entity.InvalidToken;
import com.example.bank_rest.repository.InvalidTokenRepository;
import com.example.bank_rest.service.impl.LogoutServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class LogoutServiceImplTest {

    @Mock
    private InvalidTokenRepository invalidTokenRepository;

    @InjectMocks
    private LogoutServiceImpl logoutService;

    @Test
    void invalidateToken_ShouldSaveInvalidTokenAndReturnMessage() {
        String jwt = "Bearer test.jwt.token";

        String result = logoutService.invalidateToken(jwt);

        assertEquals("Logout successful", result);

        ArgumentCaptor<InvalidToken> captor = ArgumentCaptor.forClass(InvalidToken.class);
        verify(invalidTokenRepository, times(1)).save(captor.capture());

        InvalidToken savedToken = captor.getValue();
        assertEquals("test.jwt.token", savedToken.getToken());
        assertNotNull(savedToken.getExpiryDate());

        assertTrue(savedToken.getExpiryDate().getTime() <= System.currentTimeMillis());
    }
}
