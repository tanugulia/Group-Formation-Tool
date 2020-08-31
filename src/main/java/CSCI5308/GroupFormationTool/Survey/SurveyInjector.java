package CSCI5308.GroupFormationTool.Survey;

public class SurveyInjector {

    private static SurveyInjector instance = null;

    private ISurveyAbstractFactory surveyAbstractFactory;

    private ISurveyRepository surveyRepository;

    private IResponseRepository responseRepository;

    private ISurveyFormulaRepository surveyFormulaRepository;

    private SurveyInjector() {
        surveyAbstractFactory = new SurveyAbstractFactory();
        surveyRepository = surveyAbstractFactory.createSurveyRepositoryInstance();
        responseRepository = surveyAbstractFactory.createResponseRepositoryInstance();
        surveyFormulaRepository = surveyAbstractFactory.createSurveyFormulaRepositoryInstance();
    }

    public static SurveyInjector instance() {
        if (instance == null) {
            instance = new SurveyInjector();
        }
        return instance;
    }

    public ISurveyAbstractFactory getSurveyAbstractFactory() {
        return surveyAbstractFactory;
    }

    public void setSurveyAbstractFactory(ISurveyAbstractFactory surveyAbstractFactory) {
        this.surveyAbstractFactory = surveyAbstractFactory;
    }

    public ISurveyRepository getSurveyRepository() {
        return surveyRepository;
    }

    public void setSurveyRepository(ISurveyRepository surveyRepository) {
        this.surveyRepository = surveyRepository;
    }

    public IResponseRepository getResponseRepository() {
        return responseRepository;
    }

    public void setResponseRepository(IResponseRepository responseRepository) {
        this.responseRepository = responseRepository;
    }

    public ISurveyFormulaRepository getSurveyFormulaRepository() {
        return surveyFormulaRepository;
    }

    public void setSurveyFormulaRepository(ISurveyFormulaRepository surveyFormulaRepository) {
        this.surveyFormulaRepository = surveyFormulaRepository;
    }

}
