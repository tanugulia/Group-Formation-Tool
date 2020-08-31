DELIMITER $$

DROP PROCEDURE IF EXISTS sp_getInstructorsForCourse $$

CREATE PROCEDURE `sp_getInstructorsForCourse`(IN courseId VARCHAR(40))
BEGIN
	SET TRANSACTION ISOLATION LEVEL READ UNCOMMITTED;

	SELECT first_name
		,last_name
		,banner_id
		,email
	FROM users
	WHERE user_id IN (
			SELECT user_id
			FROM role_assignment
			WHERE course_id = courseId
			            AND role_id = 1
			);
	COMMIT;
END $$
DELIMITER ;