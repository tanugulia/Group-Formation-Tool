package CSCI5308.GroupFormationTool.Course;

import CSCI5308.GroupFormationTool.User.ITestUserAbstractFactory;
import CSCI5308.GroupFormationTool.User.IUser;
import CSCI5308.GroupFormationTool.User.TestUserInjector;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

@SpringBootTest
public class UserCoursesRepositoryTest {

    private ITestCourseAbstractFactory courseAbstractFactoryTest = TestCourseInjector.instance().
            getCourseAbstractFactory();

    private ITestUserAbstractFactory userAbstractFactoryTest = TestUserInjector.instance().getUserAbstractFactory();

    @Test
    public void getUserRoleByEmailIdTest() {
        UserCoursesRepository userCoursesRepository = courseAbstractFactoryTest.createUserCoursesRepositoryMock();
        when(userCoursesRepository.getUserRoleByEmailId("padmeshdonthu@gmail.com"))
                .thenReturn("Guest");
        assertTrue("Guest".equals(userCoursesRepository.getUserRoleByEmailId("padmeshdonthu@gmail.com")));
        assertFalse("TA".equals(userCoursesRepository.getUserRoleByEmailId("padmeshdonthu@gmail.com")));
        when(userCoursesRepository.getUserRoleByEmailId("ta@dal.ca"))
                .thenReturn("TA");
        assertTrue("TA".equals(userCoursesRepository.getUserRoleByEmailId("ta@dal.ca")));
        assertFalse("Instructor".equals(userCoursesRepository.getUserRoleByEmailId("ta@dal.ca")));
        when(userCoursesRepository.getUserRoleByEmailId("student@dal.ca"))
                .thenReturn("Student");
        assertTrue("Student".equals(userCoursesRepository.getUserRoleByEmailId("student@dal.ca")));
        assertFalse("TA".equals(userCoursesRepository.getUserRoleByEmailId("student@dal.ca")));
        when(userCoursesRepository.getUserRoleByEmailId("instructor@dal.ca"))
                .thenReturn("Instructor");
        assertTrue("Instructor".equals(userCoursesRepository.getUserRoleByEmailId("instructor@dal.ca")));
        assertFalse("Guest".equals(userCoursesRepository.getUserRoleByEmailId("instructor@dal.ca")));
    }

    @Test
    public void getStudentCoursesTest() {
        ArrayList<IUserCourses> userCoursesList = courseAbstractFactoryTest.createUserCoursesListInstance();
        IUserCourses userCourses = courseAbstractFactoryTest.createUserCoursesInstance();
        userCourses.setBannerId("B00854462");
        userCourses.setCourseDescription("Sample description");
        userCourses.setCourseId("CSCI 5308");
        userCourses.setCourseName("Sample Text");
        userCourses.setUserRole("Student");
        userCoursesList.add(userCourses);
        assertTrue(userCoursesList.get(0).getBannerId().length() < 200);
        assertTrue(userCoursesList.get(0).getCourseDescription().length() < 100);
        assertTrue(userCoursesList.get(0).getCourseId().length() < 10);
        assertTrue(userCoursesList.get(0).getUserRole().length() < 100);
        assertTrue(userCoursesList.get(0).getCourseName().length() < 100);
        assertFalse(userCoursesList.get(0).getBannerId() == null);
        assertFalse(userCoursesList.get(0).getCourseDescription() == null);
        assertFalse(userCoursesList.get(0).getCourseName().isEmpty());
        assertFalse(userCoursesList.get(0).getUserRole().isEmpty());
        assertFalse(userCoursesList.get(0).getCourseId().isEmpty());
        assertFalse(userCoursesList.isEmpty());
    }

