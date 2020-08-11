USE reservations_db;

DROP TABLE IF EXISTS reservations;
DROP TABLE IF EXISTS room_comments;
DROP TABLE IF EXISTS rooms;
DROP TABLE IF EXISTS accounts;

CREATE TABLE rooms
(
    room_id         INT PRIMARY KEY,
    room_size       INT,
    floor           INT,
    air_conditioner BOOLEAN,
    projector       BOOLEAN,
    render_data     VARCHAR(255)
);

CREATE TABLE room_comments
(
    comment_id   INT NOT NULL AUTO_INCREMENT,
    comment_date DATETIME,
    user_comment VARCHAR(255),
    room_id      INT,

    FOREIGN KEY (room_id) REFERENCES rooms (room_id),
    PRIMARY KEY (comment_id)
);

CREATE TABLE accounts
(
    account_id        INT NOT NULL AUTO_INCREMENT,
    username          VARCHAR(255) UNIQUE,
    encryptedPassword VARCHAR(255),
    email             VARCHAR(255) UNIQUE,
    phoneNumber       VARCHAR(255),

    PRIMARY KEY (account_id)
);

CREATE TABLE reservations
(
    reservation_id INT NOT NULL AUTO_INCREMENT,
    room_id        INT,
    start_date     DATETIME,
    end_date       DATETIME,
    do_repeat      BOOLEAN,
    account_id     INT,
    FOREIGN KEY (room_id) REFERENCES rooms (room_id),
    FOREIGN KEY (account_id) REFERENCES accounts (account_id),

    PRIMARY KEY (reservation_id)
);


INSERT INTO accounts
VALUES (0, 'Human', '$2a$10$1jkMI4yZEz0GvzAzVuwqUeHaEaOtKSntbVeFcO4TN3rJKW6U2qqtO', 'human@humans.org', '123456789'),
       (0, 'Human 1', '$2a$10$1jkMI4yZEz0GvzAzVuwqUeHaEaOtKSntbVeFcO4TN3rJKW6U2qqtO', 'human1@humans.org', '123456789');


