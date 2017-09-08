insert into GR_DEPARTMENT(department_id, department_name) values (GR_GEN_DEPARTMENT_ID.Nextval, 'Транспортный цех');
insert into GR_DEPARTMENT(department_id, department_name) values (GR_GEN_DEPARTMENT_ID.Nextval, 'Юридическая служба');
insert into GR_DEPARTMENT(department_id, department_name) values (GR_GEN_DEPARTMENT_ID.Nextval, 'Отдел тестирования ПО');
insert into GR_DEPARTMENT(department_id, department_name) values (GR_GEN_DEPARTMENT_ID.Nextval, 'Бухгалтерия');
insert into GR_DEPARTMENT(department_id, department_name) values (GR_GEN_DEPARTMENT_ID.Nextval, 'Отдел разработки ПО');

insert into GR_USER (user_id, user_name, user_password, user_description, is_operator, is_admin)
values (GR_GEN_USER_ID.Nextval, 'barin', '$2a$10$5f8KnEfO1uaHCGG5AYsWkuCiGAf56NRfGlcctOfoFsiQ7HXGzd.QW', 'пароль: barin', 1, 1);

insert into GR_USER (user_id, user_name, user_password, user_description, is_operator, is_admin)
values (GR_GEN_USER_ID.Nextval, 'operator', '$2a$10$8rJ/qbfTOUdfKulrj3xJwOhgUQkDx.IstSbWkDzngWa.LkTPBCM8y', 'пароль: operator', 1, 0);

insert into GR_USER (user_id, user_name, user_password, user_description, is_operator, is_admin)
values (GR_GEN_USER_ID.Nextval, 'admin', '$2a$10$sBUZ2R1bSeD3xY0WzE0Fgeh9K6C1j9mSGAN5B0ugtGfEME6n2YhVK', 'пароль: admin', 0, 1);

