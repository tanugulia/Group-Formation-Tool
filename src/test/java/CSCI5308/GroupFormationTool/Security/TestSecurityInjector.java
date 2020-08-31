package CSCI5308.GroupFormationTool.Security;

public class TestSecurityInjector {

    private static TestSecurityInjector instance = null;

    private ITestSecurityAbstractFactory securityAbstractFactory;

    private TestSecurityInjector() {
        securityAbstractFactory = new TestSecurityAbstractFactory();
    }

    public static TestSecurityInjector instance() {

        if (instance == null) {
            return new TestSecurityInjector();
        }
        return instance;
    }

    public ITestSecurityAbstractFactory getSecurityAbstractFactory() {
        return securityAbstractFactory;
    }

}
