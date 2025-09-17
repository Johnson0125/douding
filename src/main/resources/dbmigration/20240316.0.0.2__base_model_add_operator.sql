-- apply alter tables
alter table sys_resource add column record_modification_version bigint not null;
alter table sys_role add column record_modification_version bigint not null;
alter table sys_role_resource add column record_modification_version bigint not null;
alter table sys_user_role add column record_modification_version bigint not null;
alter table user_info add column record_modification_version bigint not null;

alter table sys_resource drop column version;
alter table sys_role drop column version;
alter table sys_role_resource drop column version;
alter table sys_user_role drop column version;
alter table user_info drop column version;
