package com.example.healthcare.application.account.controller;

import com.example.healthcare.application.account.service.UserCmService;
import com.example.healthcare.application.common.response.CommonResponse;
import com.example.healthcare.infra.config.security.user.LoginUser;
import com.example.healthcare.application.vo.UserVO;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/cm/users")
public class CmUserController {

  private final UserCmService userCmService;

  @GetMapping("/{user-id}")
  public CommonResponse<UserVO> getUser(@PathVariable(value = "user-id") Long userId,
    @AuthenticationPrincipal LoginUser loginUser) {
    return CommonResponse.success(userCmService.getUser(loginUser, userId));
  }

}