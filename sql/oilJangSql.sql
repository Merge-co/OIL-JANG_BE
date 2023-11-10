

CREATE TABLE `category_info`
(
    `category_code`    INTEGER AUTO_INCREMENT NOT NULL,
    `category_name`    VARCHAR(100) NOT NULL,
    `upper_category_code`    INTEGER,
    PRIMARY KEY ( `category_code` )
);


CREATE TABLE `inq_category`
(
    `inq_cate_code`    INTEGER NOT NULL,
    `inq_cate_name`    VARCHAR(30) NOT NULL,
    PRIMARY KEY ( `inq_cate_code` )
);


CREATE TABLE `inquiry`
(
    `inq_code`    INTEGER AUTO_INCREMENT NOT NULL,
    `inq_title`    VARCHAR(30) NOT NULL,
    `inq_content`    VARCHAR(1000) NOT NULL,
    `inq_answer`    VARCHAR(1000),
    `inq_time`    DATETIME NOT NULL,
    `ref_user_code`    INTEGER  NOT NULL,
    `ref_inq_cate_code`    INTEGER NOT NULL,
    `inq_status`    VARCHAR(10) DEFAULT 'N' NOT NULL,
    PRIMARY KEY ( `inq_code` )
);




CREATE TABLE `message`
(
    `msg_code`    INTEGER AUTO_INCREMENT NOT NULL,
    `msg_content`    VARCHAR(1000) NOT NULL,
    `msg_status`    VARCHAR(30) NOT NULL,
    `msg_time`    DATETIME NOT NULL,
    `ref_product_code`    INTEGER NOT NULL,
    `sender_code`    INTEGER NOT NULL,
    `receiver_code`    INTEGER NOT NULL,
    `msg_delete_code`    INTEGER DEFAULT 1 NOT NULL,
    PRIMARY KEY ( `msg_code` )
);



CREATE TABLE `msg_delete_info`
(
    `msg_delete_code`    INTEGER NOT NULL,
    `msg_delete_status`    VARCHAR(30) NOT NULL,
    PRIMARY KEY ( `msg_delete_code` )
);


CREATE TABLE `pro_image_info`
(
    `pro_image_code`    INTEGER AUTO_INCREMENT NOT NULL,
    `ref_product_code`    INTEGER NOT NULL,
    `pro_image_origin_name`    VARCHAR(500) NOT NULL,
    `pro_image_db_name`    VARCHAR(500) NOT NULL,
    `pro_image_origin_addr`    VARCHAR(500) NOT NULL,

    PRIMARY KEY ( `pro_image_code` )
);




CREATE TABLE `product_info`
(
    `product_code`    INTEGER AUTO_INCREMENT NOT NULL,
    `product_thumb_addr`    VARCHAR(500) NOT NULL,
    `product_name`    VARCHAR(50) NOT NULL,
    `product_price`    INTEGER NOT NULL,
    `product_desc`    VARCHAR(500) NOT NULL,
    `enroll_datetime`    DATETIME NOT NULL,
    `view_count`    INTEGER NOT NULL,
    `ref_user_code`    INTEGER NOT NULL,
    `ref_category_code`    INTEGER NOT NULL,
    `wish_place_to_trade`    VARCHAR(200) NOT NULL,
    `sell_status_code`    INTEGER NOT NULL,
    PRIMARY KEY ( `product_code` )
);

CREATE TABLE `report`
(
    `report_no`    INTEGER AUTO_INCREMENT NOT NULL,
    `report_comment`    VARCHAR(255) NOT NULL,
    `report_date`    DATE DEFAULT (now()) NOT NULL,
    `ref_report_category_no`    INTEGER NOT NULL,
    `process_distincation`    VARCHAR(100),
    `process_comment`    VARCHAR(255),
    `process_date`    DATE,
    `report_user_nick`    VARCHAR(50) NOT NULL,
    `product_code`    INTEGER NOT NULL,
    `sell_status_code`    INTEGER NOT NULL,
    PRIMARY KEY ( `report_no` )
);


