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
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import java.util.ArrayList;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@WebMvcTest(SurveyController.class)
public class SurveyControllerTest {

    @Autowired
    private MockMvc mockMvc;

    private ITestSurveyAbstractFactory surveyAbstractFactory;

    private ITestQuestionAbstractFactory questionAbstractFactory;

    private ISurveyRepository surveyRepository;

    private ITestCourseAbstractFactory courseAbstractFactory;

    private ITestUserAbstractFactory userAbstractFactory;

    @Test
    void publishSurveyTest() throws Exception {
        String courseId = "1";
        surveyAbstractFactory = TestSurveyInjector.instance().getSurveyAbstractFactory();
        surveyRepository = surveyAbstractFactory.createSurveyRepositoryMock();
        SurveyInjector.instance().setSurveyRepository(surveyRepository);
        when(surveyRepository.getSurveyIdByCourseId(courseId)).thenReturn(1);
        when(surveyRepository.publishSurvey(anyString())).thenReturn(true);
        this.mockMvc.perform(post("/survey/publishSurvey")
                .param("courseName", "SDC")
                .param("courseId", "CSCI3901")
                .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(view().name("course/instructorCourseDetails"))
                .andDo(MockMvcResultHandlers.print())
                .andReturn();
        when(surveyRepository.publishSurvey(anyString())).thenReturn(false);
        this.mockMvc.perform(post("/survey/publishSurvey")
                .param("courseName", "SDC")
                .param("courseId", "CSCI3901")
                .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(view().name("course/instructorCourseDetails"))
                .andDo(MockMvcResultHandlers.print())
                .andReturn();
    }

    @Test
    void createSurveyFormula() throws Exception {
        String courseId = "1";
        ITestSurveyAbstractFactory surveyAbstractFactory = TestSurveyInjector.instance().getSurveyAbstractFactory();
        ISurveyFormulaRepository surveyFormulaRepository;
        surveyFormulaRepository = surveyAbstractFactory.createSurveyFormulaRepositoryMock();
        ArrayList<SurveyFormula> rules = surveyAbstractFactory.createSurveyFormulaListInstance();
        SurveyFormulaList rulesList = surveyAbstractFactory.createSurveyFormulaListObject();
        SurveyInjector.instance().setSurveyFormulaRepository(surveyFormulaRepository);
        when(surveyFormulaRepository.getSurveyDetailsToSetAlgorithm(courseId)).thenReturn(rules);
        when(surveyFormulaRepository.createAlgorithm(rulesList, "newAlgo", 1)).thenReturn(true);
        this.mockMvc.perform(get("/survey/createSurveyFormula")
                .param("courseName", "advSDC")
                .param("courseId", "1"))
                .andExpect(status().isOk())
                .andExpect(view().name("survey/createSurveyFormula"))
                .andDo(MockMvcResultHandlers.print())
                .andReturn();
    }

    @Test
    void createSurveyTest() throws Exception {
        String courseId = "1";
        surveyAbstractFactory = TestSurveyInjector.instance().getSurveyAbstractFactory();
        surveyRepository = surveyAbstractFactory.createSurveyRepositoryMock();
        questionAbstractFactory = TestQuestionInjector.instance().getQuestionAbstractFactory();
        ArrayList<IQuestion> questions = questionAbstractFactory.createQuestionListInstance();
        SurveyInjector.instance().setSurveyRepository(surveyRepository);
        when(surveyRepository.createSurvey(courseId)).thenReturn(1);
        when(surveyRepository.getQuestionsForSurvey(courseId)).thenReturn(questions);
        this.mockMvc.perform(get("/survey/createSurvey")
                .param("courseName", "Cloud")
                .param("courseId", courseId))
                .andExpect(status().isOk())
                .andExpect(view().name("survey/createSurvey"))
                .andDo(MockMvcResultHandlers.print())
                .andReturn();
    }

    @Test
    void addQuestionToSurveyTest() throws Exception {
        long questionId = 1;
        long surveyId = 1;
        String courseId = "1";
        surveyAbstractFactory = TestSurveyInjector.instance().getSurveyAbstractFactory();
        surveyRepository = surveyAbstractFactory.createSurveyRepositoryMock();
        SurveyInjector.instance().setSurveyRepository(surveyRepository);
        questionAbstractFactory = TestQuestionInjector.instance().getQuestionAbstractFactory();
        ArrayList<IQuestion> questions = questionAbstractFactory.createQuestionListInstance();
        when(surveyRepository.getQuestionsForSurvey(courseId)).thenReturn(questions);
        when(surveyRepository.addQuestionToSurvey(questionId, surveyId)).thenReturn(true);
        this.mockMvc.perform(get("/survey/createSurvey")
                .param("courseName", "Cloud")
                .param("surveyId", surveyId + "")
                .param("courseId", courseId)
                .param("surveyQuestionList", ""))
                .andExpect(status().isOk())
                .andExpect(view().name("survey/createSurvey"))
                .andDo(MockMvcResultHandlers.print())
                .andReturn();
        when(surveyRepository.addQuestionToSurvey(questionId, surveyId)).thenReturn(false);
        this.mockMvc.perform(get("/survey/createSurvey")
                .param("courseName", "Cloud")
                .param("surveyId", surveyId + "")
                .param("courseId", courseId)
                .param("surveyQuestionList", ""))
                .andExpect(status().isOk())
                .andExpect(view().name("survey/createSurvey"))
                .andDo(MockMvcResultHandlers.print())
                .andReturn();
    }

    @Test
    void deleteQuestionFromSurveyTest() throws Exception {
        long questionId = 1;
        long surveyId = 1;
        String courseId = "1";
        surveyAbstractFactory = TestSurveyInjector.instance().getSurveyAbstractFactory();
        surveyRepository = surveyAbstractFactory.createSurveyRepositoryMock();
        SurveyInjector.instance().setSurveyRepository(surveyRepository);
        questionAbstractFactory = TestQuestionInjector.instance().getQuestionAbstractFactory();
        ArrayList<IQuestion> questions = questionAbstractFactory.createQuestionListInstance();
        when(surveyRepository.getQuestionsForSurvey(courseId)).thenReturn(questions);
        when(surveyRepository.deleteQuestionFromSurvey(questionId, surveyId)).thenReturn(true);
        this.mockMvc.perform(get("/survey/createSurvey")
                .param("courseName", "Cloud")
                .param("surveyId", surveyId + "")
                .param("courseId", courseId)
                .param("surveyQuestionList", ""))
                .andExpect(status().isOk())
                .andExpect(view().name("survey/createSurvey"))
                .andDo(MockMvcResultHandlers.print())
                .andReturn();
        when(surveyRepository.deleteQuestionFromSurvey(questionId, surveyId)).thenReturn(false);
        this.mockMvc.perform(get("/survey/createSurvey")
                .param("courseName", "Cloud")
                .param("surveyId", surveyId + "")
                .param("courseId", courseId)
                .param("surveyQuestionList", ""))
                .andExpect(status().isOk())
                .andExpect(view().name("survey/createSurvey"))
                .andDo(MockMvcResultHandlers.print())
                .andReturn();
    }

    @Test
    void searchQuestionForSurvey() throws Exception {
        surveyAbstractFactory = TestSurveyInjector.instance().getSurveyAbstractFactory();
        surveyRepository = surveyAbstractFactory.createSurveyRepositoryMock();
        courseAbstractFactory = TestCourseInjector.instance().getCourseAbstractFactory();
        userAbstractFactory = TestUserInjector.instance().getUserAbstractFactory();
        IUserCoursesRepository userCoursesRepository = courseAbstractFactory.createUserCoursesRepositoryMock();
        CourseInjector.instance().setUserCoursesRepository(userCoursesRepository);
        SurveyInjector.instance().setSurveyRepository(surveyRepository);
        questionAbstractFactory = TestQuestionInjector.instance().getQuestionAbstractFactory();
        ArrayList<IQuestion> questions = questionAbstractFactory.createQuestionListInstance();
        when(userCoursesRepository.getUserRoleByEmailId(anyString())).thenReturn(DomainConstants.instructorRole);
        when(surveyRepository.getSurveyQuestions("1")).thenReturn(null);
        when(surveyRepository.getSurveyQuestionListForTA(null, 1, "")).
                thenReturn(questions);
        when(surveyRepository.getSurveyQuestionListForInstructor
                ("haard.shah@dal.ca", 1, "")).thenReturn(questions);
        this.mockMvc.perform(post("/questionManager/searchQuestionForSurvey")
                .param("courseName", "Cloud")
                .param("surveyId", "1")
                .param("courseId", "1")
                .param("questionTitle", "title")
                .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(view().name("survey/createSurvey"))
                .andDo(MockMvcResultHandlers.print())
                .andReturn();
        when(userCoursesRepository.getUserRoleByEmailId(anyString())).thenReturn(DomainConstants.tARole);
        ArrayList<Long> userIds = userAbstractFactory.createUserIdsList();
        ArrayList<IUser> users = userAbstractFactory.createUserListInstance();
        when(userCoursesRepository.getInstructorsForCourse(anyString())).thenReturn(users);
        when(surveyRepository.getSurveyQuestionListForTA(userIds, 1, "")).
                thenReturn(questions);
        when(surveyRepository.getSurveyQuestionListForInstructor
                ("haard.shah@dal.ca", 1, "title")).thenReturn(questions);
        this.mockMvc.perform(post("/questionManager/searchQuestionForSurvey")
                .param("courseName", "Cloud")
                .param("surveyId", "1")
                .param("courseId", "1")
                .param("questionTitle", "title")
                .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(view().name("survey/createSurvey"))
                .andDo(MockMvcResultHandlers.print())
                .andReturn();
    }

}
