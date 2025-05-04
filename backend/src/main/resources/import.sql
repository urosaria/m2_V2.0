-- #회원
insert into m2_user(num, agree_yn, email, id, name, password, phone, status, create_date) values (1, 'Y', 'test@11.11', 'admin', '관리자', '$2a$10$gFWj.NuYD4pqXvamuDqqquJr285Vx2KoagWmfpygd7cOUD6rJ6KGq' ,'0101111111', 'S', now());
insert into m2_user(num, agree_yn, email, id, name, password, phone, status, create_date) values (2, 'Y', 'test@11.11', 'user', '사용자', '$2a$10$gFWj.NuYD4pqXvamuDqqquJr285Vx2KoagWmfpygd7cOUD6rJ6KGq' ,'0101111111', 'S', now());
--insert into m2_user(num, agree_yn, email, id, name, password, phone, status, create_date) values (3, 'Y', 'test@11.11', 'user2', '관리자', '$2a$10$gFWj.NuYD4pqXvamuDqqquJr285Vx2KoagWmfpygd7cOUD6rJ6KGq' ,'0101111111', 'S', now());
--insert into m2_user(num, agree_yn, email, id, name, password, phone, status, create_date) values (4, 'Y', 'test@11.11', 'user3', '사용자', '$2a$10$gFWj.NuYD4pqXvamuDqqquJr285Vx2KoagWmfpygd7cOUD6rJ6KGq' ,'0101111111', 'S', now());
--insert into m2_user(num, agree_yn, email, id, name, password, phone, status, create_date) values (5, 'Y', 'test@11.11', 'user4', '관리자', '$2a$10$gFWj.NuYD4pqXvamuDqqquJr285Vx2KoagWmfpygd7cOUD6rJ6KGq' ,'0101111111', 'S', now());
--insert into m2_user(num, agree_yn, email, id, name, password, phone, status, create_date) values (6, 'Y', 'test@11.11', 'user5', '사용자', '$2a$10$gFWj.NuYD4pqXvamuDqqquJr285Vx2KoagWmfpygd7cOUD6rJ6KGq' ,'0101111111', 'S', now());
--insert into m2_user(num, agree_yn, email, id, name, password, phone, status, create_date) values (7, 'Y', 'test@11.11', 'user6', '관리자', '$2a$10$gFWj.NuYD4pqXvamuDqqquJr285Vx2KoagWmfpygd7cOUD6rJ6KGq' ,'0101111111', 'S', now());
--insert into m2_user(num, agree_yn, email, id, name, password, phone, status, create_date) values (8, 'Y', 'test@11.11', 'user7', '사용자', '$2a$10$gFWj.NuYD4pqXvamuDqqquJr285Vx2KoagWmfpygd7cOUD6rJ6KGq' ,'0101111111', 'S', now());
--insert into m2_user(num, agree_yn, email, id, name, password, phone, status, create_date) values (9, 'Y', 'test@11.11', 'user8', '관리자', '$2a$10$gFWj.NuYD4pqXvamuDqqquJr285Vx2KoagWmfpygd7cOUD6rJ6KGq' ,'0101111111', 'S', now());
--insert into m2_user(num, agree_yn, email, id, name, password, phone, status, create_date) values (10, 'Y', 'test@11.11', 'user9', '사용자', '$2a$10$gFWj.NuYD4pqXvamuDqqquJr285Vx2KoagWmfpygd7cOUD6rJ6KGq' ,'0101111111', 'S', now());
--insert into m2_user(num, agree_yn, email, id, name, password, phone, status, create_date) values (11, 'Y', 'test@11.11', 'user10', '관리자', '$2a$10$gFWj.NuYD4pqXvamuDqqquJr285Vx2KoagWmfpygd7cOUD6rJ6KGq' ,'0101111111', 'S', now());
--insert into m2_user(num, agree_yn, email, id, name, password, phone, status, create_date) values (12, 'Y', 'test@11.11', 'user11', '사용자', '$2a$10$gFWj.NuYD4pqXvamuDqqquJr285Vx2KoagWmfpygd7cOUD6rJ6KGq' ,'0101111111', 'S', now());

insert into m2_role(id, name) VALUES (1,'ADMIN');
insert into m2_role(id, name) VALUES (2,'USER');

insert into m2_user_role(user_num, role_id) VALUES (1,1);
insert into m2_user_role(user_num, role_id) VALUES (2,2);
--insert into m2_user_role(user_num, role_id) VALUES (3,2);
--insert into m2_user_role(user_num, role_id) VALUES (4,2);
--insert into m2_user_role(user_num, role_id) VALUES (5,2);
--insert into m2_user_role(user_num, role_id) VALUES (6,2);
--insert into m2_user_role(user_num, role_id) VALUES (7,2);
--insert into m2_user_role(user_num, role_id) VALUES (8,2);
--insert into m2_user_role(user_num, role_id) VALUES (9,2);
--insert into m2_user_role(user_num, role_id) VALUES (10,2);
--insert into m2_user_role(user_num, role_id) VALUES (11,2);
--insert into m2_user_role(user_num, role_id) VALUES (12,2);




-- #게시판
insert into m2_board_master(id, name, reply_yn, status, skin_name) values (1, '공지사항', 'N', 'S', 'notice');
insert into m2_board_master(id, name, reply_yn, status, skin_name) values (2, 'Q&A', 'Y', 'S', 'qna');

