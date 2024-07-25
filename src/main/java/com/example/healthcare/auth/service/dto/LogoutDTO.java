package com.example.healthcare.auth.service.dto;

import jakarta.validation.constraints.NotBlank;

public record LogoutDTO(@NotBlank String accessToken) {
}
