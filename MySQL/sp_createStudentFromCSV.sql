DROP PROCEDURE IF EXISTS sp_createStudentFromCSV $$
DELIMITER $$
CREATE DEFINER=`CSCI5308_22_TEST_USER`@`%` PROCEDURE `sp_createStudentFromCSV`(
	IN bannerId VARCHAR(40)
	,IN firstName VARCHAR(40)
	,IN lastName VARCHAR(40)
	,IN emailId VARCHAR(255)
	,IN password VARCHAR(255)
    ,IN courseId VARCHAR(40)
    ,OUT stuStatus BOOLEAN
	)
BEGIN
	IF NOT EXISTS(Select * from users where email = emailId)
    THEN
	INSERT INTO users(
		first_name
		,last_name
		,email
		,password
		,banner_id
		)
	VALUES(
		firstName
		,lastName
		,emailId
		,password
		,bannerId
		);  
	INSERT INTO role_assignment (
	 user_id
	,course_id
	,yt_id
	,role_id
	)
	VALUES
	((select user_id from users where email = emailId)
	,courseId
	,0
	,3
	);
    SET stuStatus = TRUE;
    ELSE
	UPDATE users set banner_id = bannerId, first_name = firstName, last_name = lastName where email = emailId;
    IF NOT EXISTS(SELECT * from role_assignment where user_id = (SELECT user_id from users where email = emailId) AND course_id = courseId)
    THEN
    INSERT INTO role_assignment (
	 user_id
	,course_id
	,yt_id
	,role_id
	)
	VALUES
	((select user_id from users where email = emailId)
	,courseId
	,0
	,3
	);
    SET stuStatus = FALSE;
    END IF;
    END IF;
    COMMIT;
    END $$
DELIMITER ;