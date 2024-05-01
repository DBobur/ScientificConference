package com.example.scientificconference.dto;

import lombok.*;
import org.jetbrains.annotations.NotNull;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ConferenceDto {
    @NotNull
    private String name;
    @NotNull
    private LocalDateTime tileToBe;
    @NotNull
    private String placeToBe;}
