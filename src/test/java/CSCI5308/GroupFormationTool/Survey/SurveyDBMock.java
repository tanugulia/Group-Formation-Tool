package CSCI5308.GroupFormationTool.Survey;

import CSCI5308.GroupFormationTool.Common.DomainConstants;
import CSCI5308.GroupFormationTool.Question.IQuestion;
import CSCI5308.GroupFormationTool.Question.ITestQuestionAbstractFactory;
import CSCI5308.GroupFormationTool.Question.TestQuestionInjector;
import CSCI5308.GroupFormationTool.User.ITestUserAbstractFactory;
import CSCI5308.GroupFormationTool.User.IUser;
import CSCI5308.GroupFormationTool.User.TestUserInjector;

import java.sql.Date;
import java.util.ArrayList;
import java.util.HashMap;

public class SurveyDBMock implements ISurveyRepository {

    private ITestQuestionAbstractFactory questionAbstractFactoryTest = TestQuestionInjector.instance().
            getQuestionAbstractFactory();

    private ITestUserAbstractFactory userAbstractFactoryTest = TestUserInjector.instance().
            getUserAbstractFactory();

    private ArrayList<IQuestion> questionList = null;

    public SurveyDBMock() {
        questionList = questionAbstractFactoryTest.createQuestionListInstance();
        IQuestion question = questionAbstractFactoryTest.createQuestionInstance();
        IUser user = userAbstractFactoryTest.createUserInstance();
        user.setEmailId("padmeshdonthu@gmail.com");
        question = questionAbstractFactoryTest.createQuestionInstance();
        question.setCreatedDate(new Date(System.currentTimeMillis()));
        question.setId(2);
        question.setInstructor(user);
        question.setText("Sample text");
        question.setTitle("Sample title");
        question.setType(DomainConstants.numeric);
        question.setChoices(null);
        questionList.add(question);
    }

    @Override
    public boolean checkIfSurveyCreated(String courseId) {
        return true;
    }

    @Override
    public boolean checkIfSurveyPublished(String courseId) {
        return true;
    }

    @Override
    public boolean checkIfSurveyHasFormula(String courseId) {
        return true;
    }

    @Override
    public boolean publishSurvey(String courseId) {
        return true;
    }

    @Override
    public int getSurveyIdByCourseId(String courseId) {
        return 1;
    }

    @Override
    public String getSurveyId(String courseId) {
        return "1";
    }

    @Override
    public ArrayList<IQuestion> getSurveyQuestions(String surveyId) {

        return questionList;
    }

    @Override
    public boolean isSurveyPublished(String surveyId) {
        return false;
    }

    @Override
    public boolean isSurveyCompleted(String surveyId, String userId) {
        return false;
    }

    @Override
    public int createSurvey(String courseId) {
        return 0;
    }

    @Override
    public boolean addQuestionToSurvey(long questionId, long surveyId) {
        return false;
    }

    @Override
    public ArrayList<IQuestion> getQuestionsForSurvey(String courseId) {
        return questionList;
    }

    @Override
    public boolean deleteQuestionFromSurvey(long questionId, long surveyId) {
        return false;
    }

    @Override
    public ArrayList<IQuestion> getSurveyQuestionListForInstructor(String emailId, int surveyId, String questionTitle) {
        return questionList;
    }

    @Override
    public ArrayList<IQuestion> getSurveyQuestionListForTA(ArrayList<Long> instructorIds, int surveyId,
                                                           String questionTitle) {
        return questionList;
    }

    @Override
    public ArrayList<Long> getUsersWhoTookSurvey(String courseId) {
        return null;
    }

    @Override
    public HashMap<Long, HashMap<Long, IResponse>> getAllStudentResponses(String courseId) {
        return null;
    }

}
