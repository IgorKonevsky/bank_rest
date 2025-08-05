package com.example.bank_rest.service;

import com.example.bank_rest.dto.auth.RegisterRequestDto;
import com.example.bank_rest.dto.auth.RegisterResponseDto;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.RequestBody;

public interface RegistrationService {
    RegisterResponseDto registerUser(@Valid @RequestBody RegisterRequestDto registerRequestDto);
}
