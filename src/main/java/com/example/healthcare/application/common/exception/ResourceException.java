package com.example.healthcare.application.common.exception;

import com.example.healthcare.application.common.response.ResponseCode;
import lombok.AllArgsConstructor;

public class ResourceException extends CommonException {

  @AllArgsConstructor
  public enum ResourceExceptionCode implements ResponseCode {
    RESOURCE_NOT_FOUND("RNF-001", "resource not found"),
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

  public ResourceException(ResourceExceptionCode code) {
    super(code);
  }

  public ResourceException(ResourceExceptionCode code, String message) {
    super(code, message);
  }

  public ResourceException(String message) {
    super(ResourceExceptionCode.RESOURCE_NOT_FOUND, message);
  }
}
