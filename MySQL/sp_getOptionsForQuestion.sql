DROP PROCEDURE IF EXISTS sp_getOptionsForQuestion $$
DELIMITER $$
CREATE DEFINER=`CSCI5308_22_DEVINT_USER`@`%` PROCEDURE `sp_getOptionsForQuestion`(IN questionId LONG)
BEGIN
	SET TRANSACTION ISOLATION LEVEL READ UNCOMMITTED;

	SELECT options_text
		,options_value
	FROM options_table
	WHERE question_id = questionId
	ORDER BY options_value;

	COMMIT;
END $$
DELIMITER ;