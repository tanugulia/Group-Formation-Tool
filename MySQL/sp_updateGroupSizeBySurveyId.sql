CREATE DEFINER=`CSCI5308_22_DEVINT_USER`@`%` PROCEDURE `sp_updateGroupSizeBySurveyId`(In groupSize bigint, In surveyId bigint, In courseId varchar(30))
BEGIN
update course_survey set group_size = groupSize where survey_id = surveyId and course_id = courseId;
END