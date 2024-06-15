package com.example.healthcare.util;

import com.example.healthcare.exception.CommonException;
import lombok.Getter;

@Getter
public class CommonResponse<T> {

  private final T data;
  private final String code;
  private final String message;

  public CommonResponse(ResponseCode responseCode) {
    this(null, responseCode);
  }

  public CommonResponse(CommonException e) {
    this(null, e.getResponseCode().getCode(), e.getMessage());
  }

  public CommonResponse(T data, ResponseCode responseCode) {
    this(data, responseCode.getCode(), responseCode.getMessage());
  }

  public CommonResponse(T data, String code, String message) {
    this.data = data;
    this.code = code;
    this.message = message;
  }

  public static <T> CommonResponse<T> success() {
    return new CommonResponse<>(CommonResponseCode.SUCCESS);
  }

  public static <T> CommonResponse<T> success(T data) {
    return new CommonResponse<>(data, CommonResponseCode.SUCCESS);
  }
}
