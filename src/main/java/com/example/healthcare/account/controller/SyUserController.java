package com.example.healthcare.account.controller;

import com.example.healthcare.account.service.UserSyService;
import com.example.healthcare.account.service.dto.ChangeAuthorityDTO;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/sy/users")
public class SyUserController {
    private final UserSyService userSyService;

    // 권한 수정
    @PatchMapping("/roles")
    public void changeAuthority(@Valid @RequestBody ChangeAuthorityDTO dto){
        userSyService.changeAuthority(dto);

    }
}
