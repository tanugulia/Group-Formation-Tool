package CSCI5308.GroupFormationTool.Password;

import CSCI5308.GroupFormationTool.Common.DomainConstants;
import CSCI5308.GroupFormationTool.Mail.ITestMailManagerAbstractFactory;
import CSCI5308.GroupFormationTool.Mail.MailInjector;
import CSCI5308.GroupFormationTool.Mail.MailManager;
import CSCI5308.GroupFormationTool.Mail.TestMailInjector;
import CSCI5308.GroupFormationTool.User.ITestUserAbstractFactory;
import CSCI5308.GroupFormationTool.User.IUser;
import CSCI5308.GroupFormationTool.User.TestUserInjector;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@SpringBootTest
public class ForgotPasswordManagerTest {

    private ForgotPasswordRepository forgotPasswordRepository;

    private IForgotPasswordManager forgotPasswordManager;

    private MailManager mailManager;

    private IPolicy policyInstance;

    private PasswordHistoryManager passwordHistoryManager;

    private PolicyRepository policyRepository;

    private ITestPasswordAbstractFactory passwordAbstractFactoryTest = TestPasswordInjector.instance().
            getPasswordAbstractFactory();

    private ITestMailManagerAbstractFactory mailManagerAbstractFactoryTest = TestMailInjector.instance().
            getMailManagerAbstractFactory();

    private ITestUserAbstractFactory userAbstractFactoryTest = TestUserInjector.instance().getUserAbstractFactory();

    @BeforeEach
    public void init() {
        forgotPasswordRepository = passwordAbstractFactoryTest.createForgotPasswordRepositoryMock();
        mailManager = mailManagerAbstractFactoryTest.createMailManagerMock();
        policyInstance = passwordAbstractFactoryTest.createPolicyInstance();
        passwordHistoryManager = passwordAbstractFactoryTest.createPasswordHistoryManagerMock();
        forgotPasswordManager = passwordAbstractFactoryTest.createForgotPasswordManagerInstance();
        policyRepository = passwordAbstractFactoryTest.createPolicyRepositoryMock();
        PasswordInjector.instance().setForgotPasswordRepository(forgotPasswordRepository);
        MailInjector.instance().setMailManager(mailManager);
        PasswordInjector.instance().setPasswordHistoryManager(passwordHistoryManager);
        PasswordInjector.instance().setPolicyRepository(policyRepository);
    }

    @Test
    void notifyUserTest() {
        IUser user = userAbstractFactoryTest.createUserInstance();
        user.setId(123);
        user.setBannerId("B00827531");
        user.setEmailId("haard.shah@dal.ca");
        user.setFirstName("haard");
        user.setLastName("shah");
        user.setPassword("pswd12345");
        String token = "sample token";
        when(forgotPasswordRepository.getUserId(user)).thenReturn(user);
        when(forgotPasswordRepository.getToken(user)).thenReturn(token);
        when(forgotPasswordRepository.updateToken(user, token)).thenReturn(true);
        when(mailManager.sendForgotPasswordMail(user, token)).thenReturn(true);
        assertNull(forgotPasswordManager.notifyUser(user));
        token = "";
        when(forgotPasswordRepository.getUserId(user)).thenReturn(user);
        when(forgotPasswordRepository.getToken(user)).thenReturn(token);
        when(forgotPasswordRepository.addToken(user, token)).thenReturn(true);
        when(mailManager.sendForgotPasswordMail(user, token)).thenReturn(true);
        assertNull(forgotPasswordManager.notifyUser(user));
        when(forgotPasswordRepository.getUserId(user)).thenReturn(null);
        String expectedMessage = DomainConstants.userDoesNotExists
                .replace("[[emailId]]", user.getEmailId());
        assertTrue(forgotPasswordManager.notifyUser(user).equals(expectedMessage));
    }

    @Test
    void updatePasswordTest() {
        IUser user = userAbstractFactoryTest.createUserInstance();
        user.setId(123);
        user.setBannerId("B00827531");
        user.setEmailId("haard.shah@dal.ca");
        user.setFirstName("haard");
        user.setLastName("shah");
        user.setPassword("pswd12345");
        user.setConfirmPassword("pswd12345");
        String token = "sample token";
        String passwordErrorMessage = "Error";
        String encryptedPassword = "encryptedpswd12345";
        ArrayList<IPolicy> policyList = passwordAbstractFactoryTest.createPolicyListInstance();
        IPolicy policy = passwordAbstractFactoryTest.createPolicyInstance();
        policy.setEnabled(1);
        policy.setId(0);
        policy.setValue("10");
        policyList.add(policy);
        when(policyRepository.passwordSPolicyCheck(user.getPassword())).
                thenReturn(passwordAbstractFactoryTest.createPolicyListInstance());
        when(forgotPasswordRepository.getEmailByToken(user, token)).thenReturn(user);
        when(passwordHistoryManager.isHistoryViolated(user, user.getPassword())).thenReturn(false);
        when(forgotPasswordRepository.updatePassword(user, encryptedPassword)).thenReturn(true);
        doNothing().when(passwordHistoryManager).addPasswordHistory(user, encryptedPassword);
        when(forgotPasswordRepository.deleteToken(user, token)).thenReturn(true);
        assertNull(forgotPasswordManager.updatePassword(user, token));
        user.setPassword("pa");
        user.setConfirmPassword(user.getPassword());
        when(policyRepository.passwordSPolicyCheck(user.getPassword())).thenReturn(policyList);
        String expectedMessage = DomainConstants.passwordMinimumLength + policy.getValue();
        String actualMessage = forgotPasswordManager.updatePassword(user, token);
        assertTrue(expectedMessage.equals(actualMessage));
        when(policyRepository.passwordSPolicyCheck(user.getPassword())).
                thenReturn(passwordAbstractFactoryTest.createPolicyListInstance());
        when(forgotPasswordRepository.getEmailByToken(user, token)).thenReturn(null);
        expectedMessage = DomainConstants.tokenExpiredMessage;
        actualMessage = forgotPasswordManager.updatePassword(user, token);
        assertTrue(expectedMessage.equals(actualMessage));
        when(policyRepository.passwordSPolicyCheck(user.getPassword())).
                thenReturn(passwordAbstractFactoryTest.createPolicyListInstance());
        when(forgotPasswordRepository.getEmailByToken(user, token)).thenReturn(user);
        when(passwordHistoryManager.getSettingValue("Password History")).thenReturn("5");
        when(passwordHistoryManager.isHistoryViolated(user, user.getPassword())).thenReturn(true);
        expectedMessage = "Your new password cannot be same as previous 5 passwords!";
        actualMessage = forgotPasswordManager.updatePassword(user, token);
        assertTrue(expectedMessage.equals(actualMessage));
        user.setConfirmPassword("somethingelse");
        when(policyRepository.passwordSPolicyCheck(user.getPassword())).
                thenReturn(passwordAbstractFactoryTest.createPolicyListInstance());
        when(passwordHistoryManager.isHistoryViolated(user, user.getPassword())).thenReturn(false);
        expectedMessage = DomainConstants.passwordsDontMatch;
        actualMessage = forgotPasswordManager.updatePassword(user, token);
        assertTrue(expectedMessage.equals(actualMessage));
    }
}
