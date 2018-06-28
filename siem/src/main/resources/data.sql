INSERT INTO authority(
	id, authority_name)
	VALUES (-1, 'ROLE_ADMIN');

INSERT INTO authority(
	id, authority_name)
	VALUES (-2, 'ROLE_USER');

INSERT INTO user(
	id, password, username)
	VALUES (-1, '$2a$10$S3rxpwjnJUrmgMrnMCJo8eIRCFvCcmzuPi5Y3Okz67i/2sj6xMfau', 'a');


INSERT INTO user(
	id, password, username)
	VALUES (-3, '$2a$10$KHxkSUE1e2nXDByQi4fop.ucTF0KM1NGh5XSHTqX8azCMvOZRMDw2', 'user');

INSERT INTO user(
	id, password, username)
	VALUES (-4, '$2a$10$KHxkSUE1e2nXDByQi4fop.ucTF0KM1NGh5XSHTqX8azCMvOZRMDw2', 'admin');

INSERT INTO user_authority(
	id, authority_id, user_id)
	VALUES (-1, -1, -1);

INSERT INTO user(
	id, password, username)
	VALUES (1, '$2a$10$S3rxpwjnJUrmgMrnMCJo8eIRCFvCcmzuPi5Y3Okz67i/2sj6xMfau', 'b');

INSERT INTO user_authority(
	id, authority_id, user_id)
	VALUES (1, -2, 1);

INSERT INTO user_authority(
	id, authority_id, user_id)
	VALUES (-3, -2, -3);
	
INSERT INTO user_authority(
	id, authority_id, user_id)
	VALUES (-4, -1, -4);