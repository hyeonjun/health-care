package com.example.healthcare.application.exercise.repository;

import com.example.healthcare.application.exercise.domain.Exercise;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ExerciseRepository extends JpaRepository<Exercise, Long>, ExerciseRepositoryCustom {

  List<Exercise> findAllByIdIn(List<Long> ids);

}
