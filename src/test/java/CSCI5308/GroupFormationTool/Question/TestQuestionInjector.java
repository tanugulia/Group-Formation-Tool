package CSCI5308.GroupFormationTool.Question;

public class TestQuestionInjector {

    private static TestQuestionInjector instance = null;

    private ITestQuestionAbstractFactory questionAbstractFactory;

    private TestQuestionInjector() {
        questionAbstractFactory = new TestQuestionAbstractFactory();
    }

    public static TestQuestionInjector instance() {

        if (instance == null) {
            return new TestQuestionInjector();
        }
        return instance;
    }

    public ITestQuestionAbstractFactory getQuestionAbstractFactory() {
        return questionAbstractFactory;
    }

}
