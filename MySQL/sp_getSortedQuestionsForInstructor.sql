DROP PROCEDURE IF EXISTS sp_getSortedQuestionsForInstructor $$
DELIMITER $$
CREATE DEFINER=`CSCI5308_22_DEVINT_USER`@`%` PROCEDURE `sp_getSortedQuestionsForInstructor`(IN emailId VARCHAR(255), IN sortParam VARCHAR(255))
BEGIN
	SET TRANSACTION ISOLATION LEVEL READ UNCOMMITTED;
IF sortParam = "title"
THEN
	SELECT question_id
		,title
		,question_text
		,qtype_id
		,first_name
		,last_name
		,banner_id
		,email
        ,created_date
	FROM questions Q
	INNER JOIN users U ON Q.user_id = U.user_id
	WHERE email = emailId ORDER BY title asc;
ELSE
	SELECT question_id
		,title
		,question_text
		,qtype_id
		,first_name
		,last_name
		,banner_id
		,email
        ,created_date
	FROM questions Q
	INNER JOIN users U ON Q.user_id = U.user_id
	WHERE email = emailId ORDER BY created_date asc;
END IF;
	COMMIT;
END $$
DELIMITER ;