CREATE TABLE `report_category`
(
    `report_category_no`    INTEGER NOT NULL,
    `report_category_code`    VARCHAR(255) NOT NULL,
    PRIMARY KEY ( `report_category_no` )
);


CREATE TABLE `sell_status_info`
(
    `sell_status_code`    INTEGER NOT NULL,
    `sell_status`    VARCHAR(30) NOT NULL,
    PRIMARY KEY ( `sell_status_code` )
);


CREATE TABLE `user_info`
(
    `user_code`    INTEGER auto_increment NOT NULL,
    `nickname`    VARCHAR(30) NOT NULL,
    `name`    VARCHAR(30) NOT NULL,
    `id`    VARCHAR(30) NOT NULL,
    `pwd`    VARCHAR(100) NOT NULL,
    `birthdate`    VARCHAR(30) NOT NULL,
    `gender`    VARCHAR(30) NOT NULL,
    `phone`    VARCHAR(30) NOT NULL,
    `email`    VARCHAR(30) DEFAULT ('이메일 입력해주세요.'),
    `enroll_date`    datetime DEFAULT (now()) NOT NULL,
    `enroll_type`    VARCHAR(30) NOT NULL,
    `token`    VARCHAR(300),
    `auth_name`    VARCHAR(30) NOT NULL,
    `verify_status`    VARCHAR(1) NOT NULL,
    `withdraw_status`    VARCHAR(1) DEFAULT 'N' NOT NULL,
    `profile_Image_Url` varchar(100),
    PRIMARY KEY ( `user_code` ),
    UNIQUE KEY(`nickname`),
    UNIQUE KEY(`id`),
    UNIQUE KEY(`token`)
);


CREATE TABLE `user_profile`
(
    `profile_code`    INTEGER AUTO_INCREMENT NOT NULL,
    `ref_user_code`  INTEGER NOT NULL,
    `user_image_origin_name`    VARCHAR(100) NOT NULL,
    `user_image_name`    VARCHAR(100) NOT NULL,
    `user_image_origin_addr`    VARCHAR(500) NOT NULL,
    `user_image_thumb_addr`    VARCHAR(500) NOT NULL,
    PRIMARY KEY ( `profile_code` )
);

CREATE TABLE `wish_list`
(
    `wish_code`    INTEGER AUTO_INCREMENT NOT NULL,
    `ref_product_code`    INTEGER NOT NULL,
    `ref_user_code`    INTEGER  NOT NULL,
    PRIMARY KEY ( `wish_code` )
);

