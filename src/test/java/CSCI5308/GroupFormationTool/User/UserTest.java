package CSCI5308.GroupFormationTool.User;

import CSCI5308.GroupFormationTool.Common.DomainConstants;
import CSCI5308.GroupFormationTool.Password.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@SpringBootTest
public class UserTest {

    IUser userInstance;

    UserRepository userRepository;

    PasswordHistoryManager passwordHistoryManager;

    PolicyRepository policyRepository;

    private ITestUserAbstractFactory userAbstractFactoryTest = TestUserInjector.instance().getUserAbstractFactory();

    private ITestPasswordAbstractFactory passwordAbstractFactoryTest = TestPasswordInjector.instance().
            getPasswordAbstractFactory();

    @BeforeEach
    public void init() {
        userInstance = userAbstractFactoryTest.createUserInstance();
        userRepository = userAbstractFactoryTest.createUserRepositoryMock();
        UserInjector.instance().setUserRepository(userRepository);
        policyRepository = passwordAbstractFactoryTest.createPolicyRepositoryMock();
        passwordHistoryManager = passwordAbstractFactoryTest.createPasswordHistoryManagerMock();
        PasswordInjector.instance().setPasswordHistoryManager(passwordHistoryManager);
        PasswordInjector.instance().setPolicyRepository(policyRepository);
    }

    private IUser createDefaultUser() {
        UserDBMock userDbMock = userAbstractFactoryTest.createUserDBMock();
        IUser user = loadUser(userDbMock);
        return user;
    }

    private IUser loadUser(UserDBMock userDBMock) {
        IUser user = userAbstractFactoryTest.createUserInstance();
        user = userDBMock.loadUserWithID(user);
        return user;
    }

    @Test
    public void getIdTest() {
        IUser user = createDefaultUser();
        assertEquals(1, user.getId());
    }

    @Test
    public void setIdTest() {
        IUser user = userAbstractFactoryTest.createUserInstance();
        user.setId(2);
        assertEquals(2, user.getId());
    }

    @Test
    public void getFirstNameTest() {
        IUser user = createDefaultUser();
        assertEquals("Test", user.getFirstName());
    }

    @Test
    public void setFirstNameTest() {
        IUser user = userAbstractFactoryTest.createUserInstance();
        user.setFirstName("Padmesh");
        assertEquals("Padmesh", user.getFirstName());
    }

    @Test
    public void getLastNameTest() {
        IUser user = createDefaultUser();
        assertEquals("User", user.getLastName());
    }

    @Test
    public void setLastNameTest() {
        IUser user = userAbstractFactoryTest.createUserInstance();
        user.setLastName("Donthu");
        assertEquals("Donthu", user.getLastName());
    }

    @Test
    public void getBannerIdTest() {
        IUser user = createDefaultUser();
        assertEquals("B00854462", user.getBannerId());
    }

    @Test
    public void setBannerIdTest() {
        IUser user = userAbstractFactoryTest.createUserInstance();
        user.setBannerId("B0000000");
        assertEquals("B0000000", user.getBannerId());
    }

    @Test
    public void getEmailIdTest() {
        IUser user = createDefaultUser();
        assertEquals("padmeshdonthu@gmail.com", user.getEmailId());
    }

    @Test
    public void setEmailIdTest() {
        IUser user = userAbstractFactoryTest.createUserInstance();
        user.setBannerId("padmeshdonthu@gmail.com");
        assertEquals("padmeshdonthu@gmail.com", user.getBannerId());
    }

    @Test
    public void getPasswordTest() {
        IUser user = createDefaultUser();
        assertEquals("password", user.getPassword());
    }

    @Test
    public void setPasswordTest() {
        IUser user = userAbstractFactoryTest.createUserInstance();
        user.setPassword("password");
        assertEquals("password", user.getPassword());
    }

    @Test
    public void getConfirmPasswordTest() {
        IUser user = createDefaultUser();
        assertEquals("password", user.getConfirmPassword());
    }

    @Test
    public void setConfirmPasswordTest() {
        IUser user = userAbstractFactoryTest.createUserInstance();
        user.setConfirmPassword("password");
        assertEquals("password", user.getConfirmPassword());
    }

    @Test
    void createUserTest() {
        IUser user = userAbstractFactoryTest.createUserInstance();
        user.setBannerId("B00854462");
        user.setEmailId("padmeshdonthu@gmail.com");
        user.setFirstName("Padmesh");
        user.setLastName("Donthu");
        user.setPassword("Padmesh1$");
        user.setConfirmPassword("Padmesh1$");
        String encryptedPassword = "encryptedPadmesh1$";
        ArrayList<IPolicy> policyList = passwordAbstractFactoryTest.createPolicyListInstance();
        IPolicy policy = passwordAbstractFactoryTest.createPolicyInstance();
        policy.setEnabled(1);
        policy.setId(0);
        policy.setValue("10");
        policyList.add(policy);
        when(policyRepository.passwordSPolicyCheck(user.getPassword())).
                thenReturn(passwordAbstractFactoryTest.createPolicyListInstance());
        when(userRepository.getUserByEmailId(user)).thenReturn(null);
        when(userRepository.createUser(user)).thenReturn(true);
        when(userRepository.getUserIdByEmailId(user)).thenReturn(user);
        doNothing().when(passwordHistoryManager).addPasswordHistory(user, encryptedPassword);
        assertNull(userInstance.createUser(user));
        user.setPassword("pa");
        user.setConfirmPassword("pa");
        String passwordErrorMessage = DomainConstants.passwordMinimumLength + policy.getValue();
        when(policyRepository.passwordSPolicyCheck(user.getPassword())).
                thenReturn(policyList);
        assertTrue(userInstance.createUser(user).equals(passwordErrorMessage));
        user.setPassword("Padmesh1$");
        user.setConfirmPassword("Padmesh1");
        passwordErrorMessage = DomainConstants.passwordsDontMatch;
        assertTrue(userInstance.createUser(user).equals(passwordErrorMessage));
        user.setConfirmPassword("Padmesh1$");
        when(policyRepository.passwordSPolicyCheck(user.getPassword())).
                thenReturn(passwordAbstractFactoryTest.createPolicyListInstance());
        when(userRepository.getUserByEmailId(user)).thenReturn(user);
        String userAlreadyExistsErrorMessage = DomainConstants.userAlreadyExists
                .replace("[[emailId]]", user.getEmailId());
        user.setPassword("Padmesh1$");
        user.setConfirmPassword("Padmesh1$");
        assertTrue(userInstance.createUser(user).equals(userAlreadyExistsErrorMessage));
        user.setEmailId("");
        user.setPassword("Padmesh1$");
        user.setConfirmPassword("Padmesh1$");
        when(userRepository.getUserByEmailId(user)).thenReturn(null);
        assertTrue(userInstance.createUser(user).equals(DomainConstants.signupInvalidDetails));
    }

    @Test
    void checkCurrentUserIsAdminTest() {
        String emailId = "padmeshd@gmail.com";
        IUser admin = userAbstractFactoryTest.createUserInstance();
        admin.setBannerId("B00854462");
        admin.setEmailId("padmeshdonthu@gmail.com");
        admin.setFirstName("Padmesh");
        admin.setLastName("Donthu");
        when(userRepository.getAdminDetails()).thenReturn(admin);
        assertFalse(userInstance.checkCurrentUserIsAdmin(emailId));
        emailId = "padmeshdonthu@gmail.com";
        when(userRepository.getAdminDetails()).thenReturn(admin);
        assertTrue(userInstance.checkCurrentUserIsAdmin(emailId));
    }
}
