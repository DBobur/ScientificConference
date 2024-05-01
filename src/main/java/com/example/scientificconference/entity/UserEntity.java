package com.example.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.jetbrains.annotations.NotNull;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "users")
public class UserEntity extends BaseEntity{
    @Column(unique = true)
    @NotNull
    private String email;
    @NotNull
    private String username;
    @NotNull
    private String password;
    @Column(columnDefinition = "boolean default true")
    @ManyToMany
    private List<Role> roles;

}