package com.example.healthcare.application.auth.service.dto;

import jakarta.validation.constraints.NotBlank;

public record LogoutDTO(@NotBlank String accessToken) {
}
