package com.example.healthcare.application.exercise.repository;

import com.example.healthcare.application.exercise.domain.UserExerciseSet;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserExerciseSetRepository extends JpaRepository<UserExerciseSet, Long> {

  List<UserExerciseSet> findAllByUserExerciseRoutineIdIn(List<Long> routineIds);
}
