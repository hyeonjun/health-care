package com.example.healthcare.repository.exercise;

import com.example.healthcare.application.account.domain.User;
import com.example.healthcare.application.exercise.domain.UserExerciseLog;
import com.example.healthcare.application.exercise.domain.code.ExerciseTimeType;
import com.example.healthcare.application.exercise.repository.UserExerciseLogRepository;
import com.example.healthcare.application.exercise.repository.param.SearchUserExerciseLogParam;
import com.example.healthcare.application.vo.UserExerciseLogSummaryVO;
import com.example.healthcare.application.vo.UserExerciseLogVO;
import com.example.healthcare.application.vo.UserExerciseRoutineVO;
import com.example.healthcare.application.vo.UserExerciseSetVO;
import com.example.healthcare.util.MockUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDate;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@ActiveProfiles("test")
@AutoConfigureTestDatabase(replace = Replace.NONE)
public class UserExerciseLogRepositoryTest {

  @Autowired
  private UserExerciseLogRepository userExerciseLogRepository;

  @Test
  void existExerciseLogTest() {
    User user = MockUtil.createEntityInstance(User.class);
    MockUtil.setEntityFieldValue(user, "id", 1L);

    UserExerciseLog userExerciseLog = MockUtil.createEntityInstance(UserExerciseLog.class);
    MockUtil.setEntityFieldValue(userExerciseLog, "id", 1L);
    MockUtil.setEntityFieldValue(userExerciseLog, "exerciseDate", LocalDate.now());

    SearchUserExerciseLogParam param = SearchUserExerciseLogParam.valueOf(userExerciseLog, Set.of(ExerciseTimeType.values()));
    Boolean result = userExerciseLogRepository.existExerciseLog(user, param);
    assertThat(result).isNotNull();
  }

  @Test
  void findExerciseLogMonthlyTest() {
    User user = MockUtil.createEntityInstance(User.class);
    MockUtil.setEntityFieldValue(user, "id", 1L);

    Integer year = 2024;
    Integer month = 7;

    Page<UserExerciseLogVO> result = userExerciseLogRepository.findExerciseLogMonthly(
      user, year, month, PageRequest.of(0, 50));
    assertThat(result).isNotNull();
  }

  @Test
  void findExerciseLogDailyTest() {
    User user = MockUtil.createEntityInstance(User.class);
    MockUtil.setEntityFieldValue(user, "id", 1L);

    Integer year = 2024;
    Integer month = 7;
    Integer day = 28;

    Page<UserExerciseLogSummaryVO> result = userExerciseLogRepository.findExerciseLogDaily(
      user, year, month, day, PageRequest.of(0, 50));
    assertThat(result).isNotNull();
  }

  @Test
  void findExerciseRoutineAllByLogTest() {
    UserExerciseLog log = MockUtil.createEntityInstance(UserExerciseLog.class);
    MockUtil.setEntityFieldValue(log, "id", 1L);

    Page<UserExerciseRoutineVO> result = userExerciseLogRepository.findExerciseRoutineAllByLog(
      log, PageRequest.of(0, 1000));
    assertThat(result).isNotNull();
  }

  @Test
  void findExerciseSetAllByLogTest() {
    UserExerciseLog log = MockUtil.createEntityInstance(UserExerciseLog.class);
    MockUtil.setEntityFieldValue(log, "id", 1L);

    Page<UserExerciseSetVO> result = userExerciseLogRepository.findExerciseSetAllByLog(
      log, PageRequest.of(0, 1000));
    assertThat(result).isNotNull();
  }

}
