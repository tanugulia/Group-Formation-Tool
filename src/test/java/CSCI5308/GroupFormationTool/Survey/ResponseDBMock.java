package CSCI5308.GroupFormationTool.Survey;

import CSCI5308.GroupFormationTool.User.ITestUserAbstractFactory;
import CSCI5308.GroupFormationTool.User.IUser;
import CSCI5308.GroupFormationTool.User.TestUserInjector;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ResponseDBMock implements IResponseRepository {

    private long userId;

    private long surveyId;

    private long questionId;

    private String questionTitle;

    private String questionText;

    private String optionId;

    private List<String> options;

    private String answerText;

    private int questionType;

    private ITestUserAbstractFactory testUserAbstractFactory = TestUserInjector.instance().getUserAbstractFactory();

    public ResponseDBMock() {
        userId = 1;
        surveyId = 1;
        questionId = 1;
        questionTitle = "text";
        questionText = "text";
        optionId = "32";
        options = null;
        questionType = 1;
        answerText = "3";
    }

    public IResponse loadResponse(IResponse response) {
        response.setAnswerText(answerText);
        response.setOptions(options);
        response.setSurveyId(surveyId);
        response.setQuestionId(questionId);
        response.setQuestionTitle(questionTitle);
        response.setQuestionText(questionText);
        response.setOptionId(optionId);
        response.setUserId(userId);
        response.setQuestionType(questionType);
        return response;
    }

    @Override
    public IUser getResponseUser(String emailId) {
        IUser user = testUserAbstractFactory.createUserInstance();
        user.setId(userId);
        return user;
    }

    @Override
    public long getResponseOptionId(long questionId, String optionText) {
        return 10;
    }

    @Override
    public boolean storeResponses(ArrayList<IResponse> responseList) {
        return true;
    }

    @Override
    public HashMap<Long, IResponse> getUserResponses(Long userId, Long surveyId, String courseId) {
        return null;
    }
}
