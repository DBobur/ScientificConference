package com.example.scientificconference.controller;

import com.example.scientificconference.dto.LoginDto;
import com.example.scientificconference.dto.RegisterDto;
import com.example.scientificconference.entity.UserEntity;
import com.example.scientificconference.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {
    private final AuthService authService;
    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody RegisterDto registerDto) {
        authService.register(registerDto);
        return ResponseEntity.status(HttpStatus.CREATED).body("Foydalanuvchi muvaffaqiyatli ro'yxatdan o'tdi");
    }

    @PostMapping("/login")
    public ResponseEntity<String> loginUser(@RequestBody LoginDto loginDto) {
        RegisterDto login = authService.login(loginDto);
        if (login != null) {
            return ResponseEntity.ok("Foydalanuvchi muvaffaqiyatli kirdi");
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Noto'g'ri foydalanuvchi nomi yoki parol");
        }
    }
}
