package CSCI5308.GroupFormationTool.Mail;

public class TestMailInjector {

    private static TestMailInjector instance = null;

    private ITestMailManagerAbstractFactory mailManagerAbstractFactory;

    private TestMailInjector() {
        mailManagerAbstractFactory = new TestMailManagerAbstractFactory();
    }

    public static TestMailInjector instance() {

        if (instance == null) {
            return new TestMailInjector();
        }
        return instance;
    }

    public ITestMailManagerAbstractFactory getMailManagerAbstractFactory() {
        return mailManagerAbstractFactory;
    }
}
