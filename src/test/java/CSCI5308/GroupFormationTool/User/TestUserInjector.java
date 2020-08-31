package CSCI5308.GroupFormationTool.User;

public class TestUserInjector {

    private static TestUserInjector instance = null;

    private ITestUserAbstractFactory userAbstractFactory;

    private TestUserInjector() {
        userAbstractFactory = new TestUserAbstractFactory();
    }

    public static TestUserInjector instance() {

        if (instance == null) {
            return new TestUserInjector();
        }
        return instance;
    }

    public ITestUserAbstractFactory getUserAbstractFactory() {
        return userAbstractFactory;
    }

}
