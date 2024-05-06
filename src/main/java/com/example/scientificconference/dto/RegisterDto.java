package com.example.scientificconference.dto;

import com.example.scientificconference.entity.Role;
import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RegisterDto {
    private String name;
    private String email;
    private String password;
    private List<Role> roleList;
}
