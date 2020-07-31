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
    account_id        INT NOT NULL AUTO_INCREMENT,
    username          VARCHAR(255),
    encryptedPassword VARCHAR(255),
    email             VARCHAR(255),

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
	
INSERT INTO room_comment VALUES
	(0, STR_TO_DATE('9,7,2020 14,30,0', '%d,%m,%Y %H,%i,%s'), 'Hello room!');

	
INSERT INTO accounts VALUES
	(0,'Human', '$2a$10$1jkMI4yZEz0GvzAzVuwqUeHaEaOtKSntbVeFcO4TN3rJKW6U2qqtO', 'human@humans.org'),
	(0,'Human 1', '$2a$10$1jkMI4yZEz0GvzAzVuwqUeHaEaOtKSntbVeFcO4TN3rJKW6U2qqtO', 'human1@humans.org');

INSERT INTO rooms
VALUES (100, 1, 1, true, true, 0, '253.12 488.25 118.13 51.75'),
       (101, 1, 1, true, true, 0, '373.5 488.25 55.13 51.75'),
       (102, 1, 1, true, true, 0, '430.87 488.25 118.13 51.75'),
       (103, 1, 1, true, true, 0, '551.25 488.25 83.25 51.75'),
       (104, 1, 1, true, true, 0, '693 488.25 118.13 51.75'),
       (105, 1, 1, true, true, 0, '813.37 488.25 55.13 51.75'),
       (106, 1, 1, true, true, 0, '870.75 488.25 118.13 51.75'),
       (107, 1, 1, true, true, 0, '991.13 488.25 83.25 51.75'),
       (108, 1, 1, true, true, 0, '1076.63 445.5 75.38 94.5'),
       (109, 1, 1, true, true, 0, '253.12 407.25 118.13 51.75'),
       (110, 1, 1, true, true, 0, '430.87 407.25 118.13 51.75'),
       (111, 1, 1, true, true, 0, '693 407.25 118.13 51.75'),
       (112, 1, 1, true, true, 0, '870.75 407.25 118.13 51.75'),
       (113, 1, 1, true, true, 0, '527.62 257.63 51.75 83.25'),
       (114, 1, 1, true, true, 0, '608.62 257.63 51.75 83.25'),
       (115, 1, 1, true, true, 0, '608.62 200.25 51.75 55.13'),
       (116, 1, 1, true, true, 0, '527.62 114.75 51.75 83.25'),
       (117, 1, 1, true, true, 0, '608.62 114.75 51.75 83.25'),
       (118, 1, 1, true, true, 0, '608.62 57.38 51.75 55.13'),
       (119, 1, 1, true, true, 0, '608.62 0 51.75 55.13'),
       (200, 1, 2, true, true, 0, '0 429.75 108 110.25'),
       (201, 1, 2, true, true, 0, '138.37 488.25 55.13 51.75'),
       (202, 1, 2, true, true, 0, '195.75 488.25 55.13 51.75'),
       (203, 1, 2, true, true, 0, '253.12 488.25 118.13 51.75'),
       (204, 1, 2, true, true, 0, '373.5 488.25 55.13 51.75'),
       (205, 1, 2, true, true, 0, '430.87 488.25 118.13 51.75'),
       (206, 1, 2, true, true, 0, '551.25 488.25 83.25 51.75'),
       (207, 1, 2, true, true, 0, '693 488.25 32.63 51.75'),
       (208, 1, 2, true, true, 0, '727.87 488.25 83.25 51.75'),
       (209, 1, 2, true, true, 0, '813.37 488.25 55.13 51.75'),
       (210, 1, 2, true, true, 0, '870.75 488.25 118.13 51.75'),
       (211, 1, 2, true, true, 0, '991.13 488.25 83.25 51.75'),
       (212, 1, 2, true, true, 0, '1076.63 445.5 75.38 94.5'),
       (213, 1, 2, true, true, 0, '253.12 407.25 118.13 51.75'),
       (214, 1, 2, true, true, 0, '430.87 407.25 118.13 51.75'),
       (215, 1, 2, true, true, 0, '693 407.25 32.63 51.75'),
       (216, 1, 2, true, true, 0, '727.87 407.25 83.25 51.75'),
       (217, 1, 2, true, true, 0, '870.75 407.25 32.62 51.75'),
       (218, 1, 2, true, true, 0, '905.62 407.25 83.25 51.75'),
       (219, 1, 2, true, true, 0, '527.62 257.63 51.75 83.25'),
       (220, 1, 2, true, true, 0, '608.62 257.63 51.75 83.25'),
       (221, 1, 2, true, true, 0, '608.62 200.25 51.75 55.13'),
       (222, 1, 2, true, true, 0, '527.62 114.75 51.75 83.25'),
       (223, 1, 2, true, true, 0, '608.62 114.75 68.63 83.25'),
       (224, 1, 2, true, true, 0, '608.62 57.38 51.75 55.13'),
       (225, 1, 2, true, true, 0, '608.62 0 51.75 55.13'),
       (300, 1, 3, true, true, 0, '138.37 488.25 55.13 51.75'),
       (301, 1, 3, true, true, 0, '195.75 488.25 55.13 51.75'),
       (302, 1, 3, true, true, 0, '253.12 488.25 118.13 51.75'),
       (303, 1, 3, true, true, 0, '373.5 488.25 55.13 51.75'),
       (304, 1, 3, true, true, 0, '430.87 488.25 118.13 51.75'),
       (305, 1, 3, true, true, 0, '551.25 488.25 83.25 51.75'),
       (306, 1, 3, true, true, 0, '693 488.25 32.63 51.75'),
       (307, 1, 3, true, true, 0, '727.87 488.25 83.25 51.75'),
       (308, 1, 3, true, true, 0, '813.37 488.25 55.13 51.75'),
       (309, 1, 3, true, true, 0, '870.75 488.25 32.63 51.75'),
       (310, 1, 3, true, true, 0, '905.62 488.25 83.25 51.75'),
       (311, 1, 3, true, true, 0, '991.13 488.25 83.25 51.75'),
       (312, 1, 3, true, true, 0, '1076.63 445.5 75.38 94.5'),
       (313, 1, 3, true, true, 0, '253.12 407.25 32.63 51.75'),
       (314, 1, 3, true, true, 0, '288 407.25 83.25 51.75'),
       (315, 1, 3, true, true, 0, '430.87 407.25 32.63 51.75'),
       (316, 1, 3, true, true, 0, '465.75 407.25 83.25 51.75'),
       (317, 1, 3, true, true, 0, '693 407.25 32.63 51.75'),
       (318, 1, 3, true, true, 0, '727.87 407.25 83.25 51.75'),
       (319, 1, 3, true, true, 0, '870.75 407.25 32.63 51.75'),
       (320, 1, 3, true, true, 0, '905.62 407.25 83.25 51.75'),
       (321, 1, 3, true, true, 0, '527.62 257.63 51.75 83.25'),
       (322, 1, 3, true, true, 0, '608.62 257.63 51.75 83.25'),
       (323, 1, 3, true, true, 0, '608.62 200.25 51.75 55.13'),
       (324, 1, 3, true, true, 0, '527.62 114.75 51.75 83.25'),
       (325, 1, 3, true, true, 0, '608.62 114.75 68.63 83.25'),
       (326, 1, 3, true, true, 0, '608.62 57.38 51.75 55.13'),
       (327, 1, 3, true, true, 0, '608.62 0 51.75 55.13'),
       (400, 1, 4, true, true, 0, '195.75 488.25 55.13 51.75'),
       (401, 1, 4, true, true, 0, '253.12 488.25 118.13 51.75'),
       (402, 1, 4, true, true, 0, '373.5 488.25 26.44 51.75'),
       (403, 1, 4, true, true, 0, '402.19 488.25 26.44 51.75'),
       (404, 1, 4, true, true, 0, '430.87 488.25 118.13 51.75'),
       (405, 1, 4, true, true, 0, '551.25 488.25 83.25 51.75'),
       (406, 1, 4, true, true, 0, '693 488.25 118.13 51.75'),
       (407, 1, 4, true, true, 0, '813.37 488.25 55.13 51.75'),
       (408, 1, 4, true, true, 0, '870.75 488.25 118.13 51.75'),
       (409, 1, 4, true, true, 0, '991.13 488.25 83.25 51.75'),
       (410, 1, 4, true, true, 0, '1076.63 445.5 75.38 94.5'),
       (411, 1, 4, true, true, 0, '253.12 407.25 118.13 51.75'),
       (412, 1, 4, true, true, 0, '430.87 407.25 118.13 51.75'),
       (413, 1, 4, true, true, 0, '693 407.25 118.13 51.75'),
       (414, 1, 4, true, true, 0, '870.75 407.25 118.13 51.75'),
       (415, 1, 4, true, true, 0, '527.62 257.63 51.75 83.25'),
       (416, 1, 4, true, true, 0, '608.62 257.63 51.75 83.25'),
       (417, 1, 4, true, true, 0, '608.62 228.94 51.75 26.44'),
       (418, 1, 4, true, true, 0, '608.62 200.25 51.75 26.44'),
       (419, 1, 4, true, true, 0, '527.62 114.75 51.75 83.25'),
       (420, 1, 4, true, true, 0, '608.62 114.75 68.63 83.25'),
       (421, 1, 4, true, true, 0, '608.62 57.38 51.75 55.13'),
       (422, 1, 4, true, true, 0, '608.62 0 51.75 55.13');

INSERT INTO reservations VALUES
	(0, 200, STR_TO_DATE('10,7,2020 14,40,0', '%d,%m,%Y %H,%i,%s'),
			 STR_TO_DATE('10,7,2020 15,40,0', '%d,%m,%Y %H,%i,%s'), false, 1);

