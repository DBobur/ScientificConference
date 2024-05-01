package com.example.scientificconference.service;

import com.example.scientificconference.dto.LoginDto;
import com.example.scientificconference.dto.RegisterDto;
import com.example.scientificconference.entity.UserEntity;
import com.example.scientificconference.repo.UserRepository;
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

        userRepository.save(userEntity);
        return new LoginDto(registerDto.getUsername(),registerDto.getPassword());
    }

    private boolean checkUsername(String username) {
        return userRepository.getByUsername(username) != null;
    }

    public RegisterDto login(LoginDto loginDto) {
        UserEntity userEntity = userRepository.getByUsernameAndPassword(loginDto.getUsername(),loginDto.getPassword());
        return modelMapper.map(userEntity, RegisterDto.class);
    }
}
