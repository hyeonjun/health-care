package com.example.healthcare.application.account.controller;

import com.example.healthcare.application.account.service.UserAnService;
import com.example.healthcare.application.account.service.dto.CreateUserDTO;
import com.example.healthcare.application.common.response.CommonResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/an/users")
public class AnUserController {

  private final UserAnService userAnService;

  // 회원가입
  @PostMapping("/sign-up")
  public CommonResponse<Void> signUp(@Valid @RequestBody CreateUserDTO dto) {
    userAnService.signUp(dto);
    return CommonResponse.success();
  }

}
