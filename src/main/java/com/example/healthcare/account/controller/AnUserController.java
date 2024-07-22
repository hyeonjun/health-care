package com.example.healthcare.account.controller;

import com.example.healthcare.account.service.UserAnService;
import com.example.healthcare.account.service.dto.CreateUserDTO;
import com.example.healthcare.account.service.dto.ReissueTokenDTO;
import com.example.healthcare.account.service.dto.UpdateUserDTO;
import com.example.healthcare.common.response.CommonResponse;
import com.example.healthcare.security.user.UserDetailsImpl;
import com.example.healthcare.security.user.UserDetailsServiceImpl;
import com.example.healthcare.vo.TokenVO;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;


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

  // 개인정보 수정
  @PutMapping("/profiles")
  public CommonResponse<Void> passwordCheck(@Valid @RequestBody UpdateUserDTO dto) {
    userAnService.userUpdate(dto);
    return CommonResponse.success();
  }

  // 엑세스 토큰 재발급 api 추가
  @PostMapping("/reissue:token")
  public CommonResponse<TokenVO> reissueToken(@Valid @RequestBody ReissueTokenDTO dto, @AuthenticationPrincipal UserDetailsImpl userDetails) {
    return userAnService.reissueToken(dto, userDetails);
  }
}
