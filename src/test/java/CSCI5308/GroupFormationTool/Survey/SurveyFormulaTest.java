package CSCI5308.GroupFormationTool.Survey;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@SpringBootTest
public class SurveyFormulaTest {

    private ITestSurveyAbstractFactory surveyAbstractFactory = TestSurveyInjector.instance().getSurveyAbstractFactory();

    private ISurveyFormulaRepository surveyFormulaRepository;

    @BeforeEach
    void init() {
        surveyFormulaRepository = surveyAbstractFactory.createSurveyFormulaRepositoryMock();
        SurveyInjector.instance().setSurveyFormulaRepository(surveyFormulaRepository);
    }

    @Test
    public void getCourseIdTest() {
        ISurveyFormula surveyFormulaInst = surveyAbstractFactory.createSurveyFormulaInstance();
        surveyFormulaInst.setCourseId("2");
        assertEquals(surveyFormulaInst.getCourseId(), "2");
    }

    @Test
    public void setCourseIdTest() {
        ISurveyFormula surveyFormulaInst = surveyAbstractFactory.createSurveyFormulaInstance();
        surveyFormulaInst.setCourseId("1");
        assertEquals(surveyFormulaInst.getCourseId(), "1");
        assertNotEquals(surveyFormulaInst.getCourseId(), "2");
    }

    @Test
    public void getSurveyIdTest() {
        ISurveyFormula surveyFormulaInst = surveyAbstractFactory.createSurveyFormulaInstance();
        surveyFormulaInst.setSurveyId(1);
        assertEquals(surveyFormulaInst.getSurveyId(), 1);
    }

    @Test
    public void setSurveyIdTest() {
        ISurveyFormula surveyFormulaInst = surveyAbstractFactory.createSurveyFormulaInstance();
        surveyFormulaInst.setSurveyId(2);
        assertEquals(surveyFormulaInst.getSurveyId(), 2);
        assertNotEquals(surveyFormulaInst.getSurveyId(), 3);
    }

    @Test
    public void getQuestionIdTest() {
        ISurveyFormula surveyFormulaInst = surveyAbstractFactory.createSurveyFormulaInstance();
        surveyFormulaInst.setQuestionId(1);
        assertEquals(surveyFormulaInst.getQuestionId(), 1);
    }

    @Test
    public void setQuestionIdTest() {
        ISurveyFormula surveyFormulaInst = surveyAbstractFactory.createSurveyFormulaInstance();
        surveyFormulaInst.setQuestionId(2);
        assertEquals(surveyFormulaInst.getQuestionId(), 2);
        assertNotEquals(surveyFormulaInst.getQuestionId(), 3);
    }

    @Test
    public void getQuestionTextTest() {
        ISurveyFormula surveyFormulaInst = surveyAbstractFactory.createSurveyFormulaInstance();
        surveyFormulaInst.setQuestionText("Which course?");
        assertEquals(surveyFormulaInst.getQuestionText(), "Which course?");
    }

    @Test
    public void setQuestionTextTest() {
        ISurveyFormula surveyFormulaInst = surveyAbstractFactory.createSurveyFormulaInstance();
        surveyFormulaInst.setQuestionText("Which term?");
        assertEquals(surveyFormulaInst.getQuestionText(), "Which term?");
        assertNotEquals(surveyFormulaInst.getQuestionText(), "Which course?");
    }

    @Test
    public void getQuestionTypeTest() {
        ISurveyFormula surveyFormulaInst = surveyAbstractFactory.createSurveyFormulaInstance();
        surveyFormulaInst.setQuestionType(0);
        assertEquals(surveyFormulaInst.getQuestionType(), 0);
    }

    @Test
    public void setQuestionTypeTest() {
        ISurveyFormula surveyFormulaInst = surveyAbstractFactory.createSurveyFormulaInstance();
        surveyFormulaInst.setQuestionType(1);
        assertEquals(surveyFormulaInst.getQuestionType(), 1);
        assertNotEquals(surveyFormulaInst.getQuestionType(), 2);
    }

    @Test
    public void isCompareSimilarityTest() {
        ISurveyFormula surveyFormulaInst = surveyAbstractFactory.createSurveyFormulaInstance();
        surveyFormulaInst.setCompareSimilarity(true);
        assertTrue(surveyFormulaInst.isCompareSimilarity());
    }

    @Test
    public void setCompareSimilarityTest() {
        ISurveyFormula surveyFormulaInst = surveyAbstractFactory.createSurveyFormulaInstance();
        surveyFormulaInst.setCompareSimilarity(true);
        assertTrue(surveyFormulaInst.isCompareSimilarity());
        surveyFormulaInst.setCompareSimilarity(false);
        assertFalse(surveyFormulaInst.isCompareSimilarity());
    }

