package CSCI5308.GroupFormationTool.Password;

import java.util.ArrayList;

import static org.mockito.Mockito.mock;

public class TestPasswordAbstractFactory implements ITestPasswordAbstractFactory {

    @Override
    public PasswordHistoryManager createPasswordHistoryManagerMock() {
        return mock(PasswordHistoryManager.class);
    }

    @Override
    public PolicyRepository createPolicyRepositoryMock() {
        return mock(PolicyRepository.class);
    }

    @Override
    public IPolicy createPolicyInstance() {
        return new Policy();
    }

    @Override
    public ForgotPasswordDBMock createForgotPasswordDBMock() {
        return new ForgotPasswordDBMock();
    }

    @Override
    public ForgotPasswordRepository createForgotPasswordRepositoryMock() {
        return mock(ForgotPasswordRepository.class);
    }

    @Override
    public Policy createPolicyMock() {
        return mock(Policy.class);
    }

    @Override
    public IForgotPasswordManager createForgotPasswordManagerInstance() {
        return new ForgotPasswordManager();
    }

    @Override
    public PasswordHistoryRepository createPasswordHistoryRepositoryMock() {
        return mock(PasswordHistoryRepository.class);
    }

    @Override
    public ArrayList<IPolicy> createPolicyListInstance() {
        return new ArrayList<IPolicy>();
    }

    @Override
    public PasswordHistoryManager createPasswordHistoryManagerInstance() {
        return new PasswordHistoryManager();
    }

    @Override
    public ArrayList<String> createListInstance() {
        return new ArrayList<String>();
    }
}
