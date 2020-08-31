USE `CSCI5308_22_DEVINT`;
DROP procedure IF EXISTS `sp_getAlgoIdBySurveyId`;

DELIMITER $$
USE `CSCI5308_22_DEVINT`$$
CREATE PROCEDURE `sp_getAlgoIdBySurveyId` (IN surveyId bigint)
BEGIN
select algo_id from survey_details where survey_id = surveyId;
END$$

DELIMITER ;