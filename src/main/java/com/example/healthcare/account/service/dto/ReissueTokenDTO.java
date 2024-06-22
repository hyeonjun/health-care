package com.example.healthcare.account.service.dto;

import jakarta.validation.constraints.NotBlank;

public record ReissueTokenDTO(
        @NotBlank
        String userId,
        @NotBlank
        String refreshToken) {}
