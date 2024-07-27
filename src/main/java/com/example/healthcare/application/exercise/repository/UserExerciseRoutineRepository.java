package com.example.healthcare.application.exercise.repository;

import com.example.healthcare.application.exercise.domain.UserExerciseRoutine;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserExerciseRoutineRepository extends JpaRepository<UserExerciseRoutine, Long> {
}
