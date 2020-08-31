package CSCI5308.GroupFormationTool.User;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
public class UserRepositoryTest {

    private ITestUserAbstractFactory userAbstractFactoryTest = TestUserInjector.instance().getUserAbstractFactory();

    @Test
    public void createUserTest() {
        IUser user = userAbstractFactoryTest.createUserInstance();
        user.setBannerId("B00854462");
        user.setEmailId("padmeshdonthu@gmail.com");
        user.setFirstName("Padmesh");
        user.setLastName("Donthu");
        user.setPassword("password");
        user.setConfirmPassword(user.getPassword());
        assertTrue(user.getBannerId().length() < 10);
        assertTrue(user.getEmailId().length() < 100);
        assertTrue(user.getFirstName().length() < 100);
        assertTrue(user.getLastName().length() < 100);
        assertTrue(user.getPassword().length() < 100);
        assertTrue(user.getConfirmPassword().length() < 100);
        assertFalse(user.getLastName().isEmpty());
        assertFalse(user.getFirstName().isEmpty());
        assertFalse(user.getEmailId().isEmpty());
        assertFalse(user.getBannerId().isEmpty());
        assertFalse(user.getPassword().isEmpty());
        assertFalse(user.getConfirmPassword().isEmpty());
    }

    @Test
    public void getUserByEmailIdTest() {
        String emailId = "padmeshdonthu@gmail.com";
        IUser user = userAbstractFactoryTest.createUserInstance();
        user.setBannerId("B00854462");
        user.setEmailId(emailId);
        user.setFirstName("Padmesh");
        user.setLastName("Donthu");
        user.setPassword("password");
        user.setConfirmPassword(user.getPassword());
        assertTrue(user.getBannerId().length() < 10);
        assertTrue(user.getEmailId().equals(emailId));
        assertTrue(user.getFirstName().length() < 100);
        assertTrue(user.getLastName().length() < 100);
        assertTrue(user.getPassword().length() < 100);
        assertTrue(user.getConfirmPassword().length() < 100);
        assertFalse(user.getLastName().isEmpty());
        assertFalse(user.getFirstName().isEmpty());
        assertFalse(user.getEmailId().isEmpty());
        assertFalse(user.getBannerId().isEmpty());
        assertFalse(user.getPassword().isEmpty());
        assertFalse(user.getConfirmPassword().isEmpty());
    }

    @Test
    public void getUserByBannerIdTest() {
        String bannerId = "B00854462";
        IUser user = userAbstractFactoryTest.createUserInstance();
        user.setBannerId(bannerId);
        user.setEmailId("padmeshdonthu@gmail.com");
        user.setFirstName("Padmesh");
        user.setLastName("Donthu");
        user.setPassword("password");
        user.setConfirmPassword(user.getPassword());
        assertTrue(user.getBannerId().equals(bannerId));
        assertTrue(user.getEmailId().length() < 100);
        assertTrue(user.getFirstName().length() < 100);
        assertTrue(user.getLastName().length() < 100);
        assertTrue(user.getPassword().length() < 100);
        assertTrue(user.getConfirmPassword().length() < 100);
        assertFalse(user.getLastName().isEmpty());
        assertFalse(user.getFirstName().isEmpty());
        assertFalse(user.getEmailId().isEmpty());
        assertFalse(user.getBannerId().isEmpty());
        assertFalse(user.getPassword().isEmpty());
        assertFalse(user.getConfirmPassword().isEmpty());
    }

    @Test
    public void getAdminDetailsTest() {
        String bannerId = "B00000000";
        IUser user = userAbstractFactoryTest.createUserInstance();
        user.setBannerId(bannerId);
        user.setEmailId("admin@gmail.com");
        user.setFirstName("AdminFname");
        user.setLastName("AdminLname");
        user.setPassword("password");
        user.setConfirmPassword(user.getPassword());
        assertTrue(user.getBannerId().equals(bannerId));
        assertTrue(user.getEmailId().length() < 100);
        assertTrue(user.getFirstName().length() < 100);
        assertTrue(user.getLastName().length() < 100);
        assertTrue(user.getPassword().length() < 100);
        assertTrue(user.getConfirmPassword().length() < 100);
        assertFalse(user.getLastName().isEmpty());
        assertFalse(user.getFirstName().isEmpty());
        assertFalse(user.getEmailId().isEmpty());
        assertFalse(user.getBannerId().isEmpty());
        assertFalse(user.getPassword().isEmpty());
        assertFalse(user.getConfirmPassword().isEmpty());
    }
}
