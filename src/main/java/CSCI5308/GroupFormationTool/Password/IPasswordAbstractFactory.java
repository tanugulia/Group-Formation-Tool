package CSCI5308.GroupFormationTool.Password;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public interface IPasswordAbstractFactory {

    IForgotPasswordManager createForgotPasswordManagerInstance();

    IForgotPasswordRepository createForgotPasswordRepositoryInstance();

    ITokenGenerator createTokenGeneratorInstance();

    IPasswordHistoryManager createPasswordHistoryManagerInstance();

    IPasswordHistoryRepository createPasswordHistoryRepositoryInstance();

    IPolicy createPolicyInstance();

    IPolicyRepository createPolicyRepository();

    ArrayList<IPolicy> createPolicyListInstance();

    ArrayList<String> createPasswordList();

    Date createDateInstance();

    SimpleDateFormat createSimpleDateFormatInstance();
}
