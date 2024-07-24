package com.example.healthcare.account.service.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record ReissueTokenDTO(
        @NotNull
        String email,
        @NotBlank
        String refreshToken) {}
