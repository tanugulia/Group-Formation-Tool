package CSCI5308.GroupFormationTool.Password;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class PasswordAbstractFactory implements IPasswordAbstractFactory {

    @Override
    public IForgotPasswordManager createForgotPasswordManagerInstance() {
        return new ForgotPasswordManager();
    }

    @Override
    public IForgotPasswordRepository createForgotPasswordRepositoryInstance() {
        return new ForgotPasswordRepository();
    }

    @Override
    public ITokenGenerator createTokenGeneratorInstance() {
        return new TokenGenerator();
    }

    @Override
    public IPasswordHistoryManager createPasswordHistoryManagerInstance() {
        return new PasswordHistoryManager();
    }

    @Override
    public IPasswordHistoryRepository createPasswordHistoryRepositoryInstance() {
        return new PasswordHistoryRepository();
    }

    @Override
    public IPolicy createPolicyInstance() {
        return new Policy();
    }

    @Override
    public IPolicyRepository createPolicyRepository() {
        return new PolicyRepository();
    }

    @Override
    public ArrayList<IPolicy> createPolicyListInstance() {
        return new ArrayList<IPolicy>();
    }

    @Override
    public ArrayList<String> createPasswordList() {
        return new ArrayList<String>();
    }

    @Override
    public Date createDateInstance() {
        return new Date();
    }

    @Override
    public SimpleDateFormat createSimpleDateFormatInstance() {
        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    }
}
