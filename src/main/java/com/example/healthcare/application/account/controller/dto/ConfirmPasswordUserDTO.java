package com.example.healthcare.application.account.controller.dto;

import jakarta.validation.constraints.NotBlank;

public record ConfirmPasswordUserDTO(
  @NotBlank String password) {
}
