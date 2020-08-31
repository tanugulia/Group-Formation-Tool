DELIMITER $$

DROP PROCEDURE IF EXISTS sp_deleteACourse $$

CREATE PROCEDURE `sp_deleteACourse`(IN courseId VARCHAR(40),
OUT deleteStatus BOOLEAN
)
BEGIN
	IF EXISTS(Select * from role_assignment where course_id = courseId)
    THEN
    SET deleteStatus = false;
    ELSE
	Delete FROM course where course_id = courseId;
    SET deleteStatus = true;
END IF;
	COMMIT;
END $$
DELIMITER ;