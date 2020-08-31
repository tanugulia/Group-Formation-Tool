DROP PROCEDURE IF EXISTS sp_createCourse $$
DELIMITER $$
CREATE DEFINER=`CSCI5308_22_TEST_USER`@`%` PROCEDURE `sp_createCourse`(
	 IN courseId VARCHAR(40)
	,IN course_name VARCHAR(40)
	,IN course_credits VARCHAR(40)
	,IN course_details VARCHAR(255)
    ,OUT createStatus BOOLEAN
	)
BEGIN
IF NOT EXISTS(Select * from course where course_id = courseId)
THEN
	INSERT INTO course(
		course_id
		,course_name
		,course_credits
		,course_details
		)
	VALUES(
		courseId
		,course_name
		,course_credits
		,course_details
		);
 SET createStatus = true;
 ELSE
 SET createStatus = false;
 END IF;
END $$
DELIMITER ;