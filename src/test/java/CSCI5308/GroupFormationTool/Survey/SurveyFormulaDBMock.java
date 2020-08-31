package CSCI5308.GroupFormationTool.Survey;

import java.util.ArrayList;

public class SurveyFormulaDBMock implements ISurveyFormulaRepository {

    @Override
    public ArrayList<SurveyFormula> getSurveyDetailsToSetAlgorithm(String courseId) {
        ITestSurveyAbstractFactory surveyAbstractFactory = TestSurveyInjector.instance().getSurveyAbstractFactory();
        ArrayList<SurveyFormula> rules = surveyAbstractFactory.createSurveyFormulaListInstance();
        return rules;
    }

    @Override
    public String getAlgorithmIdBySurveyId(int surveyId) {
        String algorithmId = "newAlgo";
        return algorithmId;
    }

    @Override
    public void updateSurveyGroupSize(int groupSize, int surveyId, String courseId) {
        ITestSurveyAbstractFactory surveyAbstractFactory = TestSurveyInjector.instance().getSurveyAbstractFactory();
        ISurveyFormulaRepository surveyFormulaRepository = surveyAbstractFactory.createSurveyFormulaRepositoryMock();
        SurveyInjector.instance().setSurveyFormulaRepository(surveyFormulaRepository);
        surveyFormulaRepository.updateSurveyGroupSize(groupSize, surveyId, courseId);
    }

    @Override
    public Boolean createAlgorithm(SurveyFormulaList surveyFormulaList, String generatedAlgoId, int surveyId) {
        return true;
    }

    @Override
    public Boolean deleteExistingAlgorithm(String algoId) {
        return true;
    }

    @Override
    public Boolean updateAlgorithmId(String generatedAlgoId, int surveyId) {
        return true;
    }

}
