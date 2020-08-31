DELIMITER $$

DROP PROCEDURE IF EXISTS sp_getRoleBasedCourses $$

CREATE PROCEDURE `sp_getRoleBasedCourses`(IN emailId VARCHAR(255))
BEGIN

SET TRANSACTION ISOLATION LEVEL READ UNCOMMITTED;

	SELECT banner_id
		,C.course_id
		,C.course_name
		,C.course_details
		,role_type AS user_role
	FROM users AS U
	INNER JOIN role_assignment AS RA ON U.user_id = RA.user_id
    INNER JOIN course AS C ON C.course_id = RA.course_id
	INNER JOIN roles R ON R.role_id = RA.role_id
    where U.email = emailId;
    
    COMMIT;
    
END $$;
DELIMITER ;