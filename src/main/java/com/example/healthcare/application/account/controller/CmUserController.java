package com.example.healthcare.application.account.controller;

import com.example.healthcare.application.account.controller.dto.ConfirmPasswordUserDTO;
import com.example.healthcare.application.account.controller.dto.DeleteUserDTO;
import com.example.healthcare.application.account.service.UserCmService;
import com.example.healthcare.application.common.response.CommonResponse;
import com.example.healthcare.application.vo.UserVO;
import com.example.healthcare.infra.config.security.user.LoginUser;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/cm/users")
public class CmUserController {

  private final UserCmService userCmService;

  // 회원 정보 조회
  @GetMapping("/{user-id}")
  public CommonResponse<UserVO> getUser(@PathVariable(value = "user-id") Long userId,
    @AuthenticationPrincipal LoginUser loginUser) {
    return CommonResponse.success(userCmService.getUser(loginUser, userId));
  }

  // 비밀번호 확인
  @PostMapping(value = "/{user-id}/confirm:password", consumes = MediaType.APPLICATION_JSON_VALUE)
  public CommonResponse<Boolean> confirmPassword(@PathVariable(value = "user-id") Long userId,
    @Valid @RequestBody ConfirmPasswordUserDTO dto,
    @AuthenticationPrincipal LoginUser loginUser) {
    return CommonResponse.success(userCmService.confirmPassword(loginUser, userId, dto));
  }

  // 회원 탈퇴
  @DeleteMapping(value = "/{user-id}", consumes = MediaType.APPLICATION_JSON_VALUE)
  public CommonResponse<Boolean> deleteUser(@PathVariable(value = "user-id") Long userId,
    @Valid @RequestBody DeleteUserDTO dto,
    @AuthenticationPrincipal LoginUser loginUser) {
    userCmService.deleteUser(loginUser, userId, dto);
    return CommonResponse.success();
  }
}
