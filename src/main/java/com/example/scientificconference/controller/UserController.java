package com.example.scientificconference.controller;

import com.example.scientificconference.dto.LoginDto;
import com.example.scientificconference.dto.RegisterDto;
import com.example.scientificconference.entity.UserEntity;
import com.example.scientificconference.service.AuthService;
import jdk.jfr.Category;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {
    private final AuthService authService;
    @PostMapping("/register")
    public ResponseEntity<LoginDto> register(@RequestBody RegisterDto registerDto) {
        return ResponseEntity.ok(authService.register(registerDto));
    }

    @PostMapping("/login")
    public ResponseEntity<UserEntity> loginUser(@RequestBody LoginDto loginDto) {
        return ResponseEntity.ok(authService.login(loginDto));
    }
}
