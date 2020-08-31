package CSCI5308.GroupFormationTool.Password;

public class TestPasswordInjector {

    private static TestPasswordInjector instance = null;

    private ITestPasswordAbstractFactory passwordAbstractFactory;

    private TestPasswordInjector() {
        passwordAbstractFactory = new TestPasswordAbstractFactory();
    }

    public static TestPasswordInjector instance() {

        if (instance == null) {
            return new TestPasswordInjector();
        }
        return instance;
    }

    public ITestPasswordAbstractFactory getPasswordAbstractFactory() {
        return passwordAbstractFactory;
    }
}
