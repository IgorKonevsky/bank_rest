package com.example.bank_rest.controller.impl;

import com.example.bank_rest.controller.LogoutController;
import com.example.bank_rest.service.LogoutService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Slf4j
public class LogoutControllerImpl implements LogoutController {

    private final LogoutService logoutService;

    @Override
    public ResponseEntity<?> logout(String authHeader, HttpServletResponse response) {
        return ResponseEntity.ok(logoutService.invalidateToken(authHeader));
    }
}
