package com.example.bank_rest.service;


public interface LogoutService {

    public String invalidateToken(String authHeader);
}
