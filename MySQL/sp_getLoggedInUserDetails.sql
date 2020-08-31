DELIMITER $$

DROP PROCEDURE IF EXISTS sp_getLoggedInUserDetails $$

CREATE PROCEDURE `sp_getLoggedInUserDetails`(
	IN emailId VARCHAR(255)
	,IN encryptedPassword VARCHAR(255)
	)
BEGIN
	SET TRANSACTION ISOLATION LEVEL READ UNCOMMITTED;

	SELECT first_name
		,last_name
		,banner_id
		,email
		,password
	FROM users
	WHERE email = emailId
		AND password = encryptedPassword;

	COMMIT;
END $$
DELIMITER ;