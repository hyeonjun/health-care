package com.example.healthcare.application.auth.controller.dto;

import jakarta.validation.constraints.NotBlank;

public record LogoutDTO(@NotBlank String accessToken) {
}
