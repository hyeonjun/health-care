package com.example.healthcare.application.exercise.repository;

import com.example.healthcare.application.exercise.domain.UserExerciseLog;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserExerciseLogRepository extends JpaRepository<UserExerciseLog, Long>,
  UserExerciseLogRepositoryCustom {
}
