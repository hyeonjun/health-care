package com.example.healthcare.advice;

import com.example.healthcare.common.exception.CommonException;
import com.example.healthcare.common.exception.InvalidInputValueException.InvalidInputValueExceptionCode;
import com.example.healthcare.common.response.CommonResponse;
import com.example.healthcare.common.response.CommonResponseCode;
import com.example.healthcare.vo.InvalidInputVO;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.transaction.TransactionSystemException;
import org.springframework.util.CollectionUtils;
import org.springframework.validation.BindException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j(topic = "[Global Exception] ")
@RestControllerAdvice
public class GlobalExceptionHandler {

  @ResponseStatus(value = HttpStatus.OK)
  @ExceptionHandler(value = CommonException.class)
  public CommonResponse defaultErrorHandler(HttpServletRequest req, CommonException e) {
    log.warn("defaultErrorHandler : {}", e.toString(), e);
    return new CommonResponse(e);
  }

  @ResponseStatus(value = HttpStatus.OK)
  @ExceptionHandler(value = MethodArgumentTypeMismatchException.class)
  public CommonResponse methodArgumentTypeMismatchHandler(HttpServletRequest req,
    MethodArgumentTypeMismatchException e) {
    log.warn("methodArgumentTypeMismatchErrorHandler : {}", e.toString(), e);
    List<InvalidInputVO> invalidInputList = new ArrayList<>();
    invalidInputList.add(InvalidInputVO.valueOf(e.getName(), e.getValue(), null));
    return new CommonResponse(invalidInputList,
      InvalidInputValueExceptionCode.METHOD_ARGUMENT_TYPE_MISMATCH);
  }

  @ResponseStatus(value = HttpStatus.OK)
  @ExceptionHandler(value = BindException.class)
  public CommonResponse bindingErrorHandler(HttpServletRequest req, BindException e) {
    log.warn("bindingErrorHandler : {}", e.toString(), e);
    if (!CollectionUtils.isEmpty(e.getFieldErrors())) {
      List<InvalidInputVO> invalidInputList = e.getFieldErrors().stream()
        .map(InvalidInputVO::valueOf)
        .collect(Collectors.toList());
      return new CommonResponse(invalidInputList, InvalidInputValueExceptionCode.INVALID_INPUT_VALUE);
    } else {
      return new CommonResponse(InvalidInputValueExceptionCode.INVALID_INPUT_VALUE);
    }
  }

  @ResponseStatus(value = HttpStatus.OK)
  @ExceptionHandler(value = MethodArgumentNotValidException.class)
  public CommonResponse methodArgumentNotValidErrorHandler(HttpServletRequest req,
    MethodArgumentNotValidException e) {
    log.warn("methodArgumentNotValidErrorHandler : {}", e.toString());
    if (!CollectionUtils.isEmpty(e.getBindingResult().getFieldErrors())) {
      List<InvalidInputVO> invalidInputList = e.getBindingResult().getFieldErrors().stream()
        .map(InvalidInputVO::valueOf)
        .collect(Collectors.toList());
      return new CommonResponse(invalidInputList, InvalidInputValueExceptionCode.INVALID_INPUT_VALUE);
    } else {
      return new CommonResponse(InvalidInputValueExceptionCode.INVALID_INPUT_VALUE);
    }
  }

  @ResponseStatus(value = HttpStatus.OK)
  @ExceptionHandler(value = HttpMessageNotReadableException.class)
  public CommonResponse httpMessageNotReadableErrorHandler(HttpServletRequest req, HttpMessageNotReadableException e) {
    log.warn("httpMessageNotReadableErrorHandler : {}", e.toString());
    return new CommonResponse(InvalidInputValueExceptionCode.INVALID_INPUT_VALUE);
  }

  @ResponseStatus(value = HttpStatus.OK)
  @ExceptionHandler(value = TransactionSystemException.class)
  public CommonResponse transactionSystemExceptionHandler(HttpServletRequest req,
    TransactionSystemException e) {
    log.error("transactionSystemExceptionHandler : [{}]", e.getClass().getName(), e);
    for (int i = 0; i < 5; i++) {
      Throwable cause = e.getCause();
      if (cause instanceof ConstraintViolationException) {
        return new CommonResponse(InvalidInputValueExceptionCode.INVALID_INPUT_VALUE);
      }
    }
    return new CommonResponse(CommonResponseCode.FAIL);
  }


  @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
  @ExceptionHandler(value = Exception.class)
  public CommonResponse errorHandler(HttpServletRequest req, Exception e) {
    log.error("error at : [{}]", e.getClass().getName(), e);
    return new CommonResponse(CommonResponseCode.FAIL);
  }
}
