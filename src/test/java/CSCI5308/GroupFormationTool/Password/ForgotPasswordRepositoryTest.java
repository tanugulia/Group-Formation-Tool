package CSCI5308.GroupFormationTool.Password;

import CSCI5308.GroupFormationTool.User.ITestUserAbstractFactory;
import CSCI5308.GroupFormationTool.User.IUser;
import CSCI5308.GroupFormationTool.User.TestUserInjector;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

@SpringBootTest
public class ForgotPasswordRepositoryTest {

    private ITestPasswordAbstractFactory passwordAbstractFactoryTest = TestPasswordInjector.instance().
            getPasswordAbstractFactory();

    private ForgotPasswordRepository forgotPasswordRepository;

    private ITestUserAbstractFactory userAbstractFactoryTest = TestUserInjector.instance().getUserAbstractFactory();

    private IUser user = userAbstractFactoryTest.createUserInstance();

    @BeforeEach
    void init() {
        forgotPasswordRepository = passwordAbstractFactoryTest.createForgotPasswordRepositoryMock();
    }

    @Test
    public void addTokenTest() {
        when(forgotPasswordRepository.addToken(user, "sampleToken")).thenReturn(true);
        when(forgotPasswordRepository.addToken(null, "sampleToken")).thenReturn(false);
        when(forgotPasswordRepository.addToken(user, null)).thenReturn(false);
        when(forgotPasswordRepository.addToken(user, "")).thenReturn(false);
        assertTrue(forgotPasswordRepository.addToken(user, "sampleToken"));
        assertFalse(forgotPasswordRepository.addToken(null, "sampleToken"));
        assertFalse(forgotPasswordRepository.addToken(user, null));
        assertFalse(forgotPasswordRepository.addToken(user, ""));
    }

    @Test
    public void getTokenTest() {
        when(forgotPasswordRepository.getToken(user)).thenReturn(null);
        assertFalse(forgotPasswordRepository.getToken(user) != null);
        user.setEmailId("haard.shah@dal.ca");
        when(forgotPasswordRepository.getToken(user)).thenReturn("token");
        assertTrue(forgotPasswordRepository.getToken(user).equals("token"));
    }

    @Test
    public void updatePasswordTest() {
        when(forgotPasswordRepository.updatePassword(user, "encryptedPassword")).thenReturn(true);
        when(forgotPasswordRepository.updatePassword(user, "")).thenReturn(false);
        when(forgotPasswordRepository.updatePassword(user, null)).thenReturn(false);
        when(forgotPasswordRepository.updatePassword(null, "encryptedPassword")).thenReturn(false);
        assertFalse(forgotPasswordRepository.updatePassword(null, "encryptedPassword"));
        assertFalse(forgotPasswordRepository.updatePassword(user, null));
        assertFalse(forgotPasswordRepository.updatePassword(user, ""));
        assertTrue(forgotPasswordRepository.updatePassword(user, "encryptedPassword"));
    }

    @Test
    public void updateTokenTest() {
        when(forgotPasswordRepository.updateToken(user, "newToken")).thenReturn(true);
        when(forgotPasswordRepository.updateToken(user, "")).thenReturn(false);
        when(forgotPasswordRepository.updateToken(user, null)).thenReturn(false);
        when(forgotPasswordRepository.updateToken(null, "newToken")).thenReturn(false);
        assertFalse(forgotPasswordRepository.updateToken(null, "newToken"));
        assertFalse(forgotPasswordRepository.updateToken(user, null));
        assertFalse(forgotPasswordRepository.updateToken(user, ""));
        assertTrue(forgotPasswordRepository.updateToken(user, "newToken"));
    }

    @Test
    public void deleteTokenTest() {
        when(forgotPasswordRepository.deleteToken(user, "token")).thenReturn(true);
        when(forgotPasswordRepository.deleteToken(user, "")).thenReturn(false);
        when(forgotPasswordRepository.deleteToken(user, null)).thenReturn(false);
        when(forgotPasswordRepository.deleteToken(null, "newToken")).thenReturn(false);
        assertFalse(forgotPasswordRepository.deleteToken(null, "token"));
        assertFalse(forgotPasswordRepository.deleteToken(user, null));
        assertFalse(forgotPasswordRepository.deleteToken(user, ""));
        assertTrue(forgotPasswordRepository.deleteToken(user, "token"));
    }

    @Test
    public void getUserIdTest() {
        when(forgotPasswordRepository.getUserId(user)).thenReturn(null);
        assertFalse(forgotPasswordRepository.getUserId(user) != null);
        user.setEmailId("haard.shah@dal.ca");
        when(forgotPasswordRepository.getUserId(user)).thenReturn(user);
        assertTrue(forgotPasswordRepository.getUserId(user).getEmailId().equals("haard.shah@dal.ca"));
    }

    @Test
    public void getEmailByTokenTest() {
        when(forgotPasswordRepository.getEmailByToken(user, null)).thenReturn(null);
        when(forgotPasswordRepository.getEmailByToken(null, "token")).thenReturn(null);
        when(forgotPasswordRepository.getEmailByToken(user, "token")).thenReturn(user);
        assertFalse(forgotPasswordRepository.getEmailByToken(user, null) != null);
        assertTrue(forgotPasswordRepository.getEmailByToken(null, "token") == null);
        assertTrue(forgotPasswordRepository.getEmailByToken(user, "token") != null);
    }
}