INSERT INTO rooms
VALUES (100, 4, 1, false, true, '253.12 488.25 118.13 51.75'),
       (101, 2, 1, false, true, '373.5 488.25 55.13 51.75'),
       (102, 4, 1, false, true, '430.87 488.25 118.13 51.75'),
       (103, 3, 1, true, true, '551.25 488.25 83.25 51.75'),
       (104, 4, 1, true, false, '693 488.25 118.13 51.75'),
       (105, 2, 1, true, false, '813.37 488.25 55.13 51.75'),
       (106, 4, 1, true, true, '870.75 488.25 118.13 51.75'),
       (107, 3, 1, false, true, '991.13 488.25 83.25 51.75'),
       (108, 4, 1, false, false, '1076.63 445.5 75.38 94.5'),
       (109, 4, 1, false, false, '253.12 407.25 118.13 51.75'),
       (110, 4, 1, false, true, '430.87 407.25 118.13 51.75'),
       (111, 4, 1, false, false, '693 407.25 118.13 51.75'),
       (112, 4, 1, false, false, '870.75 407.25 118.13 51.75'),
       (113, 3, 1, false, false, '527.62 257.63 51.75 83.25'),
       (114, 3, 1, true, true, '608.62 257.63 51.75 83.25'),
       (115, 2, 1, true, true, '608.62 200.25 51.75 55.13'),
       (116, 3, 1, true, false, '527.62 114.75 51.75 83.25'),
       (117, 3, 1, true, false, '608.62 114.75 51.75 83.25'),
       (118, 2, 1, true, false, '608.62 57.38 51.75 55.13'),
       (119, 2, 1, true, true, '608.62 0 51.75 55.13'),
       (200, 4, 2, true, true, '0 429.75 108 110.25'),
       (201, 2, 2, true, true, '138.37 488.25 55.13 51.75'),
       (202, 2, 2, true, true, '195.75 488.25 55.13 51.75'),
       (203, 4, 2, true, true, '253.12 488.25 118.13 51.75'),
       (204, 2, 2, true, true, '373.5 488.25 55.13 51.75'),
       (205, 4, 2, true, true, '430.87 488.25 118.13 51.75'),
       (206, 3, 2, true, true, '551.25 488.25 83.25 51.75'),
       (207, 1, 2, true, false, '693 488.25 32.63 51.75'),
       (208, 3, 2, false, true, '727.87 488.25 83.25 51.75'),
       (209, 2, 2, false, true, '813.37 488.25 55.13 51.75'),
       (210, 4, 2, false, true, '870.75 488.25 118.13 51.75'),
       (211, 3, 2, true, true, '991.13 488.25 83.25 51.75'),
       (212, 4, 2, true, true, '1076.63 445.5 75.38 94.5'),
       (213, 4, 2, true, true, '253.12 407.25 118.13 51.75'),
       (214, 4, 2, true, true, '430.87 407.25 118.13 51.75'),
       (215, 1, 2, true, false, '693 407.25 32.63 51.75'),
       (216, 3, 2, false, true, '727.87 407.25 83.25 51.75'),
       (217, 1, 2, false, false, '870.75 407.25 32.62 51.75'),
       (218, 3, 2, false, true, '905.62 407.25 83.25 51.75'),
       (219, 3, 2, false, true, '527.62 257.63 51.75 83.25'),
       (220, 3, 2, false, true, '608.62 257.63 51.75 83.25'),
       (221, 2, 2, true, true, '608.62 200.25 51.75 55.13'),
       (222, 3, 2, true, true, '527.62 114.75 51.75 83.25'),
       (223, 4, 2, true, true, '608.62 114.75 68.63 83.25'),
       (224, 2, 2, true, true, '608.62 57.38 51.75 55.13'),
       (225, 2, 2, true, true, '608.62 0 51.75 55.13'),
       (300, 2, 3, true, true, '138.37 488.25 55.13 51.75'),
       (301, 2, 3, true, true, '195.75 488.25 55.13 51.75'),
       (302, 4, 3, true, true, '253.12 488.25 118.13 51.75'),
       (303, 2, 3, true, true, '373.5 488.25 55.13 51.75'),
       (304, 4, 3, true, true, '430.87 488.25 118.13 51.75'),
       (305, 3, 3, true, true, '551.25 488.25 83.25 51.75'),
       (306, 1, 3, true, false, '693 488.25 32.63 51.75'),
       (307, 3, 3, true, true, '727.87 488.25 83.25 51.75'),
       (308, 2, 3, false, true, '813.37 488.25 55.13 51.75'),
       (309, 1, 3, false, false, '870.75 488.25 32.63 51.75'),
       (310, 3, 3, false, true, '905.62 488.25 83.25 51.75'),
       (311, 3, 3, false, true, '991.13 488.25 83.25 51.75'),
       (312, 4, 3, false, true, '1076.63 445.5 75.38 94.5'),
       (313, 1, 3, false, false, '253.12 407.25 32.63 51.75'),
       (314, 3, 3, false, true, '288 407.25 83.25 51.75'),
       (315, 1, 3, false, false, '430.87 407.25 32.63 51.75'),
       (316, 3, 3, false, true, '465.75 407.25 83.25 51.75'),
       (317, 1, 3, false, false, '693 407.25 32.63 51.75'),
       (318, 3, 3, false, true, '727.87 407.25 83.25 51.75'),
       (319, 1, 3, false, false, '870.75 407.25 32.63 51.75'),
       (320, 3, 3, true, true, '905.62 407.25 83.25 51.75'),
       (321, 3, 3, true, true, '527.62 257.63 51.75 83.25'),
       (322, 3, 3, true, true, '608.62 257.63 51.75 83.25'),
       (323, 2, 3, true, true, '608.62 200.25 51.75 55.13'),
       (324, 3, 3, true, true, '527.62 114.75 51.75 83.25'),
       (325, 4, 3, true, true, '608.62 114.75 68.63 83.25'),
       (326, 2, 3, true, true, '608.62 57.38 51.75 55.13'),
       (327, 2, 3, true, true, '608.62 0 51.75 55.13'),
       (400, 2, 4, true, true, '195.75 488.25 55.13 51.75'),
       (401, 4, 4, true, true, '253.12 488.25 118.13 51.75'),
       (402, 1, 4, true, false, '373.5 488.25 26.44 51.75'),
       (403, 1, 4, false, false, '402.19 488.25 26.44 51.75'),
       (404, 4, 4, false, true, '430.87 488.25 118.13 51.75'),
       (405, 3, 4, false, true, '551.25 488.25 83.25 51.75'),
       (406, 4, 4, false, true, '693 488.25 118.13 51.75'),
       (407, 2, 4, false, true, '813.37 488.25 55.13 51.75'),
       (408, 4, 4, false, true, '870.75 488.25 118.13 51.75'),
       (409, 3, 4, true, true, '991.13 488.25 83.25 51.75'),
       (410, 4, 4, true, true, '1076.63 445.5 75.38 94.5'),
       (411, 4, 4, true, true, '253.12 407.25 118.13 51.75'),
       (412, 4, 4, true, true, '430.87 407.25 118.13 51.75'),
       (413, 4, 4, true, true, '693 407.25 118.13 51.75'),
       (414, 4, 4, true, true, '870.75 407.25 118.13 51.75'),
       (415, 3, 4, true, true, '527.62 257.63 51.75 83.25'),
       (416, 3, 4, true, true, '608.62 257.63 51.75 83.25'),
       (417, 1, 4, false, false, '608.62 228.94 51.75 26.44'),
       (418, 1, 4, false, false, '608.62 200.25 51.75 26.44'),
       (419, 3, 4, false, true, '527.62 114.75 51.75 83.25'),
       (420, 4, 4, false, true, '608.62 114.75 68.63 83.25'),
       (421, 2, 4, false, true, '608.62 57.38 51.75 55.13'),
       (422, 2, 4, false, true, '608.62 0 51.75 55.13');

