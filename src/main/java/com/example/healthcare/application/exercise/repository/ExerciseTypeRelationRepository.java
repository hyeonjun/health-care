package com.example.healthcare.application.exercise.repository;

import com.example.healthcare.application.exercise.domain.Exercise;
import com.example.healthcare.application.exercise.domain.ExerciseTypeRelation;
import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface ExerciseTypeRelationRepository extends JpaRepository<ExerciseTypeRelation, Long> {

  List<ExerciseTypeRelation> findAllByExercise(Exercise exercise);

  @Transactional
  @Modifying
  @Query("DELETE FROM ExerciseTypeRelation etr WHERE etr.exercise.id = :id")
  void deleteAllByExerciseId(@Param("id") Long id);
}
