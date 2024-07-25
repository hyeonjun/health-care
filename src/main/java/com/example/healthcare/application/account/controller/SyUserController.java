package com.example.healthcare.application.account.controller;

import com.example.healthcare.application.account.controller.dto.SearchUserDTO;
import com.example.healthcare.application.account.service.UserSyService;
import com.example.healthcare.application.account.controller.dto.ChangeAuthorityDTO;
import com.example.healthcare.application.common.response.CommonResponse;
import com.example.healthcare.application.vo.UserVO;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/sy/users")
public class SyUserController {

  private final UserSyService userSyService;

  @GetMapping
  public CommonResponse<Page<UserVO>> getUsers(
    @Valid SearchUserDTO dto) {
    return CommonResponse.success(userSyService.getUsers(dto));
  }

  // 권한 수정
  @PutMapping(value = "/{user-id}/roles", consumes = MediaType.APPLICATION_JSON_VALUE)
  public void changeAuthority(
    @PathVariable(value = "user-id") long userId,
    @Valid @RequestBody ChangeAuthorityDTO dto) {
    userSyService.changeAuthority(userId, dto);
  }
}
