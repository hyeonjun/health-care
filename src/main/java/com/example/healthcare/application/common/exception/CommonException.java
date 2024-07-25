package com.example.healthcare.application.common.exception;

import com.example.healthcare.application.common.response.ResponseCode;

import java.util.function.Function;
import java.util.function.Supplier;

public class CommonException extends RuntimeException {

  private final ResponseCode responseCode;

  public CommonException(ResponseCode responseCode) {
    super(responseCode.getMessage());
    this.responseCode = responseCode;
  }

  public CommonException(ResponseCode responseCode, String detailMessage) {
    super(responseCode.getMessage() + " : " + detailMessage);
    this.responseCode = responseCode;
  }

  public ResponseCode getResponseCode() {
    return responseCode;
  }

  public static <T, R> Supplier<R> bind(Function<T, R> fn, T val) {
    return () -> fn.apply(val);
  }
}
