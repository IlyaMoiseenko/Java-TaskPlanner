insert into tb_user (name, username, password)
values ('Ilya Moiseenko', 'Markalfox', '$2a$10$3LxVPmsMVdptTOokiV.R8eG2Nhb0sB8waMyb4Lu7Ogcs/M.AbMVpS'),
       ('Karina Maslova', 'Karina', '$2a$10$3LxVPmsMVdptTOokiV.R8eG2Nhb0sB8waMyb4Lu7Ogcs/M.AbMVpS');

insert into tb_task (title, description, status, expiration_date)
values ('Buy cheese', null, 'TODO', '2023-01-29 12:00:00'),
       ('Do homework', 'Math', 'IN_PROGRESS', '2023-01-31 00:00:00'),
       ('Clean rooms', null, 'DONE', null),
       ('Call Mike', 'Ask about meeting', 'TODO', '2023-02-01 12:00:00');

insert into tb_user_tb_task (task_id, user_id)
values (1, 2),
       (2, 2),
       (3, 2),
       (4, 1);

insert into tb_user_role (user_id, role)
values (1, 'ROLE_ADMIN'),
       (1, 'ROLE_USER'),
       (2, 'ROLE_USER');

