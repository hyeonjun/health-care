package com.example.healthcare.application.common.exception;

import com.example.healthcare.application.common.response.ResponseCode;
import lombok.AllArgsConstructor;

public class DuplicateException extends CommonException {

  @AllArgsConstructor
  public enum DuplicateExceptionCode implements ResponseCode {
    DUPLICATE("DPE-001", "duplicate"),
    DUPLICATE_EMAIL("DPE-002", "email duplicate"),
    DUPLICATE_ROUTINE_ORDER("DPE-003", "routine order duplicate"),
    DUPLICATE_SET_NUMBER("DPE-004", "set number duplicate"),
    ;

    private final String code;
    private final String message;

    @Override
    public String getCode() {
      return code;
    }

    @Override
    public String getMessage() {
      return message;
    }
  }

  public DuplicateException(DuplicateExceptionCode code) {
    super(code);
  }

  public DuplicateException(DuplicateExceptionCode code, String message) {
    super(code, message);
  }

  public DuplicateException(String message) {
    super(DuplicateExceptionCode.DUPLICATE, message);
  }
}
