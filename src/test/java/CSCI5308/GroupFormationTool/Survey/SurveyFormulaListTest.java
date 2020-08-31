package CSCI5308.GroupFormationTool.Survey;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

@SpringBootTest
public class SurveyFormulaListTest {

    private ITestSurveyAbstractFactory surveyAbstractFactory = TestSurveyInjector.instance().getSurveyAbstractFactory();

    private ISurveyFormulaRepository surveyFormulaRepository;

    @BeforeEach
    void init() {
        surveyFormulaRepository = surveyAbstractFactory.createSurveyFormulaRepositoryMock();
        SurveyInjector.instance().setSurveyFormulaRepository(surveyFormulaRepository);
    }

    @Test
    public void getSurveyRulesTest() {
        SurveyFormulaList surveyFormulaList = surveyAbstractFactory.createSurveyFormulaListObject();
        ArrayList<SurveyFormula> rules = surveyAbstractFactory.createSurveyFormulaListInstance();
        surveyFormulaList.setSurveyRules(rules);
        assertNotNull(surveyFormulaList.getSurveyRules());
    }

    @Test
    public void setSurveyRulesTest() {
        SurveyFormulaList surveyFormulaList = surveyAbstractFactory.createSurveyFormulaListObject();
        ArrayList<SurveyFormula> rules = surveyAbstractFactory.createSurveyFormulaListInstance();
        assertNull(surveyFormulaList.getSurveyRules());
        surveyFormulaList.setSurveyRules(rules);
        assertNotNull(surveyFormulaList.getSurveyRules());
    }

}
