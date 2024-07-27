package com.example.openschool_jwt.model.mapper;

import com.example.openschool_jwt.model.User;
import com.example.openschool_jwt.model.dto.auth.JwtResponse;
import org.springframework.stereotype.Component;

@Component
public class JwtMapper {
    public JwtResponse toDto(User user) {
        JwtResponse jwtResponse = new JwtResponse();
        jwtResponse.setId(user.getId());
        jwtResponse.setEmail(user.getEmail());
        return jwtResponse;
    }
}
