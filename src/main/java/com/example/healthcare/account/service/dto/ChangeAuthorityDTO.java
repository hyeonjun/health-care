package com.example.healthcare.account.service.dto;

import com.example.healthcare.account.domain.code.AuthorityType;
import jakarta.validation.constraints.NotNull;

public record ChangeAuthorityDTO(
  @NotNull AuthorityType authorityType) {
}
