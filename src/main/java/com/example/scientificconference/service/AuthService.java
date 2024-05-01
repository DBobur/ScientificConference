package com.example.activeuser.service;

import com.example.activeuser.advice.exception.EmailAlreadyExistsExceptions;
import com.example.activeuser.advice.exception.PasswordIncorrectException;
import com.example.activeuser.advice.exception.UserNotFoundException;
import com.example.activeuser.dto.LoginDto;
import com.example.activeuser.dto.SignupDto;
import com.example.activeuser.entity.Checking;
import com.example.activeuser.entity.User;
import com.example.activeuser.jwt.JwtProvider;
import com.example.activeuser.jwt.JwtResponse;
import com.example.activeuser.repo.CheckingRepository;
import com.example.activeuser.repo.UserRepository;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final PasswordEncoder passwordEncoder;
    private final JwtProvider jwtProvider;
    private final UserRepository userRepository;
    private final CheckingRepository checkingRepository;

    public User getUserEntity(SignupDto dto) {
        return User.builder()
                .email(dto.getEmail())
                .password(passwordEncoder.encode(dto.getPassword()))
                .userName(dto.getUserName())
                .roles(dto.getRoleList())
                .build();
    }

    public JwtResponse signup(SignupDto signupDto) {
        if (signupDto != null) {
            User getUserEntity = getUserEntity(signupDto);
                User user = userRepository.save(getUserEntity);
                String token = jwtProvider.generate(user);

                saveToChecking(token);

                return new JwtResponse(user.getId(), token);

        }
        return null;
    }

    public JwtResponse login(LoginDto loginDto) {
        User user = userRepository.findByEmail(loginDto.getEmail());
        if (user != null && passwordEncoder.matches(loginDto.getPassword(), user.getPassword())) {
            String token = jwtProvider.generate(user);
            return new JwtResponse(user.getId(), token);
        }
        throw new UserNotFoundException(" email: " + loginDto.getEmail() + " or password: " + loginDto.getPassword());
    }

    public void saveToChecking(String token) {
        Claims claims = jwtProvider.parse(token);
        checkingRepository.save(
                Checking.builder()
                        .email(claims.getSubject())
                        .password(claims.get("password", String.class))
                        .build()
        );
    }

    public User checking(String password, Long userId) {
        User user = userRepository.findById(userId).get();
        String password1 = checkingRepository.findByEmail(user.getEmail()).getPassword();
        if (password.equals(password1)) {
            return user;
        }
        throw new PasswordIncorrectException(password);
    }

}