    @Test
    public void getNumericGreaterThanTest() {
        ISurveyFormula surveyFormulaInst = surveyAbstractFactory.createSurveyFormulaInstance();
        surveyFormulaInst.setNumericGreaterThan(2);
        assertEquals(surveyFormulaInst.getNumericGreaterThan(), 2);
    }

    @Test
    public void setNumericGreaterThanTest() {
        ISurveyFormula surveyFormulaInst = surveyAbstractFactory.createSurveyFormulaInstance();
        surveyFormulaInst.setNumericGreaterThan(3);
        assertEquals(surveyFormulaInst.getNumericGreaterThan(), 3);
        assertNotEquals(surveyFormulaInst.getNumericGreaterThan(), 4);
    }

    @Test
    public void getNumericLessThanTest() {
        ISurveyFormula surveyFormulaInst = surveyAbstractFactory.createSurveyFormulaInstance();
        surveyFormulaInst.setNumericLessThan(7);
        assertEquals(surveyFormulaInst.getNumericLessThan(), 7);
        assertNotEquals(surveyFormulaInst.getNumericLessThan(), 8);
    }

    @Test
    public void setNumericLessThanTest() {
        ISurveyFormula surveyFormulaInst = surveyAbstractFactory.createSurveyFormulaInstance();
        surveyFormulaInst.setNumericLessThan(8);
        assertEquals(surveyFormulaInst.getNumericLessThan(), 8);
        assertNotEquals(surveyFormulaInst.getNumericLessThan(), 9);
    }

    @Test
    public void isFreeTextSimilarityTest() {
        ISurveyFormula surveyFormulaInst = surveyAbstractFactory.createSurveyFormulaInstance();
        surveyFormulaInst.setFreeTextSimilarity(true);
        assertTrue(surveyFormulaInst.isFreeTextSimilarity());
    }

    @Test
    public void setFreeTextSimilarityTest() {
        ISurveyFormula surveyFormulaInst = surveyAbstractFactory.createSurveyFormulaInstance();
        surveyFormulaInst.setFreeTextSimilarity(true);
        assertTrue(surveyFormulaInst.isFreeTextSimilarity());
        surveyFormulaInst.setFreeTextSimilarity(false);
        assertFalse(surveyFormulaInst.isFreeTextSimilarity());
    }

    @Test
    public void isCompareDisimilarityTest() {
        ISurveyFormula surveyFormulaInst = surveyAbstractFactory.createSurveyFormulaInstance();
        surveyFormulaInst.setCompareDisimilarity(true);
        assertTrue(surveyFormulaInst.isCompareDisimilarity());
    }

    @Test
    public void setCompareDisimilarityTest() {
        ISurveyFormula surveyFormulaInst = surveyAbstractFactory.createSurveyFormulaInstance();
        surveyFormulaInst.setCompareDisimilarity(true);
        assertTrue(surveyFormulaInst.isCompareDisimilarity());
        surveyFormulaInst.setCompareDisimilarity(false);
        assertFalse(surveyFormulaInst.isCompareDisimilarity());
    }

    @Test
    public void isFreeTextDisimilarityTest() {
        ISurveyFormula surveyFormulaInst = surveyAbstractFactory.createSurveyFormulaInstance();
        surveyFormulaInst.setFreeTextDisimilarity(true);
        assertTrue(surveyFormulaInst.isFreeTextDisimilarity());
    }

    @Test
    public void setFreeTextDisimilarityTest() {
        ISurveyFormula surveyFormulaInst = surveyAbstractFactory.createSurveyFormulaInstance();
        surveyFormulaInst.setFreeTextDisimilarity(true);
        assertTrue(surveyFormulaInst.isFreeTextDisimilarity());
        surveyFormulaInst.setFreeTextDisimilarity(false);
        assertFalse(surveyFormulaInst.isFreeTextDisimilarity());
    }

    @Test
    public void getSurveyDetailsToSetAlgoTest() {
        String courseId = "1";
        ArrayList<SurveyFormula> rules = surveyAbstractFactory.createSurveyFormulaListInstance();
        when(surveyFormulaRepository.getSurveyDetailsToSetAlgorithm(courseId)).thenReturn(rules);
        assertNotNull(surveyFormulaRepository.getSurveyDetailsToSetAlgorithm(courseId));
    }

    @Test
    public void createSurveyFormulaTest() {
        int surveyId = 2;
        String generatedAlgoId = "newAlgo";
        SurveyFormulaList rules = surveyAbstractFactory.createSurveyFormulaListObject();
        when(surveyFormulaRepository.createAlgorithm(rules, generatedAlgoId, surveyId)).thenReturn(true);
        assertTrue(surveyFormulaRepository.createAlgorithm(rules, generatedAlgoId, surveyId));
    }

}
