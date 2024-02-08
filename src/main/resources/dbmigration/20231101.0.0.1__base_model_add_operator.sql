-- apply changes
create table user_info (
  id                            bigint not null,
  username                      varchar(50) not null comment 'username',
  telephone                     bigint comment 'telephone',
  gender                        integer(2) comment 'gender',
  salt                          bigint(12),
  email                         varchar(255) comment 'email',
  user_status                   integer comment 'user''s status info',
  role                          integer comment 'role of user',
  password                      varchar(255),
  version                       bigint not null,
  when_created                  datetime(6) not null,
  when_modified                 datetime(6) not null,
  who_created                   varchar(255) not null,
  who_modified                  varchar(255) not null,
  constraint pk_user_info primary key (id)
);