insert into m2_board(id, user_num, title, contents, status, create_date, read_count, board_master_id) values (1, 1, '건물타입 3가지가 추가되었습니다', '공지사항 내용입니다', 'S', now(), 0, 1);
insert into m2_board(id, user_num, title, contents, status, create_date, read_count, board_master_id) values (2, 1, '커뮤니티 질문 1', '내용입니다', 'S', now(), 0, 2);
insert into m2_board(id, user_num, title, contents, status, create_date, read_count, board_master_id) values (3, 2, '커뮤니티 질문 2', '내용입니다', 'S', now(), 0, 2);

insert into m2_board_reply(id, user_num, title, contents, status, create_date, read_count, board_id) values (1, 1, '답변제목', '답변내용', 'S', now(), 0, 2);



-- #판넬자동산출
insert into m2_est_structure (id, city_name, create_date, eaves_length, height, length, place_name, rear_truss_height, structure_type, truss_height, user_num, width) values (1, '30', now(), 0,6300,15400, 'A트러스 old', 0, 'AT', 1900, 2, 6800);

insert into m2_est_structure_detail (id, canopy_yn, ceiling_paper, ceiling_thick, ceiling_type, ceiling_yn, create_date, door_yn, downpipe_yn, gucci, gucci_amount, inside_wall_paper, inside_wall_thick, inside_wall_type, inside_wall_yn, outside_wall_paper, outside_wall_thick, outside_wall_type, roof_paper, roof_thick, roof_type, window_yn, structure_id)values (1,'Y','E1','125','E','Y',now(),'Y','Y','100','8','E1','50','E','Y','G1',50,'G','W1','175','W','Y',1);

insert into m2_est_canopy (id, amount, length, structure_detail_id) values (1, 1, 4400, 1);
insert into m2_est_canopy (id, amount, length, structure_detail_id) values (2, 2, 1300, 1);

insert into m2_est_ceiling (id, amount, height, length, structure_detail_id) values (1, 2, 4000, 6000, 1);

insert into m2_est_door (id, amount, height, door_type, width, structure_detail_id, door_type_sub) values (1, 1, 2100, 'O', 900, 1, 'S');
insert into m2_est_door (id, amount, height, door_type, width, structure_detail_id, door_type_sub) values (2, 2, 1300, 'O', 2100, 1, 'H');

-- 선홈통 삭제예정
--insert into m2_est_downpipe (id, amount, height, width, structure_detail_id) values (1, 1, 100, 100, 1);

insert into m2_est_inside_wall (id, amount, height, length, structure_detail_id) values (1, 1, 600, 500, 1);
insert into m2_est_inside_wall (id, amount, height, length, structure_detail_id) values (2, 2, 600, 600, 1);
insert into m2_est_inside_wall (id, amount, height, length, structure_detail_id) values (3, 3, 600, 700, 1);

insert into m2_est_window (id, amount, height, window_type, width, structure_detail_id) values (1, 12, 1000, 'S', 2000, 1);
insert into m2_est_window (id, amount, height, window_type, width, structure_detail_id) values (2, 6, 1000, 'S', 1500, 1);


insert into m2_est_structure (id, city_name, create_date, eaves_length, height, length, place_name, rear_truss_height, structure_type, truss_height, user_num, width) values (2, '30', now(),1250, 4700, 10100, 'B처마 테스트', 550, 'BE', 1750, 2, 6350);
insert into m2_est_structure_detail (id, canopy_yn, ceiling_paper, ceiling_thick, ceiling_type, ceiling_yn, create_date, door_yn, downpipe_yn, gucci, gucci_amount, inside_wall_paper, inside_wall_thick, inside_wall_type, inside_wall_yn, outside_wall_paper, outside_wall_thick, outside_wall_type, roof_paper, roof_thick, roof_type, window_yn, structure_id)values (2,'Y','',0,'','N',now(),'N','N','100','8','',0,'','N','G1',50,'G','W1','175','W','N',2);
insert into m2_est_canopy (id, amount, length, structure_detail_id) values (3, 1, 4400, 2);
insert into m2_est_canopy (id, amount, length, structure_detail_id) values (4, 2, 1300, 2);

insert into m2_est_structure (id, city_name, create_date, eaves_length, height, length, place_name, rear_truss_height, structure_type, truss_height, user_num, width) values (3, '30', now(), 0 , 4700, 10100, 'A처마 테스트', 0, 'AE', 1750, 2, 6350);
insert into m2_est_structure_detail (id, canopy_yn, ceiling_paper, ceiling_thick, ceiling_type, ceiling_yn, create_date, door_yn, downpipe_yn, gucci, gucci_amount, inside_wall_paper, inside_wall_thick, inside_wall_type, inside_wall_yn, outside_wall_paper, outside_wall_thick, outside_wall_type, roof_paper, roof_thick, roof_type, window_yn, structure_id)values (3,'Y','',0,'','N',now(),'N','N','100','8','',0,'','N','G1',50,'G','W1','175','W','N',3);
insert into m2_est_canopy (id, amount, length, structure_detail_id) values (5, 1, 4400, 3);
insert into m2_est_canopy (id, amount, length, structure_detail_id) values (6, 2, 1300, 3);

