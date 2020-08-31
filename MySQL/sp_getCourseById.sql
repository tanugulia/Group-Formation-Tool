DELIMITER $$

DROP PROCEDURE IF EXISTS sp_getCourseById $$

CREATE PROCEDURE `sp_getCourseById`(IN courseId VARCHAR(40))
BEGIN
	SET TRANSACTION ISOLATION LEVEL READ UNCOMMITTED;

	SELECT course_id
		,course_name
		,course_details
		,course_credits
	FROM course
	WHERE course_id = courseId;

	COMMIT;
END $$
DELIMITER ;