package com.example.healthcare.util;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class AdminCheck {
    public void adminPage() {
        // SYSTEM 권한을 가진 사용자만 해당 페이지에 접근할 수 있도록 체크
        SecurityContextHolder.getContext().getAuthentication().getAuthorities().stream()
                .filter(a -> a.getAuthority().equals("SYSTEM"))
                .findFirst()
                .orElseThrow(() -> new AccessDeniedException("Access denied"));
    }
}
