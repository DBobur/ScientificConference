package com.example.scientificconference.service;

import com.example.scientificconference.dto.LoginDto;
import com.example.scientificconference.dto.RegisterDto;
import com.example.scientificconference.entity.UserEntity;
import com.example.scientificconference.exception.EmailAlreadyExistsExceptions;
import com.example.scientificconference.exception.UserNotFoundException;
import com.example.scientificconference.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final ModelMapper modelMapper;

    public LoginDto register(RegisterDto registerDto){
        if(checkEmail(registerDto.getEmail())){
            throw new EmailAlreadyExistsExceptions("Email already");
        }
        UserEntity userEntity = UserEntity.builder()
                .email(registerDto.getEmail())
                .email(registerDto.getEmail())
                .password(registerDto.getPassword())
                .roles(registerDto.getRoleList())
                .build();

        return modelMapper.map(userRepository.save(userEntity), LoginDto.class);
    }

    private boolean checkEmail(String username) {
        return userRepository.findByEmail(username) != null;
    }

    public UserEntity login(LoginDto loginDto) {
        UserEntity entity = userRepository.findByEmail(loginDto.getEmail());
        if (Objects.equals(entity.getPassword(),loginDto.getPassword())){
            return entity;
        }
        throw new UserNotFoundException("User not found");
    }
}
