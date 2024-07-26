package com.example.healthcare.application.vo;

import com.example.healthcare.application.account.domain.User;
import com.example.healthcare.application.exercise.domain.Exercise;
import com.example.healthcare.application.exercise.domain.ExerciseTypeRelation;
import com.example.healthcare.application.exercise.domain.code.ExerciseToolType;
import com.example.healthcare.application.exercise.domain.code.ExerciseType;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class ExerciseDetailVO extends ExerciseVO {

  private String description;
  private ExerciseToolType exerciseToolType;
  private List<ExerciseType> exerciseTypes = new ArrayList<>();

  public static ExerciseDetailVO valueOf(Exercise exercise, List<ExerciseTypeRelation> exerciseTypeRelations) {
    ExerciseDetailVO vo = new ExerciseDetailVO();
    vo.setId(exercise.getId());
    vo.setName(exercise.getName());
    vo.setDeleted(exercise.isDeleted());
    vo.setExerciseBodyType(exercise.getBodyType());
    vo.setExerciseToolType(exercise.getToolType());
    vo.setDescription(exercise.getDescription());

    User user = exercise.getCreatedUser();
    vo.setUserId(user.getId());
    vo.setAuthorityType(user.getAuthorityType());

    List<ExerciseType> targetExerciseTypes = exerciseTypeRelations
      .stream()
      .map(ExerciseTypeRelation::getExerciseType)
      .toList();
    vo.setExerciseTypes(targetExerciseTypes);

    return vo;
  }

}