-- ALTER TABLE `inquiry`
--     ADD CONSTRAINT `inquiry_FK` FOREIGN KEY ( `ref_inq_cate_code` )
--         REFERENCES `inq_category` (`inq_cate_code` );
--
-- ALTER TABLE `inquiry`
--     ADD CONSTRAINT `inquiry_FK1` FOREIGN KEY ( `ref_user_code` )
--         REFERENCES `user_info` (`user_code` );
--
-- ALTER TABLE `message`
--     ADD CONSTRAINT `message_FK2` FOREIGN KEY ( `ref_product_code` )
--         REFERENCES `product_info` (`product_code` );
--
-- ALTER TABLE `message`
--     ADD CONSTRAINT `message_FK1` FOREIGN KEY ( `sender_code` )
--         REFERENCES `user_info` (`user_code` );
--
-- ALTER TABLE `message`
--     ADD CONSTRAINT `message_FK` FOREIGN KEY ( `msg_delete_code` )
--         REFERENCES `msg_delete_info` (`msg_delete_code` );
--
-- ALTER TABLE `message`
--     ADD CONSTRAINT `message_FK3` FOREIGN KEY ( `receiver_code` )
--         REFERENCES `user_info` (`user_code` );
--
-- ALTER TABLE `pro_image_info`
--     ADD CONSTRAINT `pro_image_info_FK` FOREIGN KEY ( `ref_product_code` )
--         REFERENCES `product_info` (`product_code` );
--
-- ALTER TABLE `product_info`
--     ADD CONSTRAINT `product_info_FK` FOREIGN KEY ( `ref_USER_CODE` )
--         REFERENCES `user_info` (`user_code` );
--
-- ALTER TABLE `product_info`
--     ADD CONSTRAINT `product_info_FK1` FOREIGN KEY ( `ref_category_code` )
--         REFERENCES `category_info` (`category_code` );
--
-- ALTER TABLE `product_info`
--     ADD CONSTRAINT `product_info_FK2` FOREIGN KEY ( `sell_status_code` )
--         REFERENCES `sell_status_info` (`sell_status_code` );
--
-- ALTER TABLE `report`
--     ADD CONSTRAINT `report_FK1` FOREIGN KEY ( `ref_report_category_no` )
--         REFERENCES `report_category` (`report_category_no` );
--
-- ALTER TABLE `report`
--     ADD CONSTRAINT `report_FK` FOREIGN KEY ( `product_code` )
--         REFERENCES `product_info` (`product_code` );
--
-- ALTER TABLE `report`
--     ADD CONSTRAINT `report_FK2` FOREIGN KEY ( `sell_status_code` )
--         REFERENCES `sell_status_info` (`sell_status_code` );
--
-- ALTER TABLE `user_profile`
--     ADD CONSTRAINT `user_profile_FK` FOREIGN KEY ( `ref_user_code` )
--         REFERENCES `user_info` (`user_code` );
--
--
-- ALTER TABLE `wish_list`
--     ADD CONSTRAINT `wish_list_FK` FOREIGN KEY ( `ref_product_code` )
--         REFERENCES `product_info` (`product_code` );
--
-- ALTER TABLE `wish_list`
--     ADD CONSTRAINT `wish_list_FK1` FOREIGN KEY ( `ref_user_code` )
--         REFERENCES `user_info` (`user_code` );


