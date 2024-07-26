INSERT INTO user (created_date_time, updated_date_time,
                  recent_change_status_date_time, recent_sign_in_date_time, sign_up_date_time,
                  user_id, email, mobile, name, nickname, password, authority_type, user_status)
VALUES ('2024-07-26 01:26:13.309388', '2024-07-26 01:26:13.309414',
        '2024-07-26 01:26:13.303395', null, '2024-07-26 01:26:13.303387',
        1, 'system@admin.com', '010-1234-1234', 'system', 'system',
        '{bcrypt}$2a$10$eBNnaQl1GqptsmmTMa2qwuOWMFn5dc5tWAuczrmGiRSb/iiH1B35m', 'SYSTEM', 'ACTIVATED');

INSERT INTO user (created_date_time, recent_change_status_date_time, recent_sign_in_date_time,
                  sign_up_date_time, updated_date_time, user_id, email, mobile, name, nickname, password,
                  authority_type, user_status)
VALUES ('2024-07-26 16:47:00.408847', '2024-07-26 16:47:00.399987', '2024-07-26 16:50:34.731663',
        '2024-07-26 16:47:00.399960', '2024-07-26 16:50:34.732032', 2, 'user1@test.com', '010-1234-1234', 'test1',
        'test1', '{bcrypt}$2a$10$Nf/ApuLDYvDOse7UcNsTY.gwBEV9fSNsepG3UzlQaXDrTl.jdkv6.', 'CUSTOMER', 'ACTIVATED');

INSERT INTO user (created_date_time, recent_change_status_date_time, recent_sign_in_date_time,
                  sign_up_date_time, updated_date_time, user_id, email, mobile, name, nickname, password,
                  authority_type, user_status)
VALUES ('2024-07-26 16:47:05.404559', '2024-07-26 16:47:05.404238', null, '2024-07-26 16:47:05.404229',
        '2024-07-26 16:47:05.404574', 3, 'user2@test.com', '010-1234-1234', 'test2', 'test2',
        '{bcrypt}$2a$10$NVQlnB2zc0tPkP1gYp4iNuTrBuJOQvc7cMHM74xrisk.tFr/n2Qnm', 'CUSTOMER', 'ACTIVATED');

INSERT INTO user (created_date_time, recent_change_status_date_time, recent_sign_in_date_time,
                  sign_up_date_time, updated_date_time, user_id, email, mobile, name, nickname, password,
                  authority_type, user_status)
VALUES ('2024-07-26 16:47:10.628699', '2024-07-26 16:47:10.628397', null, '2024-07-26 16:47:10.628383',
        '2024-07-26 16:47:10.628718', 4, 'user3@test.com', '010-1234-1234', 'test3', 'test3',
        '{bcrypt}$2a$10$HRPVKArU77hhb15uFHiIfuAmiq4Zi9GU4KOCz.nshlC54GeN9txjm', 'CUSTOMER', 'ACTIVATED');

INSERT INTO user (created_date_time, recent_change_status_date_time, recent_sign_in_date_time,
                  sign_up_date_time, updated_date_time, user_id, email, mobile, name, nickname, password,
                  authority_type, user_status)
VALUES ('2024-07-26 16:55:48.702125', '2024-07-26 16:55:48.701890', '2024-07-26 16:55:53.996701',
        '2024-07-26 16:55:48.701880', '2024-07-26 16:55:53.997055', 5, 'user4@test.com', '010-1234-1234', 'test4',
        'test4', '{bcrypt}$2a$10$tGZAVSlFlBkYtHPSnSMpm.x11rTZ4bULKKRpNyjp9NXxShHOvlNIS', 'CUSTOMER', 'ACTIVATED');
