package CSCI5308.GroupFormationTool.Survey;

import CSCI5308.GroupFormationTool.Question.IQuestion;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class SurveyAbstractFactory implements ISurveyAbstractFactory {

    @Override
    public ISurvey createSurveyInstance() {
        return new Survey();
    }

    @Override
    public IResponse createResponseInstance() {
        return new Response();
    }

    @Override
    public ArrayList<IResponse> createResponseListInstance() {
        return new ArrayList<IResponse>();
    }

    @Override
    public SurveyRepository createSurveyRepositoryInstance() {
        return new SurveyRepository();
    }

    @Override
    public ResponseRepository createResponseRepositoryInstance() {
        return new ResponseRepository();
    }

    @Override
    public ArrayList<IQuestion> createSurveyQuestionListInstance() {
        return new ArrayList<IQuestion>();
    }

    @Override
    public HashMap<Long, HashMap<Long, IResponse>> createAllStudentResponsesInstance() {
        return new HashMap<Long, HashMap<Long, IResponse>>();
    }

    @Override
    public HashMap<Long, IResponse> createQuestionResponseInstance() {
        return new HashMap<Long, IResponse>();
    }

    @Override
    public ISurveyFormula createSurveyFormulaInstance() {
        return new SurveyFormula();
    }

    @Override
    public ArrayList<SurveyFormula> createSurveyFormulaArrayList() {
        return new ArrayList<SurveyFormula>();
    }

    @Override
    public ISurveyFormulaRepository createSurveyFormulaRepositoryInstance() {
        return new SurveyFormulaRepository();
    }

    @Override
    public SurveyFormulaList createSurveyFormulaListInstance() {
        return new SurveyFormulaList();
    }

    @Override
    public SurveyFormula createSurveyFormulaObj() {
        return new SurveyFormula();
    }

    @Override
    public List<String> createOptionList() {
        return new ArrayList<String>();
    }
}
