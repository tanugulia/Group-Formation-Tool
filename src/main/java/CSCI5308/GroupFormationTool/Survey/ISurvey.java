package CSCI5308.GroupFormationTool.Survey;

import CSCI5308.GroupFormationTool.Question.IQuestion;

import java.util.ArrayList;
import java.util.HashMap;

public interface ISurvey {

    boolean checkIfSurveyCreated(String courseId);

    boolean checkIfSurveyPublished(String courseId);

    boolean checkIfSurveyHasFormula(String courseId);

    int checkIfGroupsCanBeFormedForSurvey(String courseId);

    boolean publishSurvey(String courseId);

    int getSurveyIdByCourseId(String courseId);

    String getSurveyId(String courseId);

    ArrayList<IQuestion> getSurveyQuestions(String surveyId);

    boolean isSurveyPublished(String courseId);

    boolean isSurveyCompleted(String courseId, String userId);

    String getSurveyId();

    void setSurveyId(String surveyId);

    String getDescription();

    void setDescription(String description);

    String courseId();

    void setCourseId(String courseId);

    int createSurvey(String courseId);

    boolean addQuestionToSurvey(long questionId, long surveyId);

    ArrayList<IQuestion> getQuestionsForSurvey(String courseId);

    boolean deleteQuestionFromSurvey(long questionId, long surveyId);

    ArrayList<IQuestion> getSearchedQuestionListForSurvey(
            String emailId, int surveyId, String courseId, String questionTitle);

    ArrayList<Long> getUsersWhoTookSurvey(String courseId);

    HashMap<Long, HashMap<Long, IResponse>> getAllStudentResponses(String courseId);

}
