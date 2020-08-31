package CSCI5308.GroupFormationTool.Course;

import CSCI5308.GroupFormationTool.User.ITestUserAbstractFactory;
import CSCI5308.GroupFormationTool.User.IUser;
import CSCI5308.GroupFormationTool.User.TestUserInjector;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import java.util.ArrayList;

import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@WebMvcTest(AdminController.class)
public class AdminControllerTest {

    private ITestCourseAbstractFactory courseAbstractFactoryTest = TestCourseInjector.instance().
            getCourseAbstractFactory();

    private ITestUserAbstractFactory userAbstractFactoryTest = TestUserInjector.instance().getUserAbstractFactory();

    private CourseRepository courseRepository;

    private UserCoursesRepository userCoursesRepository;

    @Autowired
    private MockMvc mockMvc;

    @BeforeEach
    void init() {
        courseRepository = courseAbstractFactoryTest.createCourseRepositoryMock();
        userCoursesRepository = courseAbstractFactoryTest.createUserCoursesRepositoryMock();
        CourseInjector.instance().setCourseRepository(courseRepository);
        CourseInjector.instance().setUserCoursesRepository(userCoursesRepository);
    }

    @Test
    void adminCoursesTest() throws Exception {
        when(courseRepository.getAllCourses()).thenReturn(courseAbstractFactoryTest.createCourseListInstance());
        this.mockMvc.perform(get("/admin/allCourses"))
                .andExpect(status().isOk())
                .andExpect(view().name("course/allCourses"))
                .andDo(MockMvcResultHandlers.print())
                .andReturn();
    }

    @Test
    void assignInstructorTest() throws Exception {
        String courseId = "CSCI5308";
        when(courseRepository.getCourseById(courseId)).thenReturn(courseAbstractFactoryTest.createCourseInstance());
        when(userCoursesRepository.usersCurrentlyNotInstructorsForCourse(courseId)).
                thenReturn(userAbstractFactoryTest.createUserListInstance());
        when(userCoursesRepository.getInstructorsForCourse(courseId)).
                thenReturn(userAbstractFactoryTest.createUserListInstance());
        this.mockMvc.perform(get("/admin/assignInstructor")
                .param("courseId", courseId))
                .andExpect(status().isOk())
                .andExpect(view().name("course/assignInstructor"))
                .andDo(MockMvcResultHandlers.print())
                .andReturn();
    }

    @Test
    void assignInstructorToCourseTest() throws Exception {
        long instructorId = 3;
        String courseId = "CSCI5308";
        ICourse course = courseAbstractFactoryTest.createCourseInstance();
        ArrayList<IUser> userList = userAbstractFactoryTest.createUserListInstance();
        IUser user = userAbstractFactoryTest.createUserInstance();
        user.setFirstName("Padmesh");
        user.setId(instructorId);
        user.setLastName("Donthu");
        userList.add(user);
        course.setName("ASDC");
        course.setId(courseId);
        when(courseRepository.getCourseById(courseId)).thenReturn(course);
        when(userCoursesRepository.addInstructorsToCourse(instructorId, courseId)).thenReturn(true);
        when(userCoursesRepository.getInstructorsForCourse(courseId)).thenReturn(userList);
        this.mockMvc.perform(post("/admin/assignInstructor")
                .param("instructor", String.valueOf(instructorId))
                .param("id", courseId)
                .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/assignInstructor?courseId=" + courseId))
                .andDo(MockMvcResultHandlers.print())
                .andReturn();
        when(userCoursesRepository.addInstructorsToCourse(instructorId, courseId)).thenReturn(false);
        this.mockMvc.perform(post("/admin/assignInstructor")
                .param("instructor", String.valueOf(instructorId))
                .param("id", courseId)
                .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/assignInstructor?courseId=" + courseId))
                .andDo(MockMvcResultHandlers.print())
                .andReturn();
    }
}