insert into m2_est_structure (id, city_name, create_date, eaves_length, height, length, place_name, rear_truss_height, structure_type, truss_height, user_num, width) values (4, '30', now(), 0 , 4700, 10100, 'B박스 테스트', 0, 'BB', 1750, 2, 6350);
insert into m2_est_structure_detail (id, canopy_yn, ceiling_paper, ceiling_thick, ceiling_type, ceiling_yn, create_date, door_yn, downpipe_yn, gucci, gucci_amount, inside_wall_paper, inside_wall_thick, inside_wall_type, inside_wall_yn, outside_wall_paper, outside_wall_thick, outside_wall_type, roof_paper, roof_thick, roof_type, window_yn, structure_id)values (4,'Y','',0,'','N',now(),'N','N','100','8','',0,'','N','G1',50,'G','W1','175','W','N',4);
insert into m2_est_canopy (id, amount, length, structure_detail_id) values (7, 1, 4400, 4);
insert into m2_est_canopy (id, amount, length, structure_detail_id) values (8, 2, 1300, 4);

insert into m2_est_structure (id, city_name, create_date, eaves_length, height, length, place_name, rear_truss_height, structure_type, truss_height, user_num, width) values (5, '30', now(), 0 , 4700, 10100, 'A박스 테스트', 0, 'AB', 1750, 2, 6350);
insert into m2_est_structure_detail (id, canopy_yn, ceiling_paper, ceiling_thick, ceiling_type, ceiling_yn, create_date, door_yn, downpipe_yn, gucci, gucci_amount, inside_wall_paper, inside_wall_thick, inside_wall_type, inside_wall_yn, outside_wall_paper, outside_wall_thick, outside_wall_type, roof_paper, roof_thick, roof_type, window_yn, structure_id)values (5,'Y','',0,'','N',now(),'N','N','100','8','',0,'','N','G1',50,'G','W1','175','W','N',5);
insert into m2_est_canopy (id, amount, length, structure_detail_id) values (9, 1, 4400, 5);
insert into m2_est_canopy (id, amount, length, structure_detail_id) values (10, 2, 1300, 5);

insert into m2_est_structure (id, city_name, create_date, eaves_length, height, length, place_name, rear_truss_height, structure_type, truss_height, user_num, width) values (6, '30', now(), 0 , 999999, 999999, 'B트러스 테스트', 0, 'BT', 999999, 2, 999999);
insert into m2_est_structure_detail (id, canopy_yn, ceiling_paper, ceiling_thick, ceiling_type, ceiling_yn, create_date, door_yn, downpipe_yn, gucci, gucci_amount, inside_wall_paper, inside_wall_thick, inside_wall_type, inside_wall_yn, outside_wall_paper, outside_wall_thick, outside_wall_type, roof_paper, roof_thick, roof_type, window_yn, structure_id)values (6,'Y','',0,'','N',now(),'N','N','100','8','',0,'','N','G1',50,'G','W1','175','W','N',6);
insert into m2_est_canopy (id, amount, length, structure_detail_id) values (11, 1, 4400, 6);
insert into m2_est_canopy (id, amount, length, structure_detail_id) values (12, 2, 1300, 6);

insert into m2_est_structure (id, city_name, create_date, eaves_length, height, length, place_name, rear_truss_height, structure_type, truss_height, user_num, width) values (7, '30', now(), 0 , 4700, 10100, 'A트러스 테스트', 0, 'AT', 1750, 2, 6350);
insert into m2_est_structure_detail (id, canopy_yn, ceiling_paper, ceiling_thick, ceiling_type, ceiling_yn, create_date, door_yn, downpipe_yn, gucci, gucci_amount, inside_wall_paper, inside_wall_thick, inside_wall_type, inside_wall_yn, outside_wall_paper, outside_wall_thick, outside_wall_type, roof_paper, roof_thick, roof_type, window_yn, structure_id)values (7,'Y','',0,'','N',now(),'N','N','100','8','',0,'','N','G1',50,'G','W1','175','W','N',7);
insert into m2_est_canopy (id, amount, length, structure_detail_id) values (13, 1, 4400, 7);
insert into m2_est_canopy (id, amount, length, structure_detail_id) values (14, 2, 1300, 7);



-- #간이투시도
insert into m2_picture (id, create_date, etc, name, status, user_num) values (1, now(), '지붕 색상을 조금만 진하게 해주세요', '0192998-김사장님 현장', 'S1', 2);
insert into m2_picture (id, create_date, etc, name, status, user_num) values (2, now(), 'TEST1', 'TEST1', 'S3', 1);
insert into m2_picture (id, create_date, etc, name, status, user_num) values (3, now(), 'TEST2', 'TEST2', 'S3', 1);
insert into m2_picture (id, create_date, etc, name, status, user_num) values (4, now(), 'TEST3', 'TEST3', 'S3', 1);
--
-- insert into m2_picture (id, create_date, etc, name, status, user_num) values (5, now(), '지붕 색상을 조금만 진하게 해주세요', '0192998-김사장님 현장', 'S1', 2);
-- insert into m2_picture (id, create_date, etc, name, status, user_num) values (6, now(), '지붕 색상을 조금만 진하게 해주세요', '0192998-김사장님 현장', 'S1', 2);
-- insert into m2_picture (id, create_date, etc, name, status, user_num) values (7, now(), '지붕 색상을 조금만 진하게 해주세요', '0192998-김사장님 현장', 'S1', 2);
-- insert into m2_picture (id, create_date, etc, name, status, user_num) values (8, now(), '지붕 색상을 조금만 진하게 해주세요', '0192998-김사장님 현장', 'S1', 2);
-- insert into m2_picture (id, create_date, etc, name, status, user_num) values (9, now(), '지붕 색상을 조금만 진하게 해주세요', '0192998-김사장님 현장', 'S1', 2);
-- insert into m2_picture (id, create_date, etc, name, status, user_num) values (10, now(), '지붕 색상을 조금만 진하게 해주세요', '0192998-김사장님 현장', 'S1', 2);
-- insert into m2_picture (id, create_date, etc, name, status, user_num) values (11, now(), '지붕 색상을 조금만 진하게 해주세요', '0192998-김사장님 현장', 'S1', 2);
-- insert into m2_picture (id, create_date, etc, name, status, user_num) values (12, now(), '지붕 색상을 조금만 진하게 해주세요', '0192998-김사장님 현장', 'S1', 2);
-- insert into m2_picture (id, create_date, etc, name, status, user_num) values (13, now(), '지붕 색상을 조금만 진하게 해주세요', '0192998-김사장님 현장', 'S1', 2);
-- insert into m2_picture (id, create_date, etc, name, status, user_num) values (14, now(), '지붕 색상을 조금만 진하게 해주세요', '0192998-김사장님 현장', 'S1', 2);
-- insert into m2_picture (id, create_date, etc, name, status, user_num) values (15, now(), '지붕 색상을 조금만 진하게 해주세요', '0192998-김사장님 현장', 'S1', 2);
-- insert into m2_picture (id, create_date, etc, name, status, user_num) values (16, now(), '지붕 색상을 조금만 진하게 해주세요', '0192998-김사장님 현장', 'S1', 2);
-- insert into m2_picture (id, create_date, etc, name, status, user_num) values (17, now(), '지붕 색상을 조금만 진하게 해주세요', '0192998-김사장님 현장', 'S1', 2);