INSERT INTO room_comments
VALUES (0, STR_TO_DATE('9,7,2020 14,30,0', '%d,%m,%Y %H,%i,%s'), 'Hello room!', 201),
       (0, STR_TO_DATE('9,7,2020 14,30,0', '%d,%m,%Y %H,%i,%s'), 'Found a lost red ring, placed it on the table', 200);

INSERT INTO reservations
VALUES (1, 110, STR_TO_DATE('1,6,2020 1,30,0', '%d,%m,%Y %H,%i,%s'),
        STR_TO_DATE('1,10,2020 2,30,0', '%d,%m,%Y %H,%i,%s'), false, 1),
       (2, 115, STR_TO_DATE('1,8,2020 2,30,0', '%d,%m,%Y %H,%i,%s'),
        STR_TO_DATE('1,8,2020 3,0,0', '%d,%m,%Y %H,%i,%s'), false, 1),
       (3, 208, STR_TO_DATE('1,8,2020 3,30,0', '%d,%m,%Y %H,%i,%s'),
        STR_TO_DATE('1,8,2020 4,30,0', '%d,%m,%Y %H,%i,%s'), false, 1),
       (4, 209, STR_TO_DATE('1,7,2020 4,30,0', '%d,%m,%Y %H,%i,%s'),
        STR_TO_DATE('1,9,2020 6,30,0', '%d,%m,%Y %H,%i,%s'), false, 1),
       (5, 210, STR_TO_DATE('1,7,2020 5,30,0', '%d,%m,%Y %H,%i,%s'),
        STR_TO_DATE('1,9,2020 7,30,0', '%d,%m,%Y %H,%i,%s'), false, 1),
       (6, 212, STR_TO_DATE('1,8,2020 6,30,0', '%d,%m,%Y %H,%i,%s'),
        STR_TO_DATE('1,8,2020 8,30,0', '%d,%m,%Y %H,%i,%s'), false, 1),
       (7, 307, STR_TO_DATE('26,2,2020 1,30,0', '%d,%m,%Y %H,%i,%s'),
        STR_TO_DATE('26,9,2020 2,30,0', '%d,%m,%Y %H,%i,%s'), false, 1),
       (8, 310, STR_TO_DATE('26,2,2020 2,30,0', '%d,%m,%Y %H,%i,%s'),
        STR_TO_DATE('26,9,2020 3,0,0', '%d,%m,%Y %H,%i,%s'), false, 1),
       (9, 400, STR_TO_DATE('26,2,2020 3,30,0', '%d,%m,%Y %H,%i,%s'),
        STR_TO_DATE('26,9,2020 4,30,0', '%d,%m,%Y %H,%i,%s'), false, 1),
       (10, 402, STR_TO_DATE('26,2,2020 4,30,0', '%d,%m,%Y %H,%i,%s'),
        STR_TO_DATE('26,9,2020 22,30,0', '%d,%m,%Y %H,%i,%s'), false, 1),
       (11, 412, STR_TO_DATE('26,2,2020 5,30,0', '%d,%m,%Y %H,%i,%s'),
        STR_TO_DATE('26,9,2020 7,30,0', '%d,%m,%Y %H,%i,%s'), false, 1),
       (12, 415, STR_TO_DATE('26,2,2020 6,30,0', '%d,%m,%Y %H,%i,%s'),
        STR_TO_DATE('26,9,2020 23,00,0', '%d,%m,%Y %H,%i,%s'), false, 1),
       (13, 409, STR_TO_DATE('26,7,2020 1,30,0', '%d,%m,%Y %H,%i,%s'),
        STR_TO_DATE('26,7,2020 5,30,0', '%d,%m,%Y %H,%i,%s'), true, 1),
       (14, 200, STR_TO_DATE('2,8,2020 13,30,0', '%d,%m,%Y %H,%i,%s'),
        STR_TO_DATE('2,8,2020 14,30,0', '%d,%m,%Y %H,%i,%s'), false, 1),
       (15, 200, STR_TO_DATE('2,8,2020 14,40,0', '%d,%m,%Y %H,%i,%s'),
        STR_TO_DATE('2,8,2020 16,10,0', '%d,%m,%Y %H,%i,%s'), false, 1),
       (16, 200, STR_TO_DATE('3,8,2020 6,30,0', '%d,%m,%Y %H,%i,%s'),
        STR_TO_DATE('3,8,2020 9,00,0', '%d,%m,%Y %H,%i,%s'), false, 1);

