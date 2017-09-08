------------------------ create sequences for primary keys -------------
create sequence GR_GEN_DEPARTMENT_ID minvalue 1 start with 1 increment by 1 cache 1;
create sequence GR_GEN_EMPLOYEE_ID minvalue 1 start with 1 increment by 1 cache 1;
create sequence GR_GEN_LANGUAGE_ID minvalue 1 start with 1 increment by 1 cache 1;
create sequence GR_GEN_LANGUAGE_LINK_ID minvalue 1 start with 1 increment by 1 cache 1;
create sequence GR_GEN_SCAN_ID minvalue 1 start with 1 increment by 1 cache 1;
create sequence GR_GEN_USER_ID minvalue 1 start with 1 increment by 1 cache 1;


------------------------ create table ----------------------------------
create table GR_USER (
  user_id number(10) primary key, -- GR_GEN_USER_ID  
  user_name varchar2(250) not null unique,
  user_password varchar2(250) not null,
  user_description varchar2(1000) null,
  is_operator number(1) default 0 not null,
  is_admin number(1) default 0 not null  
);

create table GR_DEPARTMENT (
  department_id number(10) primary key, -- GR_GEN_DEPARTMENT_ID
  department_name varchar2(1000) not null unique
);

create table GR_EMPLOYEE (
  employee_id number(10) primary key, -- GR_GEN_EMPLOYEE_ID      
  department_id number(10) not null,
  employee_name varchar2(250) not null,
  employee_surname varchar2(250) not null,
  employee_position varchar2(1000) not null,
  age number(3) not null,
  salary number(10, 2) not null,
  birthday date not null,
  sex number(1) not null, -- 0-MALE, 1-FEMALE, 2-TRANSGENDER, 2-SOMETHINGELSE
  is_married number(1) not null, -- 0-false, 1-true,
  is_criminal varchar2(1) not null, -- "N"-false, "Y"-true
  employee_descripton varchar2(2000) null,
  -- foreign keys
  foreign key (department_id) references GR_DEPARTMENT (department_id)
);

create table GR_LANGUAGE (
  language_id number(10) primary key,  -- GR_GEN_LANGUAGE_ID
  language_name varchar2(1000) not null unique  
);

create table GR_PERSONNEL_AND_LANGUAGES (
  language_link_id number(10) primary key, -- GR_GEN_LANGUAGE_LINK_ID
  employee_id number(10) not null,
  language_id number(10) not null,
  -- foreign keys
  foreign key (employee_id) references GR_EMPLOYEE (employee_id),
  foreign key (language_id) references GR_LANGUAGE (language_id)
);

create table GR_DEPARTMENT_SCAN (
  scan_id number(10) primary key,  -- GR_GEN_SCAN_ID
  department_id number(10) not null,
  scan_name varchar2(1000) not null,
  scan_ext varchar2(250) not null,
  scan_blob blob not null,
  -- foreign keys
  foreign key (department_id) references GR_DEPARTMENT (department_id)
);