--est_price
insert into m2_est_price ( gubun, sub_gubun, type, sub_type, start_price, gap_price, max_thick_price, standard_price, e_price) values ( 'P', 'I', 'E', 'E1', 8500, 800, 0, 0, 8000);
insert into m2_est_price ( gubun, sub_gubun, type, sub_type, start_price, gap_price, max_thick_price, standard_price, e_price) values ( 'P', 'I', 'E', 'E2', 10500, 1400, 0, 0, 8000);
insert into m2_est_price ( gubun, sub_gubun, type, sub_type, start_price, gap_price, max_thick_price, standard_price, e_price) values ( 'P', 'I', 'E', 'E3', 11500, 2000, 0, 0, 8000);
insert into m2_est_price ( gubun, sub_gubun, type, sub_type, start_price, gap_price, max_thick_price, standard_price, e_price) values ( 'P', 'I', 'G', 'G1', 15300, 1600, 0, 0, 9000);
insert into m2_est_price ( gubun, sub_gubun, type, sub_type, start_price, gap_price, max_thick_price, standard_price, e_price) values ( 'P', 'I', 'G', 'G2', 16300, 2100, 0, 0, 9000);
insert into m2_est_price ( gubun, sub_gubun, type, sub_type, start_price, gap_price, max_thick_price, standard_price, e_price) values ( 'P', 'I', 'W', 'W1', 21800, 4000, 0, 0, 9000);

insert into m2_est_price ( gubun, sub_gubun, type, sub_type, start_price, gap_price, max_thick_price, standard_price, e_price) values ( 'P', 'O', 'E', 'E1', 9300,	800, 0, 0, 6000);
insert into m2_est_price ( gubun, sub_gubun, type, sub_type, start_price, gap_price, max_thick_price, standard_price, e_price) values ( 'P', 'O', 'E', 'E2', 11300,	1400, 0, 0, 6000);
insert into m2_est_price ( gubun, sub_gubun, type, sub_type, start_price, gap_price, max_thick_price, standard_price, e_price) values ( 'P', 'O', 'E', 'E3', 12500,	2000, 0, 0, 6000);
insert into m2_est_price ( gubun, sub_gubun, type, sub_type, start_price, gap_price, max_thick_price, standard_price, e_price) values ('P', 'O', 'G', 'G1', 16300,	1600, 0, 0, 9000);
insert into m2_est_price ( gubun, sub_gubun, type, sub_type, start_price, gap_price, max_thick_price, standard_price, e_price) values ( 'P', 'O', 'G', 'G2', 17300,	2100, 0, 0, 9000);
insert into m2_est_price ( gubun, sub_gubun, type, sub_type, start_price, gap_price, max_thick_price, standard_price, e_price) values ( 'P', 'O', 'W', 'W1', 22800,4000, 0, 0, 8000);

insert into m2_est_price ( gubun, sub_gubun, type, sub_type, start_price, gap_price, max_thick_price, standard_price, e_price) values ( 'P', 'R', 'E', 'E1', 9500,	800,	6800, 0, 6000);
insert into m2_est_price ( gubun, sub_gubun, type, sub_type, start_price, gap_price, max_thick_price, standard_price, e_price) values ( 'P', 'R', 'E', 'E2', 11500,	1400,	11900, 0, 6000);
insert into m2_est_price ( gubun, sub_gubun, type, sub_type, start_price, gap_price, max_thick_price, standard_price, e_price) values ( 'P', 'R', 'E', 'E3', 12500,	2000,	17000, 0, 6000);
insert into m2_est_price ( gubun, sub_gubun, type, sub_type, start_price, gap_price, max_thick_price, standard_price, e_price) values ( 'P', 'R', 'G', 'G1', 16300,	1600,	17000, 0, 6000);
insert into m2_est_price ( gubun, sub_gubun, type, sub_type, start_price, gap_price, max_thick_price, standard_price, e_price) values ( 'P', 'R', 'G', 'G2', 17300,	2100,	17000, 0, 6000);
insert into m2_est_price ( gubun, sub_gubun, type, sub_type, start_price, gap_price, max_thick_price, standard_price, e_price) values ( 'P', 'R', 'W', 'W1', 22800,	4000,	17000, 0, 6000);

