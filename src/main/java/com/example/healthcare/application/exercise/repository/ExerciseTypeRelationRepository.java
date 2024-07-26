package com.example.healthcare.application.exercise.repository;

import com.example.healthcare.application.exercise.domain.Exercise;
import com.example.healthcare.application.exercise.domain.ExerciseTypeRelation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ExerciseTypeRelationRepository extends JpaRepository<ExerciseTypeRelation, Long> {

  List<ExerciseTypeRelation> findAllByExercise(Exercise exercise);
}
