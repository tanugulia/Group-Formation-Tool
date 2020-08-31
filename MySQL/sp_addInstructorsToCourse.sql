DELIMITER $$
DROP PROCEDURE IF EXISTS sp_addInstructorsToCourse $$
CREATE DEFINER=`CSCI5308_22_TEST_USER`@`%` PROCEDURE `sp_addInstructorsToCourse`(
	IN instructor BIGINT
	,IN courseId VARCHAR(40)
	)
BEGIN
	INSERT INTO role_assignment (
		user_id
		,course_id
		,yt_id
		,role_id
		)
	VALUES (
		instructor
		,courseId
		,0
		,1
		);
END