insert into m2_est_price ( gubun, sub_gubun, type, sub_type, start_price, gap_price, max_thick_price, standard_price, e_price) values ( 'P', 'C', 'E', 'E1', 8500,	800, 0, 0, 12000);
insert into m2_est_price ( gubun, sub_gubun, type, sub_type, start_price, gap_price, max_thick_price, standard_price, e_price) values ( 'P', 'C', 'E', 'E2', 10500,	1400, 0, 0, 12000);
insert into m2_est_price ( gubun, sub_gubun, type, sub_type, start_price, gap_price, max_thick_price, standard_price, e_price) values ( 'P', 'C', 'E', 'E3', 11500,	2000, 0, 0, 12000);
insert into m2_est_price ( gubun, sub_gubun, type, sub_type, start_price, gap_price, max_thick_price, standard_price, e_price) values ( 'P', 'C', 'G', 'G1', 15300,	1600, 0, 0, 12000);
insert into m2_est_price ( gubun, sub_gubun, type, sub_type, start_price, gap_price, max_thick_price, standard_price, e_price) values ( 'P', 'C', 'G', 'G2', 16300,	2100, 0, 0, 12000);
insert into m2_est_price ( gubun, sub_gubun, type, sub_type, start_price, gap_price, max_thick_price, standard_price, e_price) values ( 'P', 'C', 'W', 'W1', 21800,4000, 0, 0, 12000);

-- 판넬 부자재1
insert into m2_est_price ( gubun, sub_gubun, type, sub_type, start_price, gap_price, max_thick_price, standard_price) values ('S01', 'R', '용마루', '무', 25,0,0,610);
insert into m2_est_price ( gubun, sub_gubun, type, sub_type, start_price, gap_price, max_thick_price, standard_price) values ('S01', 'R', '용마루하부', '무', 25,0,0,420);
insert into m2_est_price ( gubun, sub_gubun, type, sub_type, start_price, gap_price, max_thick_price, standard_price) values ('S01', 'R', '반트러스용마루', '무', 25,0,0,500);
insert into m2_est_price ( gubun, sub_gubun, type, sub_type, start_price, gap_price, max_thick_price, standard_price) values ('S01', 'R', '물끊기', '무', 25,0,0,350);
insert into m2_est_price ( gubun, sub_gubun, type, sub_type, start_price, gap_price, max_thick_price, standard_price) values ('S01', 'R', '미돌출박공', '유', 25,0,0,320);
insert into m2_est_price ( gubun, sub_gubun, type, sub_type, start_price, gap_price, max_thick_price, standard_price) values ('S01', 'R', '돌출박공', '유', 25,0,0,420);
insert into m2_est_price ( gubun, sub_gubun, type, sub_type, start_price, gap_price, max_thick_price, standard_price) values ('S01', 'R', '물받이', '유', 25,0,0,640);

insert into m2_est_price ( gubun, sub_gubun, type, sub_type, start_price, gap_price, max_thick_price, standard_price) values ('S01', 'O', '두겁후레싱', '유', 25,0,0,375);
insert into m2_est_price ( gubun, sub_gubun, type, sub_type, start_price, gap_price, max_thick_price, standard_price) values ('S01', 'O', '외부코너', '유', 25,0,0,240);
insert into m2_est_price ( gubun, sub_gubun, type, sub_type, start_price, gap_price, max_thick_price, standard_price, e_price) values ('S01', 'O', '의자베이스', '유', 45,0,0,100,4500);

insert into m2_est_price ( gubun, sub_gubun, type, sub_type, start_price, gap_price, max_thick_price, standard_price) values ('S01', 'I', '유바', '유', 20,0,0,100);

-- 판넬 부자재2
insert into m2_est_price ( gubun, sub_gubun, type, sub_type, start_price, gap_price, max_thick_price, standard_price) values ('S02', '', '크로샤', '무', 600,0,0,0);
insert into m2_est_price ( gubun, sub_gubun, type, sub_type, start_price, gap_price, max_thick_price, standard_price) values ('S02', '', '캐노피삼각대', '무', 8000,0,0,0);
insert into m2_est_price ( gubun, sub_gubun, type, sub_type, start_price, gap_price, max_thick_price, standard_price) values ('S02', '', '캐노피정면마감', '무', 9000,0,0,0);
insert into m2_est_price ( gubun, sub_gubun, type, sub_type, start_price, gap_price, max_thick_price, standard_price) values ('S02', '', '캐노피 측면마감셋', '무', 16000,0,0,0);
insert into m2_est_price ( gubun, sub_gubun, type, sub_type, start_price, gap_price, max_thick_price, standard_price) values ('S02', '', '캐노피 상부마감(물끊기)', '무', 6500,0,0,0);


-- 판넬 부자재3
insert into m2_est_price ( gubun, sub_gubun, type, sub_type, start_price, gap_price, max_thick_price, standard_price) values ('S03', '구찌', '75DIA', '', 5000,0,0,0);
insert into m2_est_price ( gubun, sub_gubun, type, sub_type, start_price, gap_price, max_thick_price, standard_price) values ('S03', '구찌', '100DIA', '', 5000,0,0,0);
insert into m2_est_price ( gubun, sub_gubun, type, sub_type, start_price, gap_price, max_thick_price, standard_price) values ('S03', '구찌', '125DIA', '', 5000,0,0,0);

