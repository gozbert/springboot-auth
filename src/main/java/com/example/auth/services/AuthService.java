package com.example.auth.services;

import com.example.auth.dao.request.SignUpRequest;
import com.example.auth.dao.request.SigninRequest;
import com.example.auth.dao.response.AuthResponse;

public interface AuthService {
    AuthResponse signup(SignUpRequest request);

    AuthResponse signin(SigninRequest request);
}
