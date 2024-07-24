package com.example.healthcare.account.service;

import com.example.healthcare.account.domain.User;
import com.example.healthcare.account.repository.UserRepository;
import com.example.healthcare.account.service.dto.ChangeAuthorityDTO;
import com.example.healthcare.util.AdminCheck;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserSyService {

    private final AdminCheck adminCheck;

    private final UserRepository userRepository;



    @Transactional
    public void changeAuthority(ChangeAuthorityDTO dto) {
        adminCheck.adminPage();
        Long userId = dto.userId();
        User user = userRepository.findById(userId).orElseThrow(
                () -> new EntityNotFoundException("User not found with id: " + userId));
        if(user.getAuthorityType().equals(dto.authorityType())) {
            throw new IllegalArgumentException("User authority type does not match expected authority type");
        }
        user.changeAuthority(dto.authorityType());
        userRepository.save(user);

    }
}
