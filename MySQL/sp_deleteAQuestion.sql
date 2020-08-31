DROP PROCEDURE IF EXISTS sp_deleteAQuestion $$
DELIMITER $$
CREATE DEFINER=`CSCI5308_22_DEVINT_USER`@`%` PROCEDURE `sp_deleteAQuestion`(IN questionId VARCHAR(40),
OUT deleteStatus BOOLEAN
)
BEGIN
	IF EXISTS(Select * from options_table where question_id = questionId)
    THEN
    SET deleteStatus = true;
	Delete FROM options_table where question_id = questionId;
    Delete FROM questions where question_id = questionId;
    ELSE
    Delete FROM questions where question_id = questionId;
    SET deleteStatus = true;
END IF;
	COMMIT;
END $$
DELIMITER ;