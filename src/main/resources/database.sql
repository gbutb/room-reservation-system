USE reservations_db;

DROP TABLE IF EXISTS reservations;
DROP TABLE IF EXISTS rooms;
DROP TABLE IF EXISTS room_comment;
DROP TABLE IF EXISTS accounts;

CREATE TABLE room_comment (
	comment_id INT PRIMARY KEY,
	comment_date DATETIME,
	user_comment VARCHAR(1000)
);

CREATE TABLE rooms (
	room_id INT PRIMARY KEY,
	room_size INT,
	floor INT,
	conditioner BOOLEAN,
	projector BOOLEAN,
	comment_id INT,
	FOREIGN KEY (comment_id) REFERENCES room_comment(comment_id)
);

CREATE TABLE accounts (
	account_id INT PRIMARY KEY,
	username VARCHAR(255),
	encryptedPassword VARCHAR(255),
	email VARCHAR(255)
);

CREATE TABLE reservations (
	reservation_id INT PRIMARY KEY,
	room_id INT,
	start_date DATETIME,
	end_date DATETIME,
	do_repeat BOOLEAN,
	account_id INT,
	FOREIGN KEY (room_id) REFERENCES rooms(room_id),
	FOREIGN KEY (account_id) REFERENCES accounts(account_id)
);
	
INSERT INTO room_comment VALUES
	(0, STR_TO_DATE("9,7,2020 14,30,0", "%d,%m,%Y %H,%i,%s"), "Hello room!");

INSERT INTO rooms VALUES
	(200, 25, 2, true, true, 0),
	(315, 20, 3, false, true, 0);
	
INSERT INTO accounts VALUES
	(0, "Human", "human_password", "human@humans.org"),
	(1, "Human 1", "human_1_password", "human1@humans.org");
	
INSERT INTO reservations VALUES
	(0, 200, STR_TO_DATE("10,7,2020 14,40,0", "%d,%m,%Y %H,%i,%s"), 
			 STR_TO_DATE("10,7,2020 15,40,0", "%d,%m,%Y %H,%i,%s"), false, 0);
