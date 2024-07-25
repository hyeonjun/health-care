package com.example.healthcare.application.common.exception;

import com.example.healthcare.application.common.response.ResponseCode;
import lombok.AllArgsConstructor;

public class InvalidInputValueException extends CommonException {

  @AllArgsConstructor
  public enum InvalidInputValueExceptionCode implements ResponseCode {
    INVALID_INPUT_VALUE("IIV-001", "invalid input value"),
    METHOD_ARGUMENT_TYPE_MISMATCH("IIV-002", "method argument type mismatch"),
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

  public InvalidInputValueException(InvalidInputValueExceptionCode code) {
    super(code);
  }

  public InvalidInputValueException(InvalidInputValueExceptionCode code, String detailMessage) {
    super(code, detailMessage);
  }

  public InvalidInputValueException(String detailMessage) {
    super(InvalidInputValueExceptionCode.INVALID_INPUT_VALUE, detailMessage);
  }
}
