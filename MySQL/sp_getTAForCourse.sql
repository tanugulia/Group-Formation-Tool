
DROP procedure IF EXISTS `sp_getTAForCourse`;
DELIMITER $$

CREATE PROCEDURE `sp_getTAForCourse` (IN courseId VARCHAR(40))
BEGIN
	with user_info as (select user_id from role_assignment where course_id = courseId and role_id =2)
	select * from users join user_info using(user_id);
END$$

DELIMITER ;