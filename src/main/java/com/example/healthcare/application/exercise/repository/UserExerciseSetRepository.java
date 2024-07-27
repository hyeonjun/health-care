package com.example.healthcare.application.exercise.repository;

import com.example.healthcare.application.exercise.domain.UserExerciseSet;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserExerciseSetRepository extends JpaRepository<UserExerciseSet, Long> {
}