INSERT INTO category_info (category_name, upper_category_code) VALUES ('상의', null);
INSERT INTO category_info (category_name, upper_category_code) VALUES ('아웃터', null);
INSERT INTO category_info (category_name, upper_category_code) VALUES ('바지', null);
INSERT INTO category_info (category_name, upper_category_code) VALUES ('스커트', null);
INSERT INTO category_info (category_name, upper_category_code) VALUES ('원피스', null);
INSERT INTO category_info (category_name, upper_category_code) VALUES ('블라우스', 1);
INSERT INTO category_info (category_name, upper_category_code) VALUES ('셔츠', 1);
INSERT INTO category_info (category_name, upper_category_code) VALUES ('반팔 티셔츠', 1);
INSERT INTO category_info (category_name, upper_category_code) VALUES ('긴팔 티셔츠', 1);
INSERT INTO category_info (category_name, upper_category_code) VALUES ('민소매 티셔츠', 1);
INSERT INTO category_info (category_name, upper_category_code) VALUES ('니트/스웨터', 1);
INSERT INTO category_info (category_name, upper_category_code) VALUES ('맨투맨', 1);
INSERT INTO category_info (category_name, upper_category_code) VALUES ('패딩', 2);
INSERT INTO category_info (category_name, upper_category_code) VALUES ('점퍼', 2);
INSERT INTO category_info (category_name, upper_category_code) VALUES ('코트', 2);
INSERT INTO category_info (category_name, upper_category_code) VALUES ('자켓', 2);
INSERT INTO category_info (category_name, upper_category_code) VALUES ('기다건', 2);
INSERT INTO category_info (category_name, upper_category_code) VALUES ('조끼/베스트', 2);
INSERT INTO category_info (category_name, upper_category_code) VALUES ('후드티/후드집업', 2);
INSERT INTO category_info (category_name, upper_category_code) VALUES ('데님/청바지', 3);
INSERT INTO category_info (category_name, upper_category_code) VALUES ('슬랙스', 3);
INSERT INTO category_info (category_name, upper_category_code) VALUES ('면바지', 3);
INSERT INTO category_info (category_name, upper_category_code) VALUES ('반바지', 3);
INSERT INTO category_info (category_name, upper_category_code) VALUES ('트레이닝/조거팬츠', 3);
INSERT INTO category_info (category_name, upper_category_code) VALUES ('레깅스', 3);
INSERT INTO category_info (category_name, upper_category_code) VALUES ('기타 바지', 3);
INSERT INTO category_info (category_name, upper_category_code) VALUES ('롱 스커트', 4);
INSERT INTO category_info (category_name, upper_category_code) VALUES ('미디 스커트', 4);
INSERT INTO category_info (category_name, upper_category_code) VALUES ('미니 스커트', 4);
INSERT INTO category_info (category_name, upper_category_code) VALUES ('롱 원피스', 5);
INSERT INTO category_info (category_name, upper_category_code) VALUES ('미디 원피스', 5);
INSERT INTO category_info (category_name, upper_category_code) VALUES ('미니 원피스', 5);
INSERT INTO msg_delete_info (msg_delete_code, msg_delete_status) VALUES (1, '1');
INSERT INTO msg_delete_info (msg_delete_code, msg_delete_status) VALUES (2, '2');
INSERT INTO msg_delete_info (msg_delete_code, msg_delete_status) VALUES (3, '3');
INSERT INTO msg_delete_info (msg_delete_code, msg_delete_status) VALUES (4, '4');
INSERT INTO product_info (product_thumb_addr, product_name, product_price, product_desc, enroll_datetime, view_count, ref_category_code, wish_place_to_trade, sell_status_code, ref_user_code) VALUES ('1111', '1', 111, '1', '2023-11-07 17:37:19', 5, 1, '1', 1, 1);
INSERT INTO product_info (product_thumb_addr, product_name, product_price, product_desc, enroll_datetime, view_count, ref_category_code, wish_place_to_trade, sell_status_code, ref_user_code) VALUES ('1111', '1', 111, '1', '2023-11-07 17:38:09', 0, 1, '1', 1, 1);
INSERT INTO product_info (product_thumb_addr, product_name, product_price, product_desc, enroll_datetime, view_count, ref_category_code, wish_place_to_trade, sell_status_code, ref_user_code) VALUES ('1111', '1', 111, '1', '2023-11-07 17:48:55', 0, 1, '1', 1, 1);
INSERT INTO product_info (product_thumb_addr, product_name, product_price, product_desc, enroll_datetime, view_count, ref_category_code, wish_place_to_trade, sell_status_code, ref_user_code) VALUES ('1111', '1', 111, '1', '2023-11-08 12:09:51', 0, 1, '1', 1, 1);
INSERT INTO product_info (product_thumb_addr, product_name, product_price, product_desc, enroll_datetime, view_count, ref_category_code, wish_place_to_trade, sell_status_code, ref_user_code) VALUES ('1111', '1', 111, '1', '2023-11-06 13:08:30', 165, 6, '1', 1, 1);
INSERT INTO product_info (product_thumb_addr, product_name, product_price, product_desc, enroll_datetime, view_count, ref_category_code, wish_place_to_trade, sell_status_code, ref_user_code) VALUES ('1111', '1', 111, '1', '2023-11-06 13:08:40', 0, 20, '1', 1, 1);
INSERT INTO product_info (product_thumb_addr, product_name, product_price, product_desc, enroll_datetime, view_count, ref_category_code, wish_place_to_trade, sell_status_code, ref_user_code) VALUES ('1111', '1', 111, '1', '2023-11-06 13:08:50', 0, 1, '1', 1, 1);
INSERT INTO product_info (product_thumb_addr, product_name, product_price, product_desc, enroll_datetime, view_count, ref_category_code, wish_place_to_trade, sell_status_code, ref_user_code) VALUES ('1111', '1', 111, '1', '2023-11-06 13:08:30', 0, 1, '1', 1, 1);
INSERT INTO product_info (product_thumb_addr, product_name, product_price, product_desc, enroll_datetime, view_count, ref_category_code, wish_place_to_trade, sell_status_code, ref_user_code) VALUES ('1111', '1', 111, '1', '2023-11-06 13:08:40', 0, 1, '1', 1, 1);
INSERT INTO product_info (product_thumb_addr, product_name, product_price, product_desc, enroll_datetime, view_count, ref_category_code, wish_place_to_trade, sell_status_code, ref_user_code) VALUES ('1111', '1', 111, '1', '2023-11-06 13:08:50', 1, 1, '1', 1, 1);
INSERT INTO product_info (product_thumb_addr, product_name, product_price, product_desc, enroll_datetime, view_count, ref_category_code, wish_place_to_trade, sell_status_code, ref_user_code) VALUES ('1111', '1', 111, '1', '2023-11-06 15:45:31', 0, 1, '1', 1, 1);
INSERT INTO product_info (product_thumb_addr, product_name, product_price, product_desc, enroll_datetime, view_count, ref_category_code, wish_place_to_trade, sell_status_code, ref_user_code) VALUES ('1111', '1', 111, '1', '2023-11-06 16:35:31', 0, 1, '1', 1, 1);
INSERT INTO product_info (product_thumb_addr, product_name, product_price, product_desc, enroll_datetime, view_count, ref_category_code, wish_place_to_trade, sell_status_code, ref_user_code) VALUES ('1111', '1', 111, '1', '2023-11-06 17:19:35', 0, 1, '1', 1, 1);
INSERT INTO product_info (product_thumb_addr, product_name, product_price, product_desc, enroll_datetime, view_count, ref_category_code, wish_place_to_trade, sell_status_code, ref_user_code) VALUES ('1111', '1', 111, '1', '2023-11-06 17:20:57', 0, 1, '1', 1, 1);
INSERT INTO product_info (product_thumb_addr, product_name, product_price, product_desc, enroll_datetime, view_count, ref_category_code, wish_place_to_trade, sell_status_code, ref_user_code) VALUES ('1111', '1', 111, '1', '2023-11-06 17:41:59', 0, 1, '1', 1, 1);
INSERT INTO product_info (product_thumb_addr, product_name, product_price, product_desc, enroll_datetime, view_count, ref_category_code, wish_place_to_trade, sell_status_code, ref_user_code) VALUES ('1111', '1', 111, '1', '2023-11-06 18:48:17', 0, 1, '1', 1, 1);
INSERT INTO product_info (product_thumb_addr, product_name, product_price, product_desc, enroll_datetime, view_count, ref_category_code, wish_place_to_trade, sell_status_code, ref_user_code) VALUES ('1111', '1', 111, '1', '2023-11-06 19:40:50', 0, 1, '1', 1, 1);
INSERT INTO product_info (product_thumb_addr, product_name, product_price, product_desc, enroll_datetime, view_count, ref_category_code, wish_place_to_trade, sell_status_code, ref_user_code) VALUES ('1111', '1', 111, '1', '2023-11-07 09:30:52', 0, 1, '1', 1, 1);
INSERT INTO product_info (product_thumb_addr, product_name, product_price, product_desc, enroll_datetime, view_count, ref_category_code, wish_place_to_trade, sell_status_code, ref_user_code) VALUES ('1111', '1', 111, '1', '2023-11-07 09:42:15', 0, 1, '1', 1, 1);
INSERT INTO product_info (product_thumb_addr, product_name, product_price, product_desc, enroll_datetime, view_count, ref_category_code, wish_place_to_trade, sell_status_code, ref_user_code) VALUES ('1111', '1', 111, '1', '2023-11-07 09:45:25', 1, 1, '1', 1, 1);
INSERT INTO product_info (product_thumb_addr, product_name, product_price, product_desc, enroll_datetime, view_count, ref_category_code, wish_place_to_trade, sell_status_code, ref_user_code) VALUES ('1111', '1', 111, '1', '2023-11-07 14:25:46', 0, 1, '1', 1, 1);
INSERT INTO product_info (product_thumb_addr, product_name, product_price, product_desc, enroll_datetime, view_count, ref_category_code, wish_place_to_trade, sell_status_code, ref_user_code) VALUES ('1111', '1', 111, '1', '2023-11-07 14:46:24', 0, 1, '1', 1, 1);
INSERT INTO product_info (product_thumb_addr, product_name, product_price, product_desc, enroll_datetime, view_count, ref_category_code, wish_place_to_trade, sell_status_code, ref_user_code) VALUES ('1111', '1', 111, '1', '2023-11-07 17:32:08', 0, 1, '1', 1, 1);
INSERT INTO report (report_comment, report_date, ref_report_category_no, process_distincation, process_comment, process_date, report_user_nick, product_code, sell_status_code) VALUES ('1', '2023-11-07', 1, 1, '처리중', '2023-11-08', '1', 5, 1);
INSERT INTO report_category (report_category_no, report_category_code) VALUES (1, '광고성 게시글');
INSERT INTO sell_status_info (sell_status_code, sell_status) VALUES (1, '판매 중');
INSERT INTO sell_status_info (sell_status_code, sell_status) VALUES (2, '판매 완료');
INSERT INTO sell_status_info (sell_status_code, sell_status) VALUES (3, '삭제');
INSERT INTO sell_status_info (sell_status_code, sell_status) VALUES (4, '제제');
INSERT INTO user_info (nickname, name, id, pwd, birthdate, gender, phone, email, enroll_date, enroll_type, token, auth_name, verify_status, withdraw_status, profile_Image_Url) VALUES ('1111', '1111', '1111', '1111', '2023-11-06', 'F', '010-1111-2222', '222@naver.com', '2023-11-06 00:00:00', 'tt', 'ttttt', 'aaaaa', 'Y', 'N', null);
INSERT INTO user_info (nickname, name, id, pwd, birthdate, gender, phone, email, enroll_date, enroll_type, token, auth_name, verify_status, withdraw_status, profile_Image_Url) VALUES ('2222', '2222', '2222', '2222', '2023-11-06', 'M', '010-2222-3333', '222@google.com', '2023-11-06 00:00:00', 'ss', 'aaaaa', 'rrrrr', 'N', 'N', null);
INSERT INTO user_profile (ref_user_code, user_image_origin_name, user_image_name, user_image_origin_addr, user_image_thumb_addr) VALUES (1, '11111', '11111', '11111', '11111');
INSERT INTO wish_list (ref_product_code, ref_user_code) VALUES (2, 1);
INSERT INTO wish_list (ref_product_code, ref_user_code) VALUES (2, 1);
INSERT INTO wish_list (ref_product_code, ref_user_code) VALUES (3, 1);
INSERT INTO wish_list (ref_product_code, ref_user_code) VALUES (4, 1);
INSERT INTO wish_list (ref_product_code, ref_user_code) VALUES (5, 1);
INSERT INTO wish_list (ref_product_code, ref_user_code) VALUES (6, 1);
INSERT INTO wish_list (ref_product_code, ref_user_code) VALUES (7, 1);
INSERT INTO wish_list (ref_product_code, ref_user_code) VALUES (8, 1);
INSERT INTO wish_list (ref_product_code, ref_user_code) VALUES (9, 1);
INSERT INTO wish_list (ref_product_code, ref_user_code) VALUES (10, 1);
INSERT INTO wish_list (ref_product_code, ref_user_code) VALUES (11, 1);
INSERT INTO wish_list (ref_product_code, ref_user_code) VALUES (12, 1);
INSERT INTO wish_list (ref_product_code, ref_user_code) VALUES (14, 1);
INSERT INTO wish_list (ref_product_code, ref_user_code) VALUES (16, 1);
INSERT INTO wish_list (ref_product_code, ref_user_code) VALUES (1, 1);
INSERT INTO wish_list (ref_product_code, ref_user_code) VALUES (1, 1);
INSERT INTO wish_list (ref_product_code, ref_user_code) VALUES (1, 1);
INSERT INTO wish_list (ref_product_code, ref_user_code) VALUES (1, 1);
