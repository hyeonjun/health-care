package com.example.healthcare.application.account.controller.dto;

import com.example.healthcare.application.account.domain.code.AuthorityType;
import jakarta.validation.constraints.NotNull;

public record ChangeAuthorityDTO(
  @NotNull AuthorityType authorityType) {
}
