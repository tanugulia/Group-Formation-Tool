DELIMITER $$

DROP PROCEDURE IF EXISTS sp_getUserByBannerId $$

CREATE PROCEDURE `sp_getUserByBannerId`(IN bannerId VARCHAR(40))
BEGIN
	SET TRANSACTION ISOLATION LEVEL READ UNCOMMITTED;

	SELECT first_name
		,last_name
		,banner_id
		,email
	FROM users where banner_id = bannerId;

	COMMIT;
	
END $$
DELIMITER ;