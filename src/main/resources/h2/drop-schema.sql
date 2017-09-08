-- очищаем полностью базу данных
drop sequence GR_GEN_DEPARTMENT_ID if exists;
drop sequence GR_GEN_EMPLOYEE_ID if exists;
drop sequence GR_GEN_LANGUAGE_ID if exists;
drop sequence GR_GEN_LANGUAGE_LINK_ID if exists;
drop sequence GR_GEN_SCAN_ID if exists;
drop sequence GR_GEN_USER_ID if exists;

drop table GR_USER if exists;
drop table GR_DEPARTMENT if exists;
drop table GR_EMPLOYEE if exists;
drop table GR_LANGUAGE if exists;
drop table GR_PERSONNEL_AND_LANGUAGES if exists;
drop table GR_DEPARTMENT_SCAN if exists;
