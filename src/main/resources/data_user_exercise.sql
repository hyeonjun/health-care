insert into user_exercise_log (created_date_time, exercise_count, exercise_date, exercise_time, exercise_time_type,
                                is_deleted, total_reps, total_set_count, total_time, total_weight, updated_date_time,
                                user_id)
values ('2024-07-28T00:17:42.167+0900', 3, '2024-07-28T00:00:00.000+0900', 5400, 'DEFAULT', false, 130, 11, NULL, 476,
        '2024-07-28T00:17:42.167+0900', 2);

insert into user_exercise_routine (created_date_time, exercise_id, order_number, rest_time, set_count, sum_reps,
                                    sum_time, sum_weight, updated_date_time, user_exercise_log_id, weight_unit_type)
values ('2024-07-28T00:17:42.181+0900', 1, 1, 90, 4, 44, NULL, 230, '2024-07-28T00:17:42.181+0900', 1, 'KILOGRAM');

insert into user_exercise_routine (created_date_time, exercise_id, order_number, rest_time, set_count, sum_reps,
                                    sum_time, sum_weight, updated_date_time, user_exercise_log_id, weight_unit_type)
values ('2024-07-28T00:17:42.184+0900', 5, 2, 80, 4, 46, NULL, 180, '2024-07-28T00:17:42.184+0900', 1, 'KILOGRAM');

insert into user_exercise_routine (created_date_time, exercise_id, order_number, rest_time, set_count, sum_reps,
                                    sum_time, sum_weight, updated_date_time, user_exercise_log_id, weight_unit_type)
values ('2024-07-28T00:17:42.186+0900', 4, 3, 70, 3, 40, NULL, 66, '2024-07-28T00:17:42.186+0900', 1, 'KILOGRAM');

insert into user_exercise_set (complete, created_date_time, exercise_set_type, reps, set_number, time,
                                updated_date_time, user_exercise_routine_id, weight)
values (true, '2024-07-28T00:17:42.187+0900', 'DEFAULT', 15, 1, NULL, '2024-07-28T00:17:42.187+0900', 1, 50);

insert into user_exercise_set (complete, created_date_time, exercise_set_type, reps, set_number, time,
                                updated_date_time, user_exercise_routine_id, weight)
values (true, '2024-07-28T00:17:42.188+0900', 'DEFAULT', 12, 2, NULL, '2024-07-28T00:17:42.188+0900', 1, 60);

insert into user_exercise_set (complete, created_date_time, exercise_set_type, reps, set_number, time,
                                updated_date_time, user_exercise_routine_id, weight)
values (true, '2024-07-28T00:17:42.190+0900', 'DEFAULT', 5, 3, NULL, '2024-07-28T00:17:42.190+0900', 1, 80);

insert into user_exercise_set (complete, created_date_time, exercise_set_type, reps, set_number, time,
                                updated_date_time, user_exercise_routine_id, weight)
values (true, '2024-07-28T00:17:42.191+0900', 'DROP', 12, 4, NULL, '2024-07-28T00:17:42.191+0900', 1, 40);

insert into user_exercise_set (complete, created_date_time, exercise_set_type, reps, set_number, time,
                                updated_date_time, user_exercise_routine_id, weight)
values (true, '2024-07-28T00:17:42.193+0900', 'DEFAULT', 15, 1, NULL, '2024-07-28T00:17:42.193+0900', 2, 40);

insert into user_exercise_set (complete, created_date_time, exercise_set_type, reps, set_number, time,
                                updated_date_time, user_exercise_routine_id, weight)
values (true, '2024-07-28T00:17:42.194+0900', 'DEFAULT', 12, 2, NULL, '2024-07-28T00:17:42.194+0900', 2, 45);

insert into user_exercise_set (complete, created_date_time, exercise_set_type, reps, set_number, time,
                                updated_date_time, user_exercise_routine_id, weight)
values (true, '2024-07-28T00:17:42.195+0900', 'DEFAULT', 7, 3, NULL, '2024-07-28T00:17:42.195+0900', 2, 55);

insert into user_exercise_set (complete, created_date_time, exercise_set_type, reps, set_number, time,
                                updated_date_time, user_exercise_routine_id, weight)
values (true, '2024-07-28T00:17:42.196+0900', 'DROP', 12, 4, NULL, '2024-07-28T00:17:42.196+0900', 2, 40);

insert into user_exercise_set (complete, created_date_time, exercise_set_type, reps, set_number, time,
                                updated_date_time, user_exercise_routine_id, weight)
values (true, '2024-07-28T00:17:42.197+0900', 'DEFAULT', 15, 1, NULL, '2024-07-28T00:17:42.197+0900', 3, 20);

insert into user_exercise_set (complete, created_date_time, exercise_set_type, reps, set_number, time,
                                updated_date_time, user_exercise_routine_id, weight)
values (true, '2024-07-28T00:17:42.198+0900', 'DEFAULT', 15, 2, NULL, '2024-07-28T00:17:42.198+0900', 3, 22);

insert into user_exercise_set (complete, created_date_time, exercise_set_type, reps, set_number, time,
                                updated_date_time, user_exercise_routine_id, weight)
values (true, '2024-07-28T00:17:42.200+0900', 'DEFAULT', 10, 3, NULL, '2024-07-28T00:17:42.200+0900', 3, 24);