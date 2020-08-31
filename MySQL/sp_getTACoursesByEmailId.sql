
DROP procedure IF EXISTS `sp_getTACoursesByEmailId`;

DELIMITER $$

CREATE PROCEDURE `sp_getTACoursesByEmailId` ()
BEGIN
	with course_info as (Select distinct course_id from role_assignment where user_id = 
	(select User_id from users where email=emailId)  and role_id = 2)
	Select * from course join course_info using (course_id);
END$$

DELIMITER ;