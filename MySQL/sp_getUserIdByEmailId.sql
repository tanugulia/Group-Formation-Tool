DELIMITER $$

DROP PROCEDURE IF EXISTS sp_getUserIdByEmailId $$

CREATE PROCEDURE `sp_getUserIdByEmailId`(IN emailId VARCHAR(255))
BEGIN
	Select user_id from users where email= emailId;
END