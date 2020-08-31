DROP procedure IF EXISTS `sp_addTAToCourse`;
DELIMITER $$
CREATE DEFINER=`CSCI5308_22_TEST_USER`@`%` PROCEDURE `sp_addTAToCourse`(
	IN userId VARCHAR(40)
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
		userId
		,courseId
		,0
		,2
		);
END$$

DELIMITER ;