USE `CSCI5308_22_DEVINT`;
DROP procedure IF EXISTS `sp_createAlgo`;

DELIMITER $$
USE `CSCI5308_22_DEVINT`$$
CREATE DEFINER=`CSCI5308_22_DEVINT_USER`@`%` PROCEDURE `sp_createAlgo`(
 IN algoId varchar(40), 
 IN surveyId int, 
 IN questionId int, 
 IN similarityFactor int, 
 IN weightFactor int, 
 IN matchWords int, 
 IN lessThan int, 
 IN greaterThan int)
BEGIN
insert into algorithm(
algo_id, survey_id, question_id,weight, similarity_factor, match_words, less_than, greater_than)values 
(algoId, surveyId, questionId,weightFactor, similarityFactor, matchWords, lessThan, greaterThan);
END$$

DELIMITER ;
