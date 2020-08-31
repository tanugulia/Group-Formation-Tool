package CSCI5308.GroupFormationTool.Survey;

import CSCI5308.GroupFormationTool.Common.DomainConstants;
import CSCI5308.GroupFormationTool.Course.CourseInjector;
import CSCI5308.GroupFormationTool.Course.ITestCourseAbstractFactory;
import CSCI5308.GroupFormationTool.Course.IUserCoursesRepository;
import CSCI5308.GroupFormationTool.Course.TestCourseInjector;
import CSCI5308.GroupFormationTool.Question.IQuestion;
import CSCI5308.GroupFormationTool.Question.ITestQuestionAbstractFactory;
import CSCI5308.GroupFormationTool.Question.TestQuestionInjector;
import CSCI5308.GroupFormationTool.User.ITestUserAbstractFactory;
import CSCI5308.GroupFormationTool.User.IUser;
import CSCI5308.GroupFormationTool.User.TestUserInjector;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Date;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

public class SurveyTest {

    private ITestSurveyAbstractFactory surveyAbstractFactory = TestSurveyInjector.instance().getSurveyAbstractFactory();

    private ISurveyRepository surveyRepository;

    private ISurvey survey;

    private ITestQuestionAbstractFactory questionAbstractFactoryTest = TestQuestionInjector.instance().
            getQuestionAbstractFactory();

    private ArrayList<IQuestion> questionList = null;

    private ITestCourseAbstractFactory courseAbstractFactory;

    private ITestUserAbstractFactory userAbstractFactory;

    @BeforeEach
    void init() {
        surveyRepository = surveyAbstractFactory.createSurveyRepositoryMock();
        SurveyInjector.instance().setSurveyRepository(surveyRepository);
        survey = surveyAbstractFactory.createSurveyInstance();
    }

    @Test
    void checkIfSurveyCreatedTest() {
        String courseId = "1";
        when(surveyRepository.checkIfSurveyCreated(courseId)).thenReturn(true);
        assertTrue(survey.checkIfSurveyCreated(courseId));
    }

    @Test
    void checkIfSurveyPublishedTest() {
        String courseId = "1";
        when(surveyRepository.checkIfSurveyPublished(courseId)).thenReturn(true);
        assertTrue(survey.checkIfSurveyPublished(courseId));
    }

    @Test
    void checkIfSurveyHasFormulaTest() {
        String courseId = "1";
        when(surveyRepository.checkIfSurveyHasFormula(courseId)).thenReturn(true);
        assertTrue(survey.checkIfSurveyHasFormula(courseId));
    }

    @Test
    void checkIfGroupsCanBeFormedForSurveyTest() {
        String courseId = "1";
        when(surveyRepository.checkIfSurveyCreated(courseId)).thenReturn(true);
        when(surveyRepository.checkIfSurveyPublished(courseId)).thenReturn(true);
        when(surveyRepository.checkIfSurveyHasFormula(courseId)).thenReturn(true);
        assertTrue(survey.checkIfGroupsCanBeFormedForSurvey(courseId)
                == DomainConstants.surveyGroupFormationPossible);
        when(surveyRepository.checkIfSurveyCreated(courseId)).thenReturn(false);
        assertTrue(survey.checkIfGroupsCanBeFormedForSurvey(courseId)
                == DomainConstants.surveyNotCreated);
        when(surveyRepository.checkIfSurveyCreated(courseId)).thenReturn(true);
        when(surveyRepository.checkIfSurveyPublished(courseId)).thenReturn(false);
        assertTrue(survey.checkIfGroupsCanBeFormedForSurvey(courseId)
                == DomainConstants.surveyNotPublished);
        when(surveyRepository.checkIfSurveyCreated(courseId)).thenReturn(true);
        when(surveyRepository.checkIfSurveyPublished(courseId)).thenReturn(true);
        when(surveyRepository.checkIfSurveyHasFormula(courseId)).thenReturn(false);
        assertTrue(survey.checkIfGroupsCanBeFormedForSurvey(courseId)
                == DomainConstants.surveyNotHavingAlgorithm);
    }

    @Test
    void publishSurvey() {
        String courseId = "1";
        when(surveyRepository.publishSurvey(courseId)).thenReturn(true);
        assertTrue(survey.publishSurvey(courseId));
    }

    @Test
    void getSurveyIdByCourseId() {
        String courseId = "1";
        when(surveyRepository.getSurveyIdByCourseId(courseId)).thenReturn(2);
        assertTrue(survey.getSurveyIdByCourseId(courseId) == 2);
    }

    @Test
    void getSurveyIdTest() {
        String courseId = "CSCI 6509";
        when(surveyRepository.getSurveyId(courseId)).thenReturn("2");
        assertTrue(survey.getSurveyId(courseId).equals("2"));
    }

    @Test
    void getSurveyQuestionsTest() {
        questionList = questionAbstractFactoryTest.createQuestionListInstance();
        IQuestion question = questionAbstractFactoryTest.createQuestionInstance();
        question = questionAbstractFactoryTest.createQuestionInstance();
        question.setCreatedDate(new Date(System.currentTimeMillis()));
        question.setId(2);
        question.setText("Sample text");
        question.setTitle("Sample title");
        question.setType(DomainConstants.numeric);
        question.setChoices(null);
        questionList.add(question);
        String surveyId = "CSCI 6509";
        when(surveyRepository.getSurveyQuestions(surveyId)).thenReturn(questionList);
        assertTrue(survey.getSurveyQuestions(surveyId).size() == 1);
        assertFalse(survey.getSurveyQuestions(surveyId) == null);
    }

