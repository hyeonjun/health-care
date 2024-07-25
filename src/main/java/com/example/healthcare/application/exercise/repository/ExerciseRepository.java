package com.example.healthcare.application.exercise.repository;

import com.example.healthcare.application.exercise.domain.Exercise;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ExerciseRepository extends JpaRepository<Exercise, Long> {
}
