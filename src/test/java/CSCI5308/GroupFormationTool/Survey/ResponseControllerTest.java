package CSCI5308.GroupFormationTool.Survey;

import CSCI5308.GroupFormationTool.Common.DomainConstants;
import CSCI5308.GroupFormationTool.Course.CourseInjector;
import CSCI5308.GroupFormationTool.Course.ITestCourseAbstractFactory;
import CSCI5308.GroupFormationTool.Course.IUserCoursesRepository;
import CSCI5308.GroupFormationTool.Course.TestCourseInjector;
import CSCI5308.GroupFormationTool.Question.ITestQuestionAbstractFactory;
import CSCI5308.GroupFormationTool.Question.TestQuestionInjector;
import CSCI5308.GroupFormationTool.User.ITestUserAbstractFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@WebMvcTest(ResponseController.class)
public class ResponseControllerTest {

    @Autowired
    private MockMvc mockMvc;

    private ITestSurveyAbstractFactory surveyAbstractFactory;

    private IResponseRepository responseRepository;

    private ITestCourseAbstractFactory courseAbstractFactory;

    private IUserCoursesRepository userCoursesRepository;

    private ISurveyRepository surveyRepository;

    private ITestQuestionAbstractFactory questionAbstractFactory;

    private ITestUserAbstractFactory userAbstractFactory;

    @BeforeEach
    void init() {
        surveyAbstractFactory = TestSurveyInjector.instance().getSurveyAbstractFactory();
        responseRepository = surveyAbstractFactory.createResponseRepositoryMock();
        SurveyInjector.instance().setResponseRepository(responseRepository);
        surveyRepository = surveyAbstractFactory.createSurveyRepositoryMock();
        SurveyInjector.instance().setSurveyRepository(surveyRepository);
    }

    @Test
    void takeSurveyTest() throws Exception {
        String courseId = "1";
        String courseName = "2";
        courseAbstractFactory = TestCourseInjector.instance().getCourseAbstractFactory();
        userCoursesRepository = courseAbstractFactory.createUserCoursesRepositoryMock();
        CourseInjector.instance().setUserCoursesRepository(userCoursesRepository);
        questionAbstractFactory = TestQuestionInjector.instance().getQuestionAbstractFactory();
        when(userCoursesRepository.getUserRoleByEmailId(anyString())).thenReturn(DomainConstants.studentRole);
        when(surveyRepository.getSurveyId(courseId)).thenReturn("1");
        when(surveyRepository.getSurveyQuestions(anyString())).thenReturn(
                questionAbstractFactory.createQuestionListInstance());
        this.mockMvc.perform(get("/courseSurveyResponse")
                .param("courseName", "SDC")
                .param("courseId", "CSCI3901")
                .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(view().name("course/courseSurveyResponse"))
                .andDo(MockMvcResultHandlers.print())
                .andReturn();
        when(userCoursesRepository.getUserRoleByEmailId(anyString())).thenReturn(DomainConstants.tARole);
        this.mockMvc.perform(get("/courseSurveyResponse")
                .param("courseName", "SDC")
                .param("courseId", "CSCI3901")
                .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:login"))
                .andDo(MockMvcResultHandlers.print())
                .andReturn();
    }

    @Test
    void viewUserResponseTest() throws Exception {
        String courseId = "1";
        String courseName = "2";
        Long surveyId = Long.valueOf("1");
        Long userId = Long.valueOf("1");
        when(responseRepository.getUserResponses(userId, surveyId, courseId)).thenReturn(null);
        this.mockMvc.perform(get("/viewResponse")
                .param("courseName", "SDC")
                .param("courseId", "CSCI3901")
                .param("surveyId", "1")
                .param("userId", "1")
                .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(view().name("survey/userResponse"))
                .andDo(MockMvcResultHandlers.print())
                .andReturn();
    }
}