insert into m2_est_price ( gubun, sub_gubun, type, sub_type, start_price, gap_price, max_thick_price, standard_price, e_price) values ('S03', '선홈통', '75파이', '', 10000,0,0,0,10000);
insert into m2_est_price ( gubun, sub_gubun, type, sub_type, start_price, gap_price, max_thick_price, standard_price, e_price) values ('S03', '선홈통', '100파이', '', 11000,0,0,0,10000);
insert into m2_est_price ( gubun, sub_gubun, type, sub_type, start_price, gap_price, max_thick_price, standard_price, e_price) values ('S03', '선홈통', '125파이', '', 12000,0,0,0,10000);

insert into m2_est_price ( gubun, sub_gubun, type, sub_type, start_price, gap_price, max_thick_price, standard_price) values ('S03', '선홈통반도', '75파이', '', 3000,0,0,0);
insert into m2_est_price ( gubun, sub_gubun, type, sub_type, start_price, gap_price, max_thick_price, standard_price) values ('S03', '선홈통반도', '100파이', '', 3500,0,0,0);
insert into m2_est_price ( gubun, sub_gubun, type, sub_type, start_price, gap_price, max_thick_price, standard_price) values ('S03', '선홈통반도', '125파이', '', 4000,0,0,0);

insert into m2_est_price ( gubun, sub_gubun, type, sub_type, start_price, gap_price, max_thick_price, standard_price) values ('S03', '연결소켓', '75파이', '', 2000,0,0,0);
insert into m2_est_price ( gubun, sub_gubun, type, sub_type, start_price, gap_price, max_thick_price, standard_price) values ('S03', '연결소켓', '100파이', '', 3000,0,0,0);
insert into m2_est_price ( gubun, sub_gubun, type, sub_type, start_price, gap_price, max_thick_price, standard_price) values ('S03', '연결소켓', '125파이', '', 4000,0,0,0);

insert into m2_est_price ( gubun, sub_gubun, type, sub_type, start_price, gap_price, max_thick_price, standard_price) values ('S03', '엘보', '75파이', '', 10000,0,0,0);
insert into m2_est_price ( gubun, sub_gubun, type, sub_type, start_price, gap_price, max_thick_price, standard_price) values ('S03', '엘보', '100파이', '', 20000,0,0,0);
insert into m2_est_price ( gubun, sub_gubun, type, sub_type, start_price, gap_price, max_thick_price, standard_price) values ('S03', '엘보', '125파이', '', 30000,0,0,0);

insert into m2_est_price ( gubun, sub_gubun, type, sub_type, start_price, gap_price, max_thick_price, standard_price) values ('S03', 'PVC물받이받침대', '50티판넬용', '', 9000,0,0,0);
insert into m2_est_price ( gubun, sub_gubun, type, sub_type, start_price, gap_price, max_thick_price, standard_price) values ('S03', 'PVC물받이받침대', '75티판넬용', '', 10000,0,0,0);
insert into m2_est_price ( gubun, sub_gubun, type, sub_type, start_price, gap_price, max_thick_price, standard_price) values ('S03', 'PVC물받이받침대', '100티판넬용', '', 11000,0,0,0);
insert into m2_est_price ( gubun, sub_gubun, type, sub_type, start_price, gap_price, max_thick_price, standard_price) values ('S03', 'PVC물받이받침대', '125티판넬용', '', 12000,0,0,0);
insert into m2_est_price ( gubun, sub_gubun, type, sub_type, start_price, gap_price, max_thick_price, standard_price) values ('S03', 'PVC물받이받침대', '150티판넬용', '', 13000,0,0,0);
insert into m2_est_price ( gubun, sub_gubun, type, sub_type, start_price, gap_price, max_thick_price, standard_price) values ('S03', 'PVC물받이받침대', '175티판넬용', '', 14000,0,0,0);
insert into m2_est_price ( gubun, sub_gubun, type, sub_type, start_price, gap_price, max_thick_price, standard_price) values ('S03', 'PVC물받이받침대', '200티판넬용', '', 15000,0,0,0);
insert into m2_est_price ( gubun, sub_gubun, type, sub_type, start_price, gap_price, max_thick_price, standard_price) values ('S03', 'PVC물받이받침대', '225티판넬용', '', 16000,0,0,0);
insert into m2_est_price ( gubun, sub_gubun, type, sub_type, start_price, gap_price, max_thick_price, standard_price) values ('S03', 'PVC물받이받침대', '260티판넬용', '', 17000,0,0,0);

insert into m2_est_price ( gubun, sub_gubun, type, sub_type, start_price, gap_price, max_thick_price, standard_price) values ('S03', '물받이받침쇠', '6T철판', '', 10000,0,0,0);
insert into m2_est_price ( gubun, sub_gubun, type, sub_type, start_price, gap_price, max_thick_price, standard_price) values ('S03', '상자홈통', '', '', 30000,0,0,0);

