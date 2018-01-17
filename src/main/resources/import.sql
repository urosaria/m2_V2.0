insert into user(num, agree_yn, email, id, name, password, phone, status, create_date) values (1, 'Y', 'test@11.11', 'test', '이름임', '$2a$10$gFWj.NuYD4pqXvamuDqqquJr285Vx2KoagWmfpygd7cOUD6rJ6KGq' ,'0101111111', 'S', now());

insert into role(id, name) VALUES (1,'ADMIN');
insert into role(id, name) VALUES (2,'USER');

insert into user_role(user_num, role_id) VALUES (1,1);

insert into board_master(id, name, reply_yn, status, skin_name) values (1, '공지사항', 'N', 'S', 'notice');
insert into board_master(id, name, reply_yn, status, skin_name) values (2, 'Q&A', 'Y', 'S', 'qna');

insert into board(id, user_num, title, contents, status, create_date, read_count, board_master_id) values (1, 1, 'title', 'contents', 'S', now(), 0, 1);
insert into board(id, user_num, title, contents, status, create_date, read_count, board_master_id) values (2, 1, '질문', 'contents', 'S', now(), 0, 2);
insert into board(id, user_num, title, contents, status, create_date, read_count, board_master_id) values (3, 1, '질문', 'contents', 'S', now(), 0, 2);

insert into board_reply(id, user_num, title, contents, status, create_date, read_count, board_id) values (1, 1, '질문', 'contents', 'S', now(), 0, 2);