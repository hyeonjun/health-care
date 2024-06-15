package com.example.healthcare.account.controller;

import com.example.healthcare.account.service.UserAnService;
import com.example.healthcare.account.service.dto.SignUpDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/")
public class AnUserController {
  private final UserAnService userAnService;

  // 회원가입
  @PostMapping("an/users/sign-up")
  public void signUp(@RequestBody SignUpDTO signUpDTO){
    userAnService.signUp(signUpDTO);
  }

}
