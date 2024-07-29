package com.example.healthcare.application.exercise.service.data;

import com.example.healthcare.application.exercise.controller.dto.ExerciseSetDTO;
import com.example.healthcare.application.exercise.domain.UserExerciseSet;
import com.example.healthcare.application.exercise.domain.code.WeightUnitType;
import com.example.healthcare.util.CalculatorUtil;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

public class UserExerciseData {

  public static class UserExerciseLogData {
    public int exerciseCount = 0;
    public BigInteger totalSetCount = null;
    public BigInteger totalWeight = null;
    public BigInteger totalReps = null;
    public BigInteger totalTime = null;
    public List<UserExerciseSet> setEntityList = new ArrayList<>();

    public void update(UserExerciseRoutineData routineData) {
      exerciseCount++;
      totalSetCount = CalculatorUtil.add(totalSetCount, routineData.setCount);
      totalWeight = CalculatorUtil.add(totalWeight, routineData.sumWeight);
      totalReps = CalculatorUtil.add(totalReps, routineData.sumReps);
      totalTime = CalculatorUtil.add(totalTime, routineData.sumTime);
    }
  }

  public static class UserExerciseRoutineData {
    public int setCount = 0;
    public BigInteger sumWeight = null;
    public BigInteger sumReps = null;
    public BigInteger sumTime = null;

    public void update(ExerciseSetDTO setDTO, WeightUnitType weightUnitType) {
      setCount++;

      double weight = setDTO.getWeight();
      if (WeightUnitType.POUND.equals(weightUnitType)) {
        weight = WeightUnitType.lbsToKg(setDTO.getWeight());
      }

      if (setDTO.getReps() != null) {
        weight *= setDTO.getReps();
      }

      sumWeight = CalculatorUtil.add(sumWeight, Math.round(weight));
      sumReps = CalculatorUtil.add(sumReps, setDTO.getReps());
      sumTime = CalculatorUtil.add(sumTime, setDTO.getTime());
    }
  }


}
