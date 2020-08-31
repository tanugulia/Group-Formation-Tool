DELIMITER $$

DROP PROCEDURE IF EXISTS sp_updateToken $$

CREATE PROCEDURE `sp_updateToken`(
IN emailID VARCHAR(255),
IN token VARCHAR(255)
)
BEGIN
	SET TRANSACTION ISOLATION LEVEL READ UNCOMMITTED;
	UPDATE forgot_password set temporary_lik = token where email = emailID;
	COMMIT;
END $$
DELIMITER ;