-- 2024.07.26
create table exercise
(
    is_deleted         bit                                                                               not null,
    created_date_time  datetime(6),
    exercise_id        bigint                                                                            not null auto_increment,
    updated_date_time  datetime(6),
    user_id            bigint                                                                            not null,
    name               varchar(191)                                                                      not null,
    description        varchar(3000),
    exercise_body_type enum ('ABS','ARM','BACK','CARDIO','CHEST','ETC','LEG','SHOULDER','WIGHT_LIFTING') not null,
    exercise_tool_type enum ('BABEL','BARE_BODY','DUMBBELL','EASY_BAR','ETC','KETTLE_BELL','MACHINE')    not null,
    primary key (exercise_id)
) engine = InnoDB;

create table exercise_type_relation
(
    created_date_time         datetime(6),
    exercise_id               bigint                        not null,
    exercise_type_relation_id bigint                        not null auto_increment,
    updated_date_time         datetime(6),
    exercise_type             enum ('REPS','TIME','WEIGHT') not null,
    primary key (exercise_type_relation_id)
) engine = InnoDB;

create table user
(
    created_date_time              datetime(6),
    recent_change_status_date_time datetime(6),
    recent_sign_in_date_time       datetime(6),
    sign_up_date_time              datetime(6),
    updated_date_time              datetime(6),
    user_id                        bigint       not null auto_increment,
    email                          varchar(191) not null,
    mobile                         varchar(255),
    name                           varchar(255) not null,
    nickname                       varchar(255) not null,
    password                       varchar(255) not null,
    authority_type                 enum ('CUSTOMER','GUEST','SYSTEM','TRAINER'),
    user_status                    enum ('ACTIVATED','CANCELLED','DEACTIVATED'),
    primary key (user_id)
) engine = InnoDB;

create table user_exercise_log
(
    exercise_count       integer,
    exercise_date        date   not null,
    is_deleted           bit    not null,
    total_reps           decimal(38, 0),
    total_set_count      decimal(38, 0),
    total_time           decimal(38, 0),
    total_weight         decimal(38, 0),
    created_date_time    datetime(6),
    exercise_time        bigint,
    updated_date_time    datetime(6),
    user_exercise_log_id bigint not null auto_increment,
    user_id              bigint not null,
    exercise_time_type   enum ('AM','DEFAULT','PM'),
    primary key (user_exercise_log_id)
) engine = InnoDB;

create table user_exercise_routine
(
    order_number             integer,
    set_count                integer,
    sum_reps                 decimal(38, 0),
    sum_time                 decimal(38, 0),
    sum_weight               decimal(38, 0),
    created_date_time        datetime(6),
    exercise_id              bigint not null,
    rest_time                bigint,
    updated_date_time        datetime(6),
    user_exercise_log_id     bigint not null,
    user_exercise_routine_id bigint not null auto_increment,
    weight_unit_type         enum ('KILOGRAM','POUND'),
    primary key (user_exercise_routine_id)
) engine = InnoDB;

create table user_exercise_routine_setting
(
    is_deleted                       bit    not null,
    created_date_time                datetime(6),
    exercise_id                      bigint not null,
    rest_time                        bigint,
    updated_date_time                datetime(6),
    user_exercise_routine_setting_id bigint not null auto_increment,
    user_id                          bigint not null,
    setting_name                     varchar(255),
    primary key (user_exercise_routine_setting_id)
) engine = InnoDB;

create table user_exercise_set
(
    complete                 bit    not null,
    reps                     integer,
    created_date_time        datetime(6),
    set_number               bigint not null,
    time                     bigint,
    updated_date_time        datetime(6),
    user_exercise_routine_id bigint not null,
    user_exercise_set_id     bigint not null auto_increment,
    weight                   bigint,
    exercise_set_type        enum ('DEFAULT','DROP','FAIL','WARMUP'),
    primary key (user_exercise_set_id)
) engine = InnoDB;

create index IDX55r3luukc7oe20q88depist0m
    on exercise (exercise_body_type, user_id);

alter table user
    add constraint UKob8kqyqqgmefl0aco34akdtpe unique (email);

create index IDXgfy7rlvi2dlyw3v0d35c0o43l
    on user_exercise_log (exercise_date, user_id);

alter table user_exercise_log
    add constraint UKr62lumhns2g80n4miymds7bhq unique (exercise_date, exercise_time_type, user_id);

alter table user_exercise_routine
    add constraint UKqkitc82mjt5xtd6arnk332xm5 unique (user_exercise_routine_id, order_number);

alter table exercise
    add constraint FK5v547fhplc6po1fgoeqk1dl9f
        foreign key (user_id)
            references user (user_id);

alter table exercise_type_relation
    add constraint FKhdfg0clobsfiwkbf7jhfha12j
        foreign key (exercise_id)
            references exercise (exercise_id);

alter table user_exercise_log
    add constraint FKccmblk3u984y91r216x5wyeg2
        foreign key (user_id)
            references user (user_id);

alter table user_exercise_routine
    add constraint FKsrgls9wqm9s97mjlqjb3a0o03
        foreign key (exercise_id)
            references exercise (exercise_id);

alter table user_exercise_routine
    add constraint FK4j4uts2q63ymhlbso02jjo14k
        foreign key (user_exercise_log_id)
            references user_exercise_log (user_exercise_log_id);

alter table user_exercise_routine_setting
    add constraint FKcsmcg11m1p94mr9c06vusl2xl
        foreign key (exercise_id)
            references exercise (exercise_id);

alter table user_exercise_routine_setting
    add constraint FKo7s09gsp3h4ebatyaw68rda3o
        foreign key (user_id)
            references user (user_id);

alter table user_exercise_set
    add constraint FK2v43r77b177fmbajroxfgn6la
        foreign key (user_exercise_routine_id)
            references user_exercise_routine (user_exercise_routine_id);

-- 2024.07.28
alter table user_exercise_routine
    add column is_deleted bit not null;

drop index UKr62lumhns2g80n4miymds7bhq
    on user_exercise_log;

alter table user_exercise_set modify weight double;