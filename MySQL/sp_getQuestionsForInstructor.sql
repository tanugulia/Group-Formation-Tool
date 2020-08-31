DELIMITER $$

DROP PROCEDURE IF EXISTS sp_getQuestionsForInstructor $$


CREATE PROCEDURE `sp_getQuestionsForInstructor`(IN emailId VARCHAR(255))
BEGIN
	SET TRANSACTION ISOLATION LEVEL READ UNCOMMITTED;

	SELECT question_id
		,title
		,question_text
		,qtype_id
		,first_name
		,last_name
		,banner_id
		,email
	FROM questions Q
	INNER JOIN users U ON Q.user_id = U.user_id
	WHERE email = emailId;

	COMMIT;
END $$;
DELIMITER