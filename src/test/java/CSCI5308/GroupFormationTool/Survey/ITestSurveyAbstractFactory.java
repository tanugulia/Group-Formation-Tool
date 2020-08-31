package CSCI5308.GroupFormationTool.Survey;

import CSCI5308.GroupFormationTool.Question.IQuestion;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public interface ITestSurveyAbstractFactory {

    ISurvey createSurveyInstance();

    SurveyRepository createSurveyRepositoryMock();

    IResponse createResponseInstance();

    ResponseRepository createResponseRepositoryMock();

    ArrayList<IResponse> createResponseListInstance();

    ArrayList<IQuestion> createSurveyQuestionListInstance();

    ResponseDBMock createResponseDBMockInstance();

    List<String> createOptionListInstance();

    ISurveyFormula createSurveyFormulaInstance();

    SurveyFormulaRepository createSurveyFormulaRepositoryMock();

    ArrayList<SurveyFormula> createSurveyFormulaListInstance();

    SurveyFormulaList createSurveyFormulaListObject();

    Map<String, String> createMapResponse();

}
