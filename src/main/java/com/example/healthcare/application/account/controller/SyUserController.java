package com.example.healthcare.application.account.controller;

import com.example.healthcare.application.account.service.UserSyService;
import com.example.healthcare.application.account.service.dto.ChangeAuthorityDTO;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/sy/users")
public class SyUserController {
  private final UserSyService userSyService;

  // 권한 수정
  @PutMapping(value = "/{user-id}/roles", consumes = MediaType.APPLICATION_JSON_VALUE)
  public void changeAuthority(
    @PathVariable(value = "user-id") long userId,
    @Valid @RequestBody ChangeAuthorityDTO dto) {
    userSyService.changeAuthority(userId, dto);
  }
}
