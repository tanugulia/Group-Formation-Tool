CREATE DEFINER=`CSCI5308_22_DEVINT_USER`@`%` PROCEDURE `sp_getSurveyDetailsToSetAlgo`( IN courseId VARCHAR(40))
BEGIN
with surveyInfo as (
select course_id, course_survey.survey_id, question_id from course_survey join survey_questions where  
course_survey.survey_id = survey_questions.survey_id and course_id = courseId
)
select course_id, survey_id, question_id, question_text, qtype_id from questions join surveyInfo using(question_id);
END