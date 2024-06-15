package com.example.healthcare.account.repository;

import com.example.healthcare.account.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
