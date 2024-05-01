package com.example.scientificconference.entity;

import jakarta.persistence.*;
import lombok.*;
import org.jetbrains.annotations.NotNull;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "conference")
public class Conference extends BaseEntity{
    @Column(unique = true)
    @NotNull
    private String name;
    @NotNull
    private LocalDateTime timeToBe;
    @NotNull
    private String placeToBe;
}