DELIMITER $$

DROP PROCEDURE IF EXISTS sp_getUserId $$

CREATE PROCEDURE `sp_getUserId`(IN emailID VARCHAR(40))
BEGIN
	SET TRANSACTION ISOLATION LEVEL READ UNCOMMITTED;
	SELECT user_id,
		first_name
		,last_name
		,banner_id
		,email
	FROM users where email = emailId;

	COMMIT;
END $$
DELIMITER ;