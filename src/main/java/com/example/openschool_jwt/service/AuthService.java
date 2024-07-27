package com.example.openschool_jwt.service;

import com.example.openschool_jwt.model.dto.auth.JwtRequest;
import com.example.openschool_jwt.model.dto.auth.JwtResponse;
import org.springframework.stereotype.Service;

@Service
public interface AuthService {
    JwtResponse login(JwtRequest loginRequest);
    JwtResponse refresh(String refreshToken);

}
