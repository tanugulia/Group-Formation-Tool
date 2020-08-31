DELIMITER $$

DROP PROCEDURE IF EXISTS sp_getAdminDetails $$

CREATE PROCEDURE `sp_getAdminDetails`()
BEGIN
SET TRANSACTION ISOLATION LEVEL READ UNCOMMITTED;

	SELECT first_name
		,last_name
		,banner_id
		,email
	FROM users where banner_id = 'B00000000';

	COMMIT;
END $$
DELIMITER ;