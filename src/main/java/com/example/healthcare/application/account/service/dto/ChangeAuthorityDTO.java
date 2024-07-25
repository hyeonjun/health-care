package com.example.healthcare.application.account.service.dto;

import com.example.healthcare.application.account.domain.code.AuthorityType;
import jakarta.validation.constraints.NotNull;

public record ChangeAuthorityDTO(
  @NotNull AuthorityType authorityType) {
}
