USE reservations_db;

DROP TABLE IF EXISTS reservations;
DROP TABLE IF EXISTS rooms;
DROP TABLE IF EXISTS room_comment;
DROP TABLE IF EXISTS accounts;

CREATE TABLE room_comment
(
    comment_id   INT PRIMARY KEY,
    comment_date DATETIME,
    user_comment VARCHAR(255)
);

CREATE TABLE rooms
(
    room_id         INT PRIMARY KEY,
    room_size       INT,
    floor           INT,
    air_conditioner BOOLEAN,
    projector       BOOLEAN,
    comment_id      INT,
    render_data     VARCHAR(255),
    FOREIGN KEY (comment_id) REFERENCES room_comment (comment_id)
);

CREATE TABLE accounts
(
    account_id        INT PRIMARY KEY,
    username          VARCHAR(255),
    encryptedPassword VARCHAR(255),
    email             VARCHAR(255)
);

CREATE TABLE reservations
(
    reservation_id INT PRIMARY KEY,
    room_id        INT,
    start_date     DATETIME,
    end_date       DATETIME,
    do_repeat      BOOLEAN,
    account_id     INT,
    FOREIGN KEY (room_id) REFERENCES rooms (room_id),
    FOREIGN KEY (account_id) REFERENCES accounts (account_id)
);

INSERT INTO room_comment
VALUES (0, STR_TO_DATE('9,7,2020 14,30,0', '%d,%m,%Y %H,%i,%s'), 'Hello room!');


INSERT INTO accounts
VALUES (0, 'Human', '$2a$10$1jkMI4yZEz0GvzAzVuwqUeHaEaOtKSntbVeFcO4TN3rJKW6U2qqtO', 'human@humans.org'),
       (1, 'Human 1', '$2a$10$1jkMI4yZEz0GvzAzVuwqUeHaEaOtKSntbVeFcO4TN3rJKW6U2qqtO', 'human1@humans.org');

INSERT INTO rooms
VALUES (100, 4, 1, false, true, 0, '253.12 488.25 118.13 51.75'),
       (101, 2, 1, false, true, 0, '373.5 488.25 55.13 51.75'),
       (102, 4, 1, false, true, 0, '430.87 488.25 118.13 51.75'),
       (103, 3, 1, true, true, 0, '551.25 488.25 83.25 51.75'),
       (104, 4, 1, true, false, 0, '693 488.25 118.13 51.75'),
       (105, 2, 1, true, false, 0, '813.37 488.25 55.13 51.75'),
       (106, 4, 1, true, true, 0, '870.75 488.25 118.13 51.75'),
       (107, 3, 1, false, true, 0, '991.13 488.25 83.25 51.75'),
       (108, 4, 1, false, false, 0, '1076.63 445.5 75.38 94.5'),
       (109, 4, 1, false, false, 0, '253.12 407.25 118.13 51.75'),
       (110, 4, 1, false, true, 0, '430.87 407.25 118.13 51.75'),
       (111, 4, 1, false, false, 0, '693 407.25 118.13 51.75'),
       (112, 4, 1, false, false, 0, '870.75 407.25 118.13 51.75'),
       (113, 3, 1, false, false, 0, '527.62 257.63 51.75 83.25'),
       (114, 3, 1, true, true, 0, '608.62 257.63 51.75 83.25'),
       (115, 2, 1, true, true, 0, '608.62 200.25 51.75 55.13'),
       (116, 3, 1, true, false, 0, '527.62 114.75 51.75 83.25'),
       (117, 3, 1, true, false, 0, '608.62 114.75 51.75 83.25'),
       (118, 2, 1, true, false, 0, '608.62 57.38 51.75 55.13'),
       (119, 2, 1, true, true, 0, '608.62 0 51.75 55.13'),
       (200, 4, 2, true, true, 0, '0 429.75 108 110.25'),
       (201, 2, 2, true, true, 0, '138.37 488.25 55.13 51.75'),
       (202, 2, 2, true, true, 0, '195.75 488.25 55.13 51.75'),
       (203, 4, 2, true, true, 0, '253.12 488.25 118.13 51.75'),
       (204, 2, 2, true, true, 0, '373.5 488.25 55.13 51.75'),
       (205, 4, 2, true, true, 0, '430.87 488.25 118.13 51.75'),
       (206, 3, 2, true, true, 0, '551.25 488.25 83.25 51.75'),
       (207, 1, 2, true, false, 0, '693 488.25 32.63 51.75'),
       (208, 3, 2, false, true, 0, '727.87 488.25 83.25 51.75'),
       (209, 2, 2, false, true, 0, '813.37 488.25 55.13 51.75'),
       (210, 4, 2, false, true, 0, '870.75 488.25 118.13 51.75'),
       (211, 3, 2, true, true, 0, '991.13 488.25 83.25 51.75'),
       (212, 4, 2, true, true, 0, '1076.63 445.5 75.38 94.5'),
       (213, 4, 2, true, true, 0, '253.12 407.25 118.13 51.75'),
       (214, 4, 2, true, true, 0, '430.87 407.25 118.13 51.75'),
       (215, 1, 2, true, false, 0, '693 407.25 32.63 51.75'),
       (216, 3, 2, false, true, 0, '727.87 407.25 83.25 51.75'),
       (217, 1, 2, false, false, 0, '870.75 407.25 32.62 51.75'),
       (218, 3, 2, false, true, 0, '905.62 407.25 83.25 51.75'),
       (219, 3, 2, false, true, 0, '527.62 257.63 51.75 83.25'),
       (220, 3, 2, false, true, 0, '608.62 257.63 51.75 83.25'),
       (221, 2, 2, true, true, 0, '608.62 200.25 51.75 55.13'),
       (222, 3, 2, true, true, 0, '527.62 114.75 51.75 83.25'),
       (223, 4, 2, true, true, 0, '608.62 114.75 68.63 83.25'),
       (224, 2, 2, true, true, 0, '608.62 57.38 51.75 55.13'),
       (225, 2, 2, true, true, 0, '608.62 0 51.75 55.13'),
       (300, 2, 3, true, true, 0, '138.37 488.25 55.13 51.75'),
       (301, 2, 3, true, true, 0, '195.75 488.25 55.13 51.75'),
       (302, 4, 3, true, true, 0, '253.12 488.25 118.13 51.75'),
       (303, 2, 3, true, true, 0, '373.5 488.25 55.13 51.75'),
       (304, 4, 3, true, true, 0, '430.87 488.25 118.13 51.75'),
       (305, 3, 3, true, true, 0, '551.25 488.25 83.25 51.75'),
       (306, 1, 3, true, false, 0, '693 488.25 32.63 51.75'),
       (307, 3, 3, true, true, 0, '727.87 488.25 83.25 51.75'),
       (308, 2, 3, false, true, 0, '813.37 488.25 55.13 51.75'),
       (309, 1, 3, false, false, 0, '870.75 488.25 32.63 51.75'),
       (310, 3, 3, false, true, 0, '905.62 488.25 83.25 51.75'),
       (311, 3, 3, false, true, 0, '991.13 488.25 83.25 51.75'),
       (312, 4, 3, false, true, 0, '1076.63 445.5 75.38 94.5'),
       (313, 1, 3, false, false, 0, '253.12 407.25 32.63 51.75'),
       (314, 3, 3, false, true, 0, '288 407.25 83.25 51.75'),
       (315, 1, 3, false, false, 0, '430.87 407.25 32.63 51.75'),
       (316, 3, 3, false, true, 0, '465.75 407.25 83.25 51.75'),
       (317, 1, 3, false, false, 0, '693 407.25 32.63 51.75'),
       (318, 3, 3, false, true, 0, '727.87 407.25 83.25 51.75'),
       (319, 1, 3, false, false, 0, '870.75 407.25 32.63 51.75'),
       (320, 3, 3, true, true, 0, '905.62 407.25 83.25 51.75'),
       (321, 3, 3, true, true, 0, '527.62 257.63 51.75 83.25'),
       (322, 3, 3, true, true, 0, '608.62 257.63 51.75 83.25'),
       (323, 2, 3, true, true, 0, '608.62 200.25 51.75 55.13'),
       (324, 3, 3, true, true, 0, '527.62 114.75 51.75 83.25'),
       (325, 4, 3, true, true, 0, '608.62 114.75 68.63 83.25'),
       (326, 2, 3, true, true, 0, '608.62 57.38 51.75 55.13'),
       (327, 2, 3, true, true, 0, '608.62 0 51.75 55.13'),
       (400, 2, 4, true, true, 0, '195.75 488.25 55.13 51.75'),
       (401, 4, 4, true, true, 0, '253.12 488.25 118.13 51.75'),
       (402, 1, 4, true, false, 0, '373.5 488.25 26.44 51.75'),
       (403, 1, 4, false, false, 0, '402.19 488.25 26.44 51.75'),
       (404, 4, 4, false, true, 0, '430.87 488.25 118.13 51.75'),
       (405, 3, 4, false, true, 0, '551.25 488.25 83.25 51.75'),
       (406, 4, 4, false, true, 0, '693 488.25 118.13 51.75'),
       (407, 2, 4, false, true, 0, '813.37 488.25 55.13 51.75'),
       (408, 4, 4, false, true, 0, '870.75 488.25 118.13 51.75'),
       (409, 3, 4, true, true, 0, '991.13 488.25 83.25 51.75'),
       (410, 4, 4, true, true, 0, '1076.63 445.5 75.38 94.5'),
       (411, 4, 4, true, true, 0, '253.12 407.25 118.13 51.75'),
       (412, 4, 4, true, true, 0, '430.87 407.25 118.13 51.75'),
       (413, 4, 4, true, true, 0, '693 407.25 118.13 51.75'),
       (414, 4, 4, true, true, 0, '870.75 407.25 118.13 51.75'),
       (415, 3, 4, true, true, 0, '527.62 257.63 51.75 83.25'),
       (416, 3, 4, true, true, 0, '608.62 257.63 51.75 83.25'),
       (417, 1, 4, false, false, 0, '608.62 228.94 51.75 26.44'),
       (418, 1, 4, false, false, 0, '608.62 200.25 51.75 26.44'),
       (419, 3, 4, false, true, 0, '527.62 114.75 51.75 83.25'),
       (420, 4, 4, false, true, 0, '608.62 114.75 68.63 83.25'),
       (421, 2, 4, false, true, 0, '608.62 57.38 51.75 55.13'),
       (422, 2, 4, false, true, 0, '608.62 0 51.75 55.13');

