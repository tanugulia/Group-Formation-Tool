package CSCI5308.GroupFormationTool.Survey;

public class TestSurveyInjector {

    private static TestSurveyInjector instance = null;

    private ITestSurveyAbstractFactory surveyAbstractFactory;

    private TestSurveyInjector() {
        surveyAbstractFactory = new TestSurveyAbstractFactory();
    }

    public static TestSurveyInjector instance() {

        if (instance == null) {
            return new TestSurveyInjector();
        }
        return instance;
    }

    public ITestSurveyAbstractFactory getSurveyAbstractFactory() {
        return surveyAbstractFactory;
    }

}
