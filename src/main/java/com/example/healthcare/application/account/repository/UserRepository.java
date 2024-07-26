package com.example.healthcare.application.account.repository;

import com.example.healthcare.application.account.domain.User;
import com.example.healthcare.application.account.domain.code.UserStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long>, UserRepositoryCustom {
    Optional<User> findByIdAndUserStatusIs(Long id, UserStatus userStatus);
    Optional<User> findByEmail(String email);
    Optional<User> findByEmailAndUserStatusIsNot(String email, UserStatus userStatus);
}
