package CSCI5308.GroupFormationTool.Survey;

import CSCI5308.GroupFormationTool.User.IUser;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface IResponse {

    long getUserId();

    void setUserId(long userId);

    long getSurveyId();

    void setSurveyId(long surveyId);

    long getQuestionId();

    void setQuestionId(long questionId);

    String getOptionId();

    void setOptionId(String optionId);

    List<String> getOptions();

    void setOptions(List<String> options);

    String getAnswerText();

    void setAnswerText(String answerText);

    int getQuestionType();

    void setQuestionType(int questionType);

    ArrayList<IResponse> createResponseList(Map<String, String> studentResponse);

    boolean storeResponses(ArrayList<IResponse> responseList);

    IUser getResponseUser(String emailId);

    String getQuestionTitle();

    void setQuestionTitle(String questionTitle);

    String getQuestionText();

    void setQuestionText(String questionText);

    HashMap<Long, IResponse> getUserResponses(Long userId, Long surveyId, String courseId);

}
