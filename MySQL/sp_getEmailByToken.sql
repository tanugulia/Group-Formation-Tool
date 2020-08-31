DELIMITER $$
CREATE PROCEDURE `sp_getEmailByToken`(IN token VARCHAR(255))
BEGIN
	SET TRANSACTION ISOLATION LEVEL READ UNCOMMITTED;
	SELECT user_id,email
	FROM forgot_password where temporary_lik = token;
	COMMIT;
END$$
DELIMITER ;
