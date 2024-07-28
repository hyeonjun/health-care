package com.example.healthcare.application.exercise.repository;

import com.example.healthcare.application.exercise.domain.UserExerciseRoutine;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

public interface UserExerciseRoutineRepository extends JpaRepository<UserExerciseRoutine, Long> {

  @Transactional
  @Modifying
  @Query("UPDATE UserExerciseRoutine uer " +
    "SET uer.isDeleted = true " +
    "WHERE uer.userExerciseLog.id = :id ")
  void deleteAllByUserExerciseLogIdQuery(Long id);

}
