USE `CSCI5308_22_DEVINT`;
DROP procedure IF EXISTS `sp_updateAlgoIdBySurveyId`;

DELIMITER $$
USE `CSCI5308_22_DEVINT`$$
CREATE PROCEDURE `sp_updateAlgoIdBySurveyId` (IN algoId varchar(40), IN surveyId int)
BEGIN
update survey_details set algo_id = algoId where survey_id = surveyId;
END$$

DELIMITER ;