    @Test
    void isSurveyPublishedTest() {
        String surveyId = "2";
        when(surveyRepository.isSurveyPublished(surveyId)).thenReturn(true);
        when(surveyRepository.getSurveyId("CSCI 6509")).thenReturn("2");
        assertTrue(survey.isSurveyPublished("CSCI 6509"));
    }

    @Test
    void isSurveyCompletedTest() {
        when(surveyRepository.isSurveyCompleted("2", "75")).thenReturn(false);
        when(surveyRepository.getSurveyId("CSCI 6509")).thenReturn("2");
        assertFalse(survey.isSurveyCompleted("CSCI 6509", "2"));
    }

    @Test
    void createSurveyTest() {
        String courseId = "1";
        when(surveyRepository.createSurvey(courseId)).thenReturn(1);
        assertTrue(survey.createSurvey(courseId) == 1);
        survey.setCourseId(courseId);
        survey.setDescription("This is a test survey!");
        survey.setSurveyId("1");
        assertFalse(survey.getDescription() == null);
        assertTrue(survey.getSurveyId().equals("1"));
        assertTrue(survey.getDescription().equals("This is a test survey!"));
        assertFalse(survey.getSurveyId().length() < 0);
    }

    @Test
    void addQuestionToSurveyTest() {
        when(surveyRepository.addQuestionToSurvey(32, 23)).thenReturn(true);
        assertTrue(survey.addQuestionToSurvey(32, 23));
    }

    @Test
    void getQuestionsForSurveyTest() {
        questionList = questionAbstractFactoryTest.createQuestionListInstance();
        IQuestion question = questionAbstractFactoryTest.createQuestionInstance();
        question = questionAbstractFactoryTest.createQuestionInstance();
        question.setCreatedDate(new Date(System.currentTimeMillis()));
        question.setId(2);
        question.setText("Sample text");
        question.setTitle("Sample title");
        question.setType(DomainConstants.numeric);
        question.setChoices(null);
        questionList.add(question);
        when(surveyRepository.getQuestionsForSurvey("CSCI 6509")).thenReturn(questionList);
        when(surveyRepository.getQuestionsForSurvey("2")).thenReturn(null);
        assertTrue(survey.getQuestionsForSurvey("CSCI 6509").size() == 1);
        assertFalse(survey.getQuestionsForSurvey("2") != null);
    }

    @Test
    void deleteQuestionFromSurveyTest() {
        when(surveyRepository.deleteQuestionFromSurvey(32, 2)).thenReturn(true);
        when(surveyRepository.deleteQuestionFromSurvey(2, 22)).thenReturn(false);
        assertTrue(survey.deleteQuestionFromSurvey(32, 2));
        assertFalse(survey.deleteQuestionFromSurvey(2, 22));
    }

    @Test
    void getQuestionListForSurveyTest() {
        surveyAbstractFactory = TestSurveyInjector.instance().getSurveyAbstractFactory();
        surveyRepository = surveyAbstractFactory.createSurveyRepositoryMock();
        courseAbstractFactory = TestCourseInjector.instance().getCourseAbstractFactory();
        userAbstractFactory = TestUserInjector.instance().getUserAbstractFactory();
        IUserCoursesRepository userCoursesRepository = courseAbstractFactory.createUserCoursesRepositoryMock();
        CourseInjector.instance().setUserCoursesRepository(userCoursesRepository);
        SurveyInjector.instance().setSurveyRepository(surveyRepository);
        questionAbstractFactoryTest = TestQuestionInjector.instance().getQuestionAbstractFactory();
        ArrayList<IQuestion> questions = questionAbstractFactoryTest.createQuestionListInstance();
        when(userCoursesRepository.getUserRoleByEmailId(anyString())).thenReturn(DomainConstants.instructorRole);
        when(surveyRepository.getSurveyQuestions("1")).thenReturn(null);
        when(surveyRepository.getSurveyQuestionListForTA(null, 1, "")).
                thenReturn(questions);
        when(surveyRepository.getSurveyQuestionListForInstructor
                ("haard.shah@dal.ca", 1, "title")).thenReturn(questions);
        assertTrue(survey.getSearchedQuestionListForSurvey
                ("haard.shah@dal.ca", 1, "title", "title").size() == 0);
        when(userCoursesRepository.getUserRoleByEmailId(anyString())).thenReturn(DomainConstants.tARole);
        ArrayList<Long> userIds = userAbstractFactory.createUserIdsList();
        ArrayList<IUser> users = userAbstractFactory.createUserListInstance();
        when(userCoursesRepository.getInstructorsForCourse(anyString())).thenReturn(users);
        when(surveyRepository.getSurveyQuestionListForTA(userIds, 1, "")).
                thenReturn(questions);
        when(surveyRepository.getSurveyQuestionListForInstructor
                ("haard.shah@dal.ca", 1, "title")).thenReturn(questions);
        assertTrue(survey.getSearchedQuestionListForSurvey
                ("haard.shah@dal.ca", 1, "title", "title").size() == 0);
    }

}
