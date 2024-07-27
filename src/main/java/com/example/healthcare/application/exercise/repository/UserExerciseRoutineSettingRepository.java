package com.example.healthcare.application.exercise.repository;

import com.example.healthcare.application.exercise.domain.UserExerciseRoutineSetting;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserExerciseRoutineSettingRepository extends JpaRepository<UserExerciseRoutineSetting, Long> {
}
