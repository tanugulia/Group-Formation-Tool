package CSCI5308.GroupFormationTool.Security;

public class SecurityInjector {

    private static SecurityInjector instance = null;

    private ISecurityAbstractFactory securityAbstractFactory;

    private IPasswordEncryptor passwordEncryptor;

    private SecurityInjector() {
        securityAbstractFactory = new SecurityAbstractFactory();
        passwordEncryptor = securityAbstractFactory.createBCryptEncryptionInstance();
    }

    public static SecurityInjector instance() {
        if (instance == null) {
            instance = new SecurityInjector();
        }
        return instance;
    }

    public ISecurityAbstractFactory getSecurityAbstractFactory() {
        return securityAbstractFactory;
    }

    public IPasswordEncryptor getPasswordEncryptor() {
        return passwordEncryptor;
    }

    public void setPasswordEncryptor(IPasswordEncryptor passwordEncryptor) {
        this.passwordEncryptor = passwordEncryptor;
    }
}
