package com.example.bank_rest.controller.impl;

import com.example.bank_rest.controller.AuthController;
import com.example.bank_rest.dto.auth.AuthRequestDto;
import com.example.bank_rest.service.AuthService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RequiredArgsConstructor
@RestController
@Slf4j
public class AuthControllerImpl implements AuthController {

    private final AuthService authService;


    @Override
    public Map<String, Object> authHandler(AuthRequestDto authRequestDto) {
        log.info("Class: AuthControllerImpl, method: authHandler, authRequestDto = {}", authRequestDto.toString());
        return authService.handleAuth(authRequestDto);

    }
}