    @Test
    public void getTACoursesTest() {
        ArrayList<IUserCourses> userCoursesList = courseAbstractFactoryTest.createUserCoursesListInstance();
        IUserCourses userCourses = courseAbstractFactoryTest.createUserCoursesInstance();
        userCourses.setBannerId("B00854462");
        userCourses.setCourseDescription("Sample description");
        userCourses.setCourseId("CSCI 5308");
        userCourses.setCourseName("Sample Text");
        userCourses.setUserRole("TA");
        userCoursesList.add(userCourses);
        assertTrue(userCoursesList.get(0).getBannerId().length() < 200);
        assertTrue(userCoursesList.get(0).getCourseDescription().length() < 100);
        assertTrue(userCoursesList.get(0).getCourseId().length() < 10);
        assertTrue(userCoursesList.get(0).getUserRole().length() < 100);
        assertTrue(userCoursesList.get(0).getCourseName().length() < 100);
        assertFalse(userCoursesList.get(0).getBannerId() == null);
        assertFalse(userCoursesList.get(0).getCourseDescription() == null);
        assertFalse(userCoursesList.get(0).getCourseName().isEmpty());
        assertFalse(userCoursesList.get(0).getUserRole().isEmpty());
        assertFalse(userCoursesList.get(0).getCourseId().isEmpty());
        assertFalse(userCoursesList.isEmpty());
    }

    @Test
    public void getInstructorCoursesTest() {
        ArrayList<IUserCourses> userCoursesList = courseAbstractFactoryTest.createUserCoursesListInstance();
        IUserCourses userCourses = courseAbstractFactoryTest.createUserCoursesInstance();
        userCourses.setBannerId("B00854462");
        userCourses.setCourseDescription("Sample description");
        userCourses.setCourseId("CSCI 5308");
        userCourses.setCourseName("Sample Text");
        userCourses.setUserRole("Instructor");
        userCoursesList.add(userCourses);
        assertTrue(userCoursesList.get(0).getBannerId().length() < 200);
        assertTrue(userCoursesList.get(0).getCourseDescription().length() < 100);
        assertTrue(userCoursesList.get(0).getCourseId().length() < 10);
        assertTrue(userCoursesList.get(0).getUserRole().length() < 100);
        assertTrue(userCoursesList.get(0).getCourseName().length() < 100);
        assertFalse(userCoursesList.get(0).getBannerId() == null);
        assertFalse(userCoursesList.get(0).getCourseDescription() == null);
        assertFalse(userCoursesList.get(0).getCourseName().isEmpty());
        assertFalse(userCoursesList.get(0).getUserRole().isEmpty());
        assertFalse(userCoursesList.get(0).getCourseId().isEmpty());
        assertFalse(userCoursesList.isEmpty());
    }

    @Test
    public void getTAForCourseTest() {
        String courseId = "CSCI 5308";
        IUser user = userAbstractFactoryTest.createUserInstance();
        user.setBannerId("B00854462");
        user.setEmailId("padmeshdonthu@gmail.com");
        user.setFirstName("Padmesh");
        user.setLastName("Donthu");
        assertTrue(user.getBannerId().length() < 10);
        assertTrue(user.getEmailId().length() < 100);
        assertTrue(user.getFirstName().length() < 100);
        assertTrue(user.getLastName().length() < 100);
        assertFalse(user.getLastName().isEmpty());
        assertFalse(user.getFirstName().isEmpty());
        assertFalse(user.getEmailId().isEmpty());
        assertFalse(user.getBannerId().isEmpty());
    }

    @Test
    public void enrollTAForCourseUsingEmailIdTest() {
        UserCoursesRepository userCoursesRepository = courseAbstractFactoryTest.createUserCoursesRepositoryMock();
        IUser user = userAbstractFactoryTest.createUserInstance();
        when(userCoursesRepository.enrollTAForCourseUsingEmailId(user, "1")).thenReturn(false);
        assertFalse(userCoursesRepository.enrollTAForCourseUsingEmailId(user, "1"));
        when(userCoursesRepository.enrollTAForCourseUsingEmailId(user, "1"))
                .thenReturn(true);
        assertTrue(userCoursesRepository.enrollTAForCourseUsingEmailId(user, "1"));
    }

    @Test
    public void getInstructorsForCourseTest() {
        String courseId = "CSCI 5308";
        IUser user = userAbstractFactoryTest.createUserInstance();
        user.setBannerId("B00854462");
        user.setEmailId("padmeshdonthu@gmail.com");
        user.setFirstName("Padmesh");
        user.setLastName("Donthu");
        assertTrue(user.getBannerId().length() < 10);
        assertTrue(user.getEmailId().length() < 100);
        assertTrue(user.getFirstName().length() < 100);
        assertTrue(user.getLastName().length() < 100);
        assertFalse(user.getLastName().isEmpty());
        assertFalse(user.getFirstName().isEmpty());
        assertFalse(user.getEmailId().isEmpty());
        assertFalse(user.getBannerId().isEmpty());
    }
}
