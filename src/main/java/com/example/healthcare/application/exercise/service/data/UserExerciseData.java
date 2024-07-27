package com.example.healthcare.application.exercise.service.data;

import com.example.healthcare.application.exercise.controller.dto.CreateUserExerciseSetDTO;
import com.example.healthcare.application.exercise.domain.UserExerciseSet;
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

    public void update(CreateUserExerciseSetDTO setDTO) {
      setCount++;
      sumWeight = CalculatorUtil.add(sumWeight, setDTO.weight());
      sumReps = CalculatorUtil.add(sumReps, setDTO.reps());
      sumTime = CalculatorUtil.add(sumTime, setDTO.time());
    }
  }


}
