package CSCI5308.GroupFormationTool.Question;

public class QuestionInjector {

    private static QuestionInjector instance = null;

    private IQuestionAbstractFactory questionAbstractFactory;

    private IQuestionAdminRepository questionAdminRepository;

    private IQuestionManagerRepository questionManagerRepository;

    private QuestionInjector() {
        questionAbstractFactory = new QuestionAbstractFactory();
        questionManagerRepository = questionAbstractFactory.createQuestionManagerRepository();
        questionAdminRepository = questionAbstractFactory.createQuestionAdminRepository();
    }

    public static QuestionInjector instance() {
        if (instance == null) {
            instance = new QuestionInjector();
        }
        return instance;
    }

    public IQuestionAbstractFactory getQuestionAbstractFactory() {
        return questionAbstractFactory;
    }

    public void setQuestionAbstractFactory(IQuestionAbstractFactory questionAbstractFactory) {
        this.questionAbstractFactory = questionAbstractFactory;
    }

    public IQuestionAdminRepository getQuestionAdminRepository() {
        return questionAdminRepository;
    }

    public void setQuestionAdminRepository(IQuestionAdminRepository questionAdminRepository) {
        this.questionAdminRepository = questionAdminRepository;
    }

    public IQuestionManagerRepository getQuestionManagerRepository() {
        return questionManagerRepository;
    }

    public void setQuestionManagerRepository(IQuestionManagerRepository questionManagerRepository) {
        this.questionManagerRepository = questionManagerRepository;
    }

}
