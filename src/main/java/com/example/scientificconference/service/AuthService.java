package com.example.scientificconference.service;

import com.example.scientificconference.dto.LoginDto;
import com.example.scientificconference.dto.RegisterDto;
import com.example.scientificconference.entity.UserEntity;
import com.example.scientificconference.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final ModelMapper modelMapper;

    public LoginDto register(RegisterDto registerDto){
        if(checkUsername(registerDto.getUsername())){
            return null;
        }

        UserEntity userEntity = UserEntity.builder()
                .email(registerDto.getEmail())
                .username(registerDto.getUsername())
                .password(registerDto.getPassword())
                .roles(registerDto.getRoleList())
                .build();

        return modelMapper.map(userRepository.save(userEntity), LoginDto.class);
    }

    private boolean checkUsername(String username) {
        return userRepository.getByUsername(username) != null;
    }

    public UserEntity login(LoginDto loginDto) {
        return userRepository.getByUsernameAndPassword(loginDto.getUsername(),loginDto.getPassword());
    }
}
