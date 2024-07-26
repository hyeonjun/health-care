INSERT INTO healthCare.exercise (is_deleted, created_date_time, exercise_id, updated_date_time, user_id, name,
                                 description, exercise_body_type, exercise_tool_type)
VALUES (false, '2024-07-26 23:08:58.595784', 1, '2024-07-26 23:08:58.595800', 1, '랫풀다운',
        '랫풀다운 머신으로 바를 잡고 겨드랑이 쪽으로 팔꿈치를 강하게 아래로 내리면서 진행', 'BACK', 'MACHINE');

INSERT INTO healthCare.exercise_type_relation (created_date_time, exercise_id, exercise_type_relation_id,
                                               updated_date_time, exercise_type)
VALUES ('2024-07-26 23:08:58.604408', 1, 1, '2024-07-26 23:08:58.604421', 'WEIGHT');
INSERT INTO healthCare.exercise_type_relation (created_date_time, exercise_id, exercise_type_relation_id,
                                               updated_date_time, exercise_type)
VALUES ('2024-07-26 23:08:58.606708', 1, 2, '2024-07-26 23:08:58.606719', 'REPS');

--

INSERT INTO healthCare.exercise (is_deleted, created_date_time, exercise_id, updated_date_time, user_id, name,
                                 description, exercise_body_type, exercise_tool_type)
VALUES (false, '2024-07-26 23:17:48.118987', 2, '2024-07-26 23:17:48.119031', 1, '백스쿼트',
        '1. 다리를 어깨너비만큼 벌리고, 바벨을 등 뒤 어깨 윙 올려줍니다. 2. 상체가 앞으로 숙여지지 않도록 코어에 힘을 준 상태에 엉덩이를 뒤로 빼며 앉습니다. 3. 상체의 자세를 유지하면서, 발바닥으로 지면을 밀고 일어납니다.',
        'LEG', 'BABEL');

INSERT INTO healthCare.exercise_type_relation (created_date_time, exercise_id, exercise_type_relation_id,
                                               updated_date_time, exercise_type)
VALUES ('2024-07-26 23:17:48.130375', 2, 3, '2024-07-26 23:17:48.130391', 'WEIGHT');
INSERT INTO healthCare.exercise_type_relation (created_date_time, exercise_id, exercise_type_relation_id,
                                               updated_date_time, exercise_type)
VALUES ('2024-07-26 23:17:48.142973', 2, 4, '2024-07-26 23:17:48.142988', 'REPS');

--

INSERT INTO healthCare.exercise (is_deleted, created_date_time, exercise_id, updated_date_time, user_id, name,
                                 description, exercise_body_type, exercise_tool_type)
VALUES (false, '2024-07-26 23:25:16.543734', 3, '2024-07-26 23:25:16.543748', 2, '프론트 스쿼트',
        '1. 다리를 어깨너비만큼 벌리고, 바벨을 등 뒤 어깨 윙 올려줍니다. 2. 상체가 앞으로 숙여지지 않도록 코어에 힘을 준 상태에 엉덩이를 뒤로 빼며 앉습니다. 3. 상체의 자세를 유지하면서, 발바닥으로 지면을 밀고 일어납니다.',
        'LEG', 'BABEL');

INSERT INTO healthCare.exercise_type_relation (created_date_time, exercise_id, exercise_type_relation_id,
                                               updated_date_time, exercise_type)
VALUES ('2024-07-26 23:25:16.547443', 3, 5, '2024-07-26 23:25:16.547462', 'WEIGHT');
INSERT INTO healthCare.exercise_type_relation (created_date_time, exercise_id, exercise_type_relation_id,
                                               updated_date_time, exercise_type)
VALUES ('2024-07-26 23:25:16.550142', 3, 6, '2024-07-26 23:25:16.550151', 'REPS');

--

INSERT INTO healthCare.exercise (is_deleted, created_date_time, exercise_id, updated_date_time, user_id, name,
                                 description, exercise_body_type, exercise_tool_type)
VALUES (false, '2024-07-27 02:34:04.649906', 4, '2024-07-27 02:34:04.649920', 2, '원암덤벨로우', '', 'BACK', 'DUMBBELL');

INSERT INTO healthCare.exercise_type_relation (created_date_time, exercise_id, exercise_type_relation_id,
                                               updated_date_time, exercise_type)
VALUES ('2024-07-27 02:34:04.658776', 4, 7, '2024-07-27 02:34:04.658792', 'WEIGHT');
INSERT INTO healthCare.exercise_type_relation (created_date_time, exercise_id, exercise_type_relation_id,
                                               updated_date_time, exercise_type)
VALUES ('2024-07-27 02:34:04.661935', 4, 8, '2024-07-27 02:34:04.661948', 'REPS');

--

INSERT INTO healthCare.exercise (is_deleted, created_date_time, exercise_id, updated_date_time, user_id, name,
                                 description, exercise_body_type, exercise_tool_type)
VALUES (false, '2024-07-27 02:36:08.642699', 5, '2024-07-27 02:36:08.642723', 1, '시티드 로우', '', 'BACK', 'MACHINE');

INSERT INTO healthCare.exercise_type_relation (created_date_time, exercise_id, exercise_type_relation_id,
                                               updated_date_time, exercise_type)
VALUES ('2024-07-27 02:36:08.646145', 5, 9, '2024-07-27 02:36:08.646163', 'WEIGHT');
INSERT INTO healthCare.exercise_type_relation (created_date_time, exercise_id, exercise_type_relation_id,
                                               updated_date_time, exercise_type)
VALUES ('2024-07-27 02:36:08.648057', 5, 10, '2024-07-27 02:36:08.648065', 'REPS');

--

INSERT INTO healthCare.exercise (is_deleted, created_date_time, exercise_id, updated_date_time, user_id, name,
                                 description, exercise_body_type, exercise_tool_type)
VALUES (true, '2024-07-27 02:44:06.113990', 6, '2024-07-27 02:46:41.781478', 3, '스미스 머신 밀리터리 프레스', '', 'SHOULDER',
        'MACHINE');

INSERT INTO healthCare.exercise_type_relation (created_date_time, exercise_id, exercise_type_relation_id,
                                               updated_date_time, exercise_type)
VALUES ('2024-07-27 02:46:41.777193', 6, 13, '2024-07-27 02:46:41.777210', 'WEIGHT');
INSERT INTO healthCare.exercise_type_relation (created_date_time, exercise_id, exercise_type_relation_id,
                                               updated_date_time, exercise_type)
VALUES ('2024-07-27 02:46:41.779663', 6, 14, '2024-07-27 02:46:41.779675', 'REPS');