INSERT INTO reservations
VALUES (0, 115, STR_TO_DATE('1,8,2020 1,30,0', '%d,%m,%Y %H,%i,%s'),
        STR_TO_DATE('1,8,2020 2,30,0', '%d,%m,%Y %H,%i,%s'), true, 0),
       (1, 202, STR_TO_DATE('1,8,2020 2,30,0', '%d,%m,%Y %H,%i,%s'),
        STR_TO_DATE('1,8,2020 3,0,0', '%d,%m,%Y %H,%i,%s'), true, 0),
       (2, 208, STR_TO_DATE('1,8,2020 3,30,0', '%d,%m,%Y %H,%i,%s'),
        STR_TO_DATE('1,8,2020 4,30,0', '%d,%m,%Y %H,%i,%s'), true, 0),
       (3, 314, STR_TO_DATE('1,8,2020 4,30,0', '%d,%m,%Y %H,%i,%s'),
        STR_TO_DATE('1,8,2020 6,30,0', '%d,%m,%Y %H,%i,%s'), true, 0),
       (4, 317, STR_TO_DATE('1,8,2020 5,30,0', '%d,%m,%Y %H,%i,%s'),
        STR_TO_DATE('1,8,2020 7,30,0', '%d,%m,%Y %H,%i,%s'), true, 0),
       (5, 415, STR_TO_DATE('1,8,2020 6,30,0', '%d,%m,%Y %H,%i,%s'),
        STR_TO_DATE('1,8,2020 8,30,0', '%d,%m,%Y %H,%i,%s'), true, 0);

