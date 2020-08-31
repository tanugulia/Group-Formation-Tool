DELIMITER $$

DROP PROCEDURE IF EXISTS sp_getUserByEmailId $$

CREATE DEFINER=`CSCI5308_22_TEST`@`%` PROCEDURE `sp_getUserByEmailId`(IN emailId VARCHAR(255))
BEGIN
	SET TRANSACTION ISOLATION LEVEL READ UNCOMMITTED;

	SELECT first_name
		,last_name
		,banner_id
		,email
	FROM users where email = emailId;

	COMMIT;
	
END $$
DELIMITER ;