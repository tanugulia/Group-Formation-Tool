DELIMITER $$

DROP PROCEDURE IF EXISTS sp_getUsersCurrentlyNotInstructorsForCourse $$

CREATE PROCEDURE `sp_getUsersCurrentlyNotInstructorsForCourse`(IN courseId VARCHAR(40))
BEGIN
	SET TRANSACTION ISOLATION LEVEL READ UNCOMMITTED;

	SELECT user_id
	,first_name
	,last_name
	,email
	,banner_id
FROM users
WHERE user_id NOT IN (
		SELECT user_id
		FROM role_assignment
		WHERE course_id = courseId
		)
	AND banner_id <> 'B00000000';

	COMMIT;
	
END $$
DELIMITER ;