--창호
insert into m2_est_price ( gubun, sub_gubun, type, sub_type, start_price, gap_price, max_thick_price, standard_price) values ('W', 'S', '50티판넬용', '16mm유리', 50000,0,0,0);
insert into m2_est_price ( gubun, sub_gubun, type, sub_type, start_price, gap_price, max_thick_price, standard_price) values ('W', 'S', '75티판넬용', '16mm유리', 50000,0,0,0);
insert into m2_est_price ( gubun, sub_gubun, type, sub_type, start_price, gap_price, max_thick_price, standard_price) values ('W', 'S', '100티판넬용', '16mm유리', 50000,0,0,0);
insert into m2_est_price ( gubun, sub_gubun, type, sub_type, start_price, gap_price, max_thick_price, standard_price) values ('W', 'S', '125티판넬용', '16mm유리', 50000,0,0,0);
insert into m2_est_price ( gubun, sub_gubun, type, sub_type, start_price, gap_price, max_thick_price, standard_price) values ('W', 'S', '150티판넬용', '16mm유리', 50000,0,0,0);

insert into m2_est_price ( gubun, sub_gubun, type, sub_type, start_price, gap_price, max_thick_price, standard_price) values ('W', 'D', '225T', '16mm유리', 75000,0,0,0);

--도어
insert into m2_est_price ( gubun, sub_gubun, type, sub_type, start_price, gap_price, max_thick_price, standard_price) values ('D', 'S', '50티판넬용', '800*2000', 70000,0,0,0);
insert into m2_est_price ( gubun, sub_gubun, type, sub_type, start_price, gap_price, max_thick_price, standard_price) values ('D', 'S', '50티판넬용', '800*2100', 75000,0,0,0);
insert into m2_est_price ( gubun, sub_gubun, type, sub_type, start_price, gap_price, max_thick_price, standard_price) values ('D', 'S', '50티판넬용', '900*2100', 78000,0,0,0);
insert into m2_est_price ( gubun, sub_gubun, type, sub_type, start_price, gap_price, max_thick_price, standard_price) values ('D', 'S', '50티판넬용', '1800*2100', 150000,0,0,0);

insert into m2_est_price ( gubun, sub_gubun, type, sub_type, start_price, gap_price, max_thick_price, standard_price) values ('D', 'S', '75티판넬용', '800*2000', 73000,0,0,0);
insert into m2_est_price ( gubun, sub_gubun, type, sub_type, start_price, gap_price, max_thick_price, standard_price) values ('D', 'S', '75티판넬용', '800*2100', 78000,0,0,0);
insert into m2_est_price ( gubun, sub_gubun, type, sub_type, start_price, gap_price, max_thick_price, standard_price) values ('D', 'S', '75티판넬용', '900*2100', 82000,0,0,0);
insert into m2_est_price ( gubun, sub_gubun, type, sub_type, start_price, gap_price, max_thick_price, standard_price) values ('D', 'S', '75티판넬용', '1800*2100', 160000,0,0,0);

insert into m2_est_price ( gubun, sub_gubun, type, sub_type, start_price, gap_price, max_thick_price, standard_price) values ('D', 'S', '100티판넬용', '800*2000', 73000,0,0,0);
insert into m2_est_price ( gubun, sub_gubun, type, sub_type, start_price, gap_price, max_thick_price, standard_price) values ('D', 'S', '100티판넬용', '800*2100', 78000,0,0,0);
insert into m2_est_price ( gubun, sub_gubun, type, sub_type, start_price, gap_price, max_thick_price, standard_price) values ('D', 'S', '100티판넬용', '900*2100', 82000,0,0,0);
insert into m2_est_price ( gubun, sub_gubun, type, sub_type, start_price, gap_price, max_thick_price, standard_price) values ('D', 'S', '100티판넬용', '1800*2100', 160000,0,0,0);

insert into m2_est_price ( gubun, sub_gubun, type, sub_type, start_price, gap_price, max_thick_price, standard_price) values ('D', 'S', '125티판넬용', '800*2000', 73000,0,0,0);
insert into m2_est_price ( gubun, sub_gubun, type, sub_type, start_price, gap_price, max_thick_price, standard_price) values ('D', 'S', '125티판넬용', '800*2100', 78000,0,0,0);
insert into m2_est_price ( gubun, sub_gubun, type, sub_type, start_price, gap_price, max_thick_price, standard_price) values ('D', 'S', '125티판넬용', '900*2100', 82000,0,0,0);
insert into m2_est_price ( gubun, sub_gubun, type, sub_type, start_price, gap_price, max_thick_price, standard_price) values ('D', 'S', '125티판넬용', '1800*2100', 160000,0,0,0);

insert into m2_est_price ( gubun, sub_gubun, type, sub_type, start_price, gap_price, max_thick_price, standard_price) values ('D', 'S', '150티판넬용', '800*2000', 73000,0,0,0);
insert into m2_est_price ( gubun, sub_gubun, type, sub_type, start_price, gap_price, max_thick_price, standard_price) values ('D', 'S', '150티판넬용', '800*2100', 78000,0,0,0);
insert into m2_est_price ( gubun, sub_gubun, type, sub_type, start_price, gap_price, max_thick_price, standard_price) values ('D', 'S', '150티판넬용', '900*2100', 82000,0,0,0);
insert into m2_est_price ( gubun, sub_gubun, type, sub_type, start_price, gap_price, max_thick_price, standard_price) values ('D', 'S', '150티판넬용', '1800*2100', 160000,0,0,0);

insert into m2_est_price ( gubun, sub_gubun, type, sub_type, start_price, gap_price, max_thick_price, standard_price) values ('D', 'F', '50티판넬용', '800*2000', 70000,0,0,0);
insert into m2_est_price ( gubun, sub_gubun, type, sub_type, start_price, gap_price, max_thick_price, standard_price) values ('D', 'F', '50티판넬용', '800*2100', 75000,0,0,0);
insert into m2_est_price ( gubun, sub_gubun, type, sub_type, start_price, gap_price, max_thick_price, standard_price) values ('D', 'F', '50티판넬용', '900*2100', 78000,0,0,0);
insert into m2_est_price ( gubun, sub_gubun, type, sub_type, start_price, gap_price, max_thick_price, standard_price) values ('D', 'F', '50티판넬용', '1800*2100', 150000,0,0,0);

insert into m2_est_price ( gubun, sub_gubun, type, sub_type, start_price, gap_price, max_thick_price, standard_price) values ('D', 'F', '75티판넬용', '800*2000', 73000,0,0,0);
insert into m2_est_price ( gubun, sub_gubun, type, sub_type, start_price, gap_price, max_thick_price, standard_price) values ('D', 'F', '75티판넬용', '800*2100', 78000,0,0,0);
insert into m2_est_price ( gubun, sub_gubun, type, sub_type, start_price, gap_price, max_thick_price, standard_price) values ('D', 'F', '75티판넬용', '900*2100', 82000,0,0,0);
insert into m2_est_price ( gubun, sub_gubun, type, sub_type, start_price, gap_price, max_thick_price, standard_price) values ('D', 'F', '75티판넬용', '1800*2100', 160000,0,0,0);

insert into m2_est_price ( gubun, sub_gubun, type, sub_type, start_price, gap_price, max_thick_price, standard_price) values ('D', 'F', '100티판넬용', '800*2000', 73000,0,0,0);
insert into m2_est_price ( gubun, sub_gubun, type, sub_type, start_price, gap_price, max_thick_price, standard_price) values ('D', 'F', '100티판넬용', '800*2100', 78000,0,0,0);
insert into m2_est_price ( gubun, sub_gubun, type, sub_type, start_price, gap_price, max_thick_price, standard_price) values ('D', 'F', '100티판넬용', '900*2100', 82000,0,0,0);
insert into m2_est_price ( gubun, sub_gubun, type, sub_type, start_price, gap_price, max_thick_price, standard_price) values ('D', 'F', '100티판넬용', '1800*2100', 160000,0,0,0);

insert into m2_est_price ( gubun, sub_gubun, type, sub_type, start_price, gap_price, max_thick_price, standard_price) values ('D', 'F', '125티판넬용', '800*2000', 73000,0,0,0);
insert into m2_est_price ( gubun, sub_gubun, type, sub_type, start_price, gap_price, max_thick_price, standard_price) values ('D', 'F', '125티판넬용', '800*2100', 78000,0,0,0);
insert into m2_est_price ( gubun, sub_gubun, type, sub_type, start_price, gap_price, max_thick_price, standard_price) values ('D', 'F', '125티판넬용', '900*2100', 82000,0,0,0);
insert into m2_est_price ( gubun, sub_gubun, type, sub_type, start_price, gap_price, max_thick_price, standard_price) values ('D', 'F', '125티판넬용', '1800*2100', 160000,0,0,0);

insert into m2_est_price ( gubun, sub_gubun, type, sub_type, start_price, gap_price, max_thick_price, standard_price) values ('D', 'F', '150티판넬용', '800*2000', 73000,0,0,0);
insert into m2_est_price ( gubun, sub_gubun, type, sub_type, start_price, gap_price, max_thick_price, standard_price) values ('D', 'F', '150티판넬용', '800*2100', 78000,0,0,0);
insert into m2_est_price ( gubun, sub_gubun, type, sub_type, start_price, gap_price, max_thick_price, standard_price) values ('D', 'F', '150티판넬용', '900*2100', 82000,0,0,0);
insert into m2_est_price ( gubun, sub_gubun, type, sub_type, start_price, gap_price, max_thick_price, standard_price) values ('D', 'F', '150티판넬용', '1800*2100', 160000,0,0,0);


insert into m2_est_price ( gubun, sub_gubun, type, sub_type, start_price, gap_price, max_thick_price, standard_price) values ('D', 'H', '마감50티', '', 45000,0,0,0);
insert into m2_est_price ( gubun, sub_gubun, type, sub_type, start_price, gap_price, max_thick_price, standard_price) values ('D', 'H', '마감75티', '', 48000,0,0,0);
insert into m2_est_price ( gubun, sub_gubun, type, sub_type, start_price, gap_price, max_thick_price, standard_price) values ('D', 'H', '마감100티', '', 49000,0,0,0);
insert into m2_est_price ( gubun, sub_gubun, type, sub_type, start_price, gap_price, max_thick_price, standard_price) values ('D', 'H', '마감125티', '', 50000,0,0,0);
insert into m2_est_price ( gubun, sub_gubun, type, sub_type, start_price, gap_price, max_thick_price, standard_price) values ('D', 'H', '마감150티', '', 52000,0,0,0);

--캐노피
insert into m2_est_price ( gubun, sub_gubun, type, sub_type, start_price, gap_price, max_thick_price, standard_price) values ('C', '', '', '', 9300,0,0,0);
insert into m2_est_price ( gubun, sub_gubun, type, sub_type, start_price, gap_price, max_thick_price, standard_price, e_price) values ('B', '', '', '', 8500,0,0,0,9000);
insert into m2_est_price ( gubun, sub_gubun, type, sub_type, start_price, gap_price, max_thick_price, standard_price, e_price) values ('PR', '', '', '', 8500,0,0,0,9000);
