package CSCI5308.GroupFormationTool.Survey;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

public class ResponseTest {

    private IResponseRepository responseRepository;

    private ITestSurveyAbstractFactory surveyAbstractFactory = TestSurveyInjector.instance().
            getSurveyAbstractFactory();

    @BeforeEach
    public void init() {
        responseRepository = surveyAbstractFactory.createResponseRepositoryMock();
        SurveyInjector.instance().setResponseRepository(responseRepository);
    }

    public IResponse createDefaultResponse() {
        ResponseDBMock responseDBMock = surveyAbstractFactory.createResponseDBMockInstance();
        IResponse response = loadResponse(responseDBMock);
        return response;
    }

    public IResponse loadResponse(ResponseDBMock responseDBMock) {
        IResponse response = surveyAbstractFactory.createResponseInstance();
        response = responseDBMock.loadResponse(response);
        return response;
    }

    @Test
    void getUserIdTest() {
        IResponse response = createDefaultResponse();
        assertTrue(response.getUserId() == 1);
    }

    @Test
    void setUserIdTest() {
        IResponse response = surveyAbstractFactory.createResponseInstance();
        response.setUserId(1);
        assertTrue(response.getUserId() == 1);
    }

    @Test
    void getSurveyIdTest() {
        IResponse response = createDefaultResponse();
        assertTrue(response.getSurveyId() == 1);
    }

    @Test
    void setSurveyIdTest() {
        IResponse response = surveyAbstractFactory.createResponseInstance();
        response.setSurveyId(1);
        assertTrue(response.getSurveyId() == 1);
    }

    @Test
    void getQuestionIdTest() {
        IResponse response = createDefaultResponse();
        assertTrue(response.getQuestionId() == 1);
    }

    @Test
    void setQuestionIdTest() {
        IResponse response = surveyAbstractFactory.createResponseInstance();
        response.setQuestionId(1);
        assertTrue(response.getQuestionId() == 1);
    }

    @Test
    void getOptionIdTest() {
        IResponse response = createDefaultResponse();
        assertTrue(response.getOptionId().equals("32"));
    }

    @Test
    void setOptionIdTest() {
        IResponse response = surveyAbstractFactory.createResponseInstance();
        response.setOptionId("1");
        assertTrue(response.getOptionId().equals("1"));
    }

    @Test
    void getOptionsTest() {
        IResponse response = createDefaultResponse();
        assertTrue(response.getOptions() == null);
    }

    @Test
    void setOptionsTest() {
        IResponse response = surveyAbstractFactory.createResponseInstance();
        List<String> options = surveyAbstractFactory.createOptionListInstance();
        options.add("32");
        response.setOptions(options);
        assertTrue(response.getOptions().get(0).equals("32"));
    }

    @Test
    void getAnswerTextTest() {
        IResponse response = createDefaultResponse();
        assertTrue(response.getAnswerText().equals("3"));
    }

    @Test
    void setAnswerTextTest() {
        IResponse response = surveyAbstractFactory.createResponseInstance();
        response.setAnswerText("3");
        assertTrue(response.getAnswerText().equals("3"));
    }

    @Test
    void getQuestionTypeTest() {
        IResponse response = createDefaultResponse();
        assertTrue(response.getQuestionType() == 1);
    }

    @Test
    void setQuestionTypeTest() {
        IResponse response = surveyAbstractFactory.createResponseInstance();
        response.setQuestionType(1);
        assertTrue(response.getQuestionType() == 1);
    }

    @Test
    void storeResponsesTest() {
        when(responseRepository.storeResponses(null)).thenReturn(true);
        IResponse response = surveyAbstractFactory.createResponseInstance();
        assertTrue(response.storeResponses(null));
    }

    @Test
    void getResponseUserTest() {
        when(responseRepository.getResponseUser("pa@gmail.com")).thenReturn(null);
        IResponse response = surveyAbstractFactory.createResponseInstance();
        assertTrue(response.getResponseUser("pa@gmail.com") == null);
    }

    @Test
    void getQuestionTitleTest() {
        IResponse response = createDefaultResponse();
        assertTrue(response.getQuestionTitle().equals("text"));
    }

    @Test
    void setQuestionTitleTest() {
        IResponse response = surveyAbstractFactory.createResponseInstance();
        response.setQuestionTitle("3");
        assertTrue(response.getQuestionTitle().equals("3"));
    }

    @Test
    void getQuestionTextTest() {
        IResponse response = createDefaultResponse();
        assertTrue(response.getQuestionText().equals("text"));
    }

    @Test
    void setQuestionTextTest() {
        IResponse response = surveyAbstractFactory.createResponseInstance();
        response.setQuestionText("3");
        assertTrue(response.getQuestionText().equals("3"));
    }

    @Test
    void getUserResponsesTest() {
        IResponse response = surveyAbstractFactory.createResponseInstance();
        when(responseRepository.getUserResponses((long) 1, (long) 1, "1")).thenReturn(null);
        assertTrue(response.getUserResponses((long) 1, (long) 1, "1") == null);
    }
}
