package com.example.bank_rest.service;

import com.example.bank_rest.dto.auth.AuthRequestDto;

import java.util.Map;

public interface AuthService {

    public Map<String, Object> handleAuth(AuthRequestDto authRequestDto);

}
