DELIMITER $$

DROP PROCEDURE IF EXISTS sp_getUserRoleByEmailId $$

CREATE PROCEDURE `sp_getUserRoleByEmailId`(IN emailId VARCHAR(255))
BEGIN


SET TRANSACTION ISOLATION LEVEL READ UNCOMMITTED;

	with role_info as (Select distinct role_id from role_assignment where user_id = 
    (select User_id from users where email=emailId))
		select role_type from roles join role_info using(role_id);
    
    COMMIT;
    
END $$;
DELIMITER ;
