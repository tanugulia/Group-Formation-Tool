
DROP procedure IF EXISTS `sp_getInstructorCoursesByEmailId`;

DELIMITER $$

CREATE PROCEDURE `sp_getInstructorCoursesByEmailId`(IN emailId VARCHAR(255))
BEGIN
	with course_info as (Select distinct course_id from role_assignment where user_id = 
	(select User_id from users where email=emailId)  and role_id = 1)
	Select * from course join course_info using (course_id);
END$$

DELIMITER ;