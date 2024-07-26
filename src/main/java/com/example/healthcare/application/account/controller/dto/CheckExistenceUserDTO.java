package com.example.healthcare.application.account.controller.dto;

import jakarta.validation.constraints.NotEmpty;

public record CheckExistenceUserDTO(
  @NotEmpty
  String email
) {
}
