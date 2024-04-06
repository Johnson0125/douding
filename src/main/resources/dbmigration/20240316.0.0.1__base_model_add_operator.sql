-- apply changes
create table sys_resource (
  id                            bigint auto_increment not null,
  resource_name                 varchar(20) not null comment 'resource name',
  url                           varchar(50) not null comment 'resource url',
  url_status                    integer(1) not null comment 'url status',
  version                       bigint not null,
  when_created                  datetime(6) not null,
  when_modified                 datetime(6) not null,
  who_created                   varchar(255) not null,
  who_modified                  varchar(255) not null,
  constraint pk_sys_resource primary key (id)
);

create table sys_role (
  id                            bigint auto_increment not null,
  role_name                     varchar(20) not null comment 'role name',
  role_status                   integer(1) not null comment 'role status',
  version                       bigint not null,
  when_created                  datetime(6) not null,
  when_modified                 datetime(6) not null,
  who_created                   varchar(255) not null,
  who_modified                  varchar(255) not null,
  constraint pk_sys_role primary key (id)
);

create table sys_role_resource (
  id                            bigint auto_increment not null,
  role_id                       bigint(19) not null comment 'role id',
  resource_id                   bigint(19) not null comment 'resource id',
  version                       bigint not null,
  when_created                  datetime(6) not null,
  when_modified                 datetime(6) not null,
  who_created                   varchar(255) not null,
  who_modified                  varchar(255) not null,
  constraint pk_sys_role_resource primary key (id)
);

create table sys_user_role (
  id                            bigint auto_increment not null,
  user_id                       bigint(19) not null comment 'user id',
  role_id                       bigint(19) not null comment 'role id',
  version                       bigint not null,
  when_created                  datetime(6) not null,
  when_modified                 datetime(6) not null,
  who_created                   varchar(255) not null,
  who_modified                  varchar(255) not null,
  constraint pk_sys_user_role primary key (id)
);

-- apply alter tables
alter table user_info modify telephone varchar(255);
