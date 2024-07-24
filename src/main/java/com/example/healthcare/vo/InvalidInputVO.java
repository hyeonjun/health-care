package com.example.healthcare.vo;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.springframework.validation.FieldError;

import java.util.Arrays;
import java.util.Optional;
import java.util.stream.Stream;
import jakarta.validation.constraints.Pattern.Flag;

public class InvalidInputVO {

  private String field;
  private Object value;
  private String details;
  private String code;
  private String[] arguments;

  public static InvalidInputVO valueOf(String field, Object value, String details) {
    return new InvalidInputVO(field, value, details, "default", null);
  }

  public static InvalidInputVO valueOf(FieldError e) {
    return new InvalidInputVO(e.getField(), e.getRejectedValue(), e.getDefaultMessage(), e.getCode(), convertArguments(e));
  }

  public InvalidInputVO(String field, Object value, String details, String code,
    String[] arguments) {
    this.field = field;
    this.value = value;
    this.details = details;
    this.code = code;
    this.arguments = arguments;
  }

  private static String[] convertArguments(FieldError e) {
    return Optional.ofNullable(e.getArguments())
      .map(errArgs -> {
        String[] args = Arrays.stream(errArgs)
          .filter(x -> !(x instanceof Flag[]))
          .skip(1)
          .flatMap(o -> o instanceof Object[] ? Arrays.stream((Object[]) o) : Stream.of(o))
          .map(Object::toString)
          .toArray(String[]::new);
        ArrayUtils.reverse(args);
        return args;
      })
      .orElse(null);
  }

  public String getField() {
    return field;
  }

  public void setField(String field) {
    this.field = field;
  }

  public Object getValue() {
    return value;
  }

  public void setValue(Object value) {
    this.value = value;
  }

  public String getDetails() {
    return details;
  }

  public void setDetails(String details) {
    this.details = details;
  }

  public String getCode() {
    return code;
  }

  public void setCode(String code) {
    this.code = code;
  }

  public String[] getArguments() {
    return arguments;
  }

  public void setArguments(String[] arguments) {
    this.arguments = arguments;
  }

  @Override
  public String toString() {
    return new ToStringBuilder(this, ToStringStyle.JSON_STYLE)
      .append("field", field)
      .append("value", value != null ? value : null)
      .append("details", details)
      .append("code", code)
      .append("arguments", arguments != null ? arguments : null)
      .toString();
  }
}