INSERT INTO reservations
VALUES (0, 105, STR_TO_DATE('3,8,2020 15,0,0', '%d,%m,%Y %H,%i,%s'),
        STR_TO_DATE('3,8,2020 21,30,0', '%d,%m,%Y %H,%i,%s'), true, 1),
       (0, 201, STR_TO_DATE('4,8,2020 18,30,0', '%d,%m,%Y %H,%i,%s'),
        STR_TO_DATE('4,8,2020 19,30,0', '%d,%m,%Y %H,%i,%s'), true, 1),
       (0, 208, STR_TO_DATE('5,8,2020 11,30,0', '%d,%m,%Y %H,%i,%s'),
        STR_TO_DATE('5,8,2020 14,0,0', '%d,%m,%Y %H,%i,%s'), true, 1),
       (0, 305, STR_TO_DATE('6,8,2020 17,0,0', '%d,%m,%Y %H,%i,%s'),
        STR_TO_DATE('6,8,2020 23,0,0', '%d,%m,%Y %H,%i,%s'), true, 1),
       (0, 311, STR_TO_DATE('7,8,2020 10,0,0', '%d,%m,%Y %H,%i,%s'),
        STR_TO_DATE('7,8,2020 11,0,0', '%d,%m,%Y %H,%i,%s'), true, 1),
       (0, 401, STR_TO_DATE('8,8,2020 16,30,0', '%d,%m,%Y %H,%i,%s'),
        STR_TO_DATE('8,8,2020 17,30,0', '%d,%m,%Y %H,%i,%s'), true, 1),
       (0, 415, STR_TO_DATE('9,8,2020 19,30,0', '%d,%m,%Y %H,%i,%s'),
        STR_TO_DATE('9,8,2020 20,0,0', '%d,%m,%Y %H,%i,%s'), true, 1);
