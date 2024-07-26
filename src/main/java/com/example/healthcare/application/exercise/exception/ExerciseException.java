package com.example.healthcare.application.exercise.exception;

import com.example.healthcare.application.common.exception.CommonException;
import com.example.healthcare.application.common.response.ResponseCode;
import lombok.AllArgsConstructor;

public class ExerciseException extends CommonException {

  @AllArgsConstructor
  public enum ExerciseExceptionCode implements ResponseCode {
    INVALID_EXERCISE("EEC-001", "invalid exercise"),
    NOT_FOUND_RELATION_EXERCISE_TYPE("EEC-002", "relation exercise type not found")
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

  public ExerciseException(ExerciseExceptionCode code) {
    super(code);
  }

  public ExerciseException(ExerciseExceptionCode code, String message) {
    super(code, message);
  }

  public ExerciseException(String message) {
    super(ExerciseExceptionCode.INVALID_EXERCISE, message);
  }
}
