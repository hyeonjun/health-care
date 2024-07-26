package com.example.healthcare.application.account.controller.dto;

import com.example.healthcare.application.account.domain.code.UserStatus;
import com.example.healthcare.application.common.exception.InvalidInputValueException;
import com.example.healthcare.application.common.exception.InvalidInputValueException.InvalidInputValueExceptionCode;
import jakarta.validation.constraints.NotNull;

public record DeleteUserDTO(
  @NotNull UserStatus userStatus
) {

  public DeleteUserDTO {
    if (!UserStatus.getSuspendedStatuses().contains(userStatus)) {
      throw new InvalidInputValueException(InvalidInputValueExceptionCode.INVALID_INPUT_VALUE);
    }
  }
}
