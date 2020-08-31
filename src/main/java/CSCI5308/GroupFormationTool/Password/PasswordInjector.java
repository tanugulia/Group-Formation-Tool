package CSCI5308.GroupFormationTool.Password;

public class PasswordInjector {

    private static PasswordInjector instance = null;

    private IPasswordAbstractFactory passwordAbstractFactory;

    private ITokenGenerator tokenGenerator;

    private IForgotPasswordManager forgotPasswordManager;

    private IForgotPasswordRepository forgotPasswordRepository;

    private IPasswordHistoryManager passwordHistoryManager;

    private IPasswordHistoryRepository passwordHistoryRepository;

    private IPolicyRepository policyRepository;

    private PasswordInjector() {
        passwordAbstractFactory = new PasswordAbstractFactory();
        forgotPasswordManager = passwordAbstractFactory.createForgotPasswordManagerInstance();
        forgotPasswordRepository = passwordAbstractFactory.createForgotPasswordRepositoryInstance();
        tokenGenerator = passwordAbstractFactory.createTokenGeneratorInstance();
        passwordHistoryManager = passwordAbstractFactory.createPasswordHistoryManagerInstance();
        passwordHistoryRepository = passwordAbstractFactory.createPasswordHistoryRepositoryInstance();
        policyRepository = passwordAbstractFactory.createPolicyRepository();
    }

    public static PasswordInjector instance() {
        if (instance == null) {
            instance = new PasswordInjector();
        }
        return instance;
    }

    public IPasswordAbstractFactory getPasswordAbstractFactory() {
        return passwordAbstractFactory;
    }

    public ITokenGenerator getTokenGenerator() {
        return tokenGenerator;
    }

    public IForgotPasswordManager getForgotPasswordManager() {
        return forgotPasswordManager;
    }

    public IForgotPasswordRepository getForgotPasswordRepository() {
        return forgotPasswordRepository;
    }

    public void setForgotPasswordRepository(IForgotPasswordRepository forgotPasswordRepository) {
        this.forgotPasswordRepository = forgotPasswordRepository;
    }

    public IPasswordHistoryManager getPasswordHistoryManager() {
        return passwordHistoryManager;
    }

    public void setPasswordHistoryManager(IPasswordHistoryManager passwordHistoryManager) {
        this.passwordHistoryManager = passwordHistoryManager;
    }

    public IPasswordHistoryRepository getPasswordHistoryRepository() {
        return passwordHistoryRepository;
    }

    public void setPasswordHistoryRepository(IPasswordHistoryRepository passwordHistoryRepository) {
        this.passwordHistoryRepository = passwordHistoryRepository;
    }

    public IPolicyRepository getPolicyRepository() {
        return policyRepository;
    }

    public void setPolicyRepository(IPolicyRepository policyRepository) {
        this.policyRepository = policyRepository;
    }
}
