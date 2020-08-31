package CSCI5308.GroupFormationTool.Survey;

import java.util.ArrayList;

public interface ISurveyFormulaRepository {

    ArrayList<SurveyFormula> getSurveyDetailsToSetAlgorithm(String courseId);

    String getAlgorithmIdBySurveyId(int surveyId);

    void updateSurveyGroupSize(int groupSize, int surveyId, String courseId);

    Boolean createAlgorithm(SurveyFormulaList surveyFormulaList, String generatedAlgorithmId, int surveyId);

    Boolean deleteExistingAlgorithm(String algorithmId);

    Boolean updateAlgorithmId(String generatedAlgorithmId, int surveyId);

}
