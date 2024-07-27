package com.example.openschool_jwt.controller;

import com.example.openschool_jwt.model.dto.auth.JwtRequest;
import com.example.openschool_jwt.model.dto.auth.JwtResponse;
import com.example.openschool_jwt.model.dto.user.UserDto;
import com.example.openschool_jwt.service.AuthService;
import com.example.openschool_jwt.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;
    private final UserService userService;

    @PostMapping("/login")
    public ResponseEntity<JwtResponse> login(@RequestBody JwtRequest jwtRequest) {
        log.info("Start auth method in AuthController");
        return ResponseEntity.ok(authService.login(jwtRequest));
    }

    @PostMapping("/register")
    public ResponseEntity<UserDto> register(@RequestBody UserDto userDto) {
        log.info("Start registration method in AuthController");
        return ResponseEntity.ok(userService.create(userDto));
    }

    @PostMapping("/refresh")
    public ResponseEntity<JwtResponse> refresh(@RequestBody String refreshToken) {
        log.info("Start refresh method in AuthController");
        return ResponseEntity.ok(authService.refresh(refreshToken));
    }
}
