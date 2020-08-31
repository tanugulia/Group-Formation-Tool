package CSCI5308.GroupFormationTool.Course;

import CSCI5308.GroupFormationTool.Survey.ISurveyRepository;
import CSCI5308.GroupFormationTool.Survey.ITestSurveyAbstractFactory;
import CSCI5308.GroupFormationTool.Survey.SurveyInjector;
import CSCI5308.GroupFormationTool.Survey.TestSurveyInjector;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@WebMvcTest(TaController.class)
public class TAControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void courseDetailsTest() throws Exception {
        String courseId = "CSCI3901";
        ITestSurveyAbstractFactory surveyAbstractFactory = TestSurveyInjector.instance().getSurveyAbstractFactory();
        ISurveyRepository surveyRepository = surveyAbstractFactory.createSurveyRepositoryMock();
        SurveyInjector.instance().setSurveyRepository(surveyRepository);
        when(surveyRepository.getSurveyIdByCourseId(courseId)).thenReturn(1);
        when(surveyRepository.checkIfSurveyPublished(courseId)).thenReturn(true);
        this.mockMvc.perform(get("/taCourseDetails")
                .param("courseName", "SDC")
                .param("courseId", "CSCI3901"))
                .andExpect(status().isOk())
                .andExpect(view().name("course/taCourseDetails"))
                .andDo(MockMvcResultHandlers.print())
                .andReturn();
    }

}
