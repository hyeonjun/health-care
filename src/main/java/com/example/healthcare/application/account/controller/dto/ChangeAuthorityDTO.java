package com.example.healthcare.application.account.controller.dto;

import com.example.healthcare.application.account.domain.code.AuthorityType;
import com.example.healthcare.application.common.exception.InvalidInputValueException;
import com.example.healthcare.application.common.exception.InvalidInputValueException.InvalidInputValueExceptionCode;
import jakarta.validation.constraints.NotNull;

public record ChangeAuthorityDTO(
  @NotNull AuthorityType authorityType) {

  public ChangeAuthorityDTO {
    if (!AuthorityType.getCommonAuthorityTypes().contains(authorityType)) {
      throw new InvalidInputValueException(InvalidInputValueExceptionCode.INVALID_INPUT_VALUE);
    }
  }

}
