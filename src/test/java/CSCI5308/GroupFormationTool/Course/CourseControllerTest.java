package CSCI5308.GroupFormationTool.Course;

import CSCI5308.GroupFormationTool.Common.DomainConstants;
import CSCI5308.GroupFormationTool.Survey.*;
import CSCI5308.GroupFormationTool.User.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@WebMvcTest(CourseController.class)
public class CourseControllerTest {

    private ITestCourseAbstractFactory courseAbstractFactoryTest = TestCourseInjector.instance().
            getCourseAbstractFactory();

    private ITestUserAbstractFactory userAbstractFactoryTest = TestUserInjector.instance().
            getUserAbstractFactory();

    private CourseRepository courseRepository;

    private UserCoursesRepository userCoursesRepository;

    private UserRepository userRepository;

    @Autowired
    private MockMvc mockMvc;

    @BeforeEach
    void init() {
        courseRepository = courseAbstractFactoryTest.createCourseRepositoryMock();
        userCoursesRepository = courseAbstractFactoryTest.createUserCoursesRepositoryMock();
        userRepository = userAbstractFactoryTest.createUserRepositoryMock();
        CourseInjector.instance().setCourseRepository(courseRepository);
        CourseInjector.instance().setUserCoursesRepository(userCoursesRepository);
        UserInjector.instance().setUserRepository(userRepository);
    }

    @Test
    void courseListTest() throws Exception {
        String emailId = "anonymousUser";
        IUser user = userAbstractFactoryTest.createUserInstance();
        user.setEmailId(emailId);
        when(userRepository.getAdminDetails()).thenReturn(user);
        when(courseRepository.getAllCourses()).thenReturn(courseAbstractFactoryTest.createCourseListInstance());
        this.mockMvc.perform(get("/courseList"))
                .andExpect(status().isOk())
                .andExpect(view().name("course/allCourses"))
                .andDo(MockMvcResultHandlers.print())
                .andReturn();
        emailId = "padmeshdonthu@gmail.com";
        user.setEmailId(emailId);
        when(userRepository.getAdminDetails()).thenReturn(user);
        when(userCoursesRepository.getStudentCourses(emailId)).
                thenReturn(courseAbstractFactoryTest.createCourseListInstance());
        when(userCoursesRepository.getTACourses(emailId)).
                thenReturn(courseAbstractFactoryTest.createCourseListInstance());
        when(userCoursesRepository.getInstructorCourses(emailId)).
                thenReturn(courseAbstractFactoryTest.createCourseListInstance());
        when(userCoursesRepository.getUserRoleByEmailId(anyString())).thenReturn(DomainConstants.guestRole);
        this.mockMvc.perform(get("/courseList"))
                .andExpect(status().isOk())
                .andExpect(view().name("course/guestCourses"))
                .andDo(MockMvcResultHandlers.print())
                .andReturn();
        when(userCoursesRepository.getUserRoleByEmailId(anyString())).thenReturn(DomainConstants.studentRole);
        this.mockMvc.perform(get("/courseList"))
                .andExpect(status().isOk())
                .andExpect(view().name("course/studentCourses"))
                .andDo(MockMvcResultHandlers.print())
                .andReturn();
        when(userCoursesRepository.getUserRoleByEmailId(anyString())).thenReturn(DomainConstants.tARole);
        this.mockMvc.perform(get("/courseList"))
                .andExpect(status().isOk())
                .andExpect(view().name("course/taCourses"))
                .andDo(MockMvcResultHandlers.print())
                .andReturn();
        when(userCoursesRepository.getUserRoleByEmailId(anyString())).thenReturn(DomainConstants.instructorRole);
        this.mockMvc.perform(get("/courseList"))
                .andExpect(status().isOk())
                .andExpect(view().name("course/instructorCourses"))
                .andDo(MockMvcResultHandlers.print())
                .andReturn();
    }

    @Test
    void enrollTATest() throws Exception {
        String courseId = "CSCI5308";
        when(userCoursesRepository.getTAForCourse(courseId)).
                thenReturn(userAbstractFactoryTest.createUserListInstance());
        this.mockMvc.perform(get("/enrollTA")
                .param("courseId", courseId))
                .andExpect(status().isOk())
                .andExpect(view().name("course/enrollTA"))
                .andDo(MockMvcResultHandlers.print())
                .andReturn();
    }

    @Test
    void addTATest() throws Exception {
        String courseId = "CSCI5308";
        when(userCoursesRepository.enrollTAForCourseUsingEmailId(userAbstractFactoryTest.createUserInstance(),
                courseId)).thenReturn(true);
        when(userCoursesRepository.getTAForCourse(courseId)).
                thenReturn(userAbstractFactoryTest.createUserListInstance());
        this.mockMvc.perform(post("/enrollTA")
                .param("courseId", "CSCI5308")
                .flashAttr("user", userAbstractFactoryTest.createUserInstance())
                .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(view().name("course/enrollTA"))
                .andDo(MockMvcResultHandlers.print())
                .andReturn();
        when(userCoursesRepository.enrollTAForCourseUsingEmailId(userAbstractFactoryTest.createUserInstance(),
                courseId)).thenReturn(false);
        when(userCoursesRepository.getTAForCourse(courseId)).
                thenReturn(userAbstractFactoryTest.createUserListInstance());
        this.mockMvc.perform(post("/enrollTA")
                .param("courseId", "CSCI5308")
                .flashAttr("user", userAbstractFactoryTest.createUserInstance())
                .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(view().name("course/enrollTA"))
                .andDo(MockMvcResultHandlers.print())
                .andReturn();
    }

    @Test
    void uploadCSVFileTest() throws Exception {
        this.mockMvc.perform(get("/uploadCSVFile")
                .param("courseId", "CSCI5308"))
                .andExpect(status().isOk())
                .andExpect(view().name("course/uploadCSVFile"))
                .andDo(MockMvcResultHandlers.print())
                .andReturn();
    }

    @Test
    void allCoursesTest() throws Exception {
        when(courseRepository.getAllCourses()).thenReturn(courseAbstractFactoryTest.createCourseListInstance());
        this.mockMvc.perform(get("/admin/allAdminCourses"))
                .andExpect(status().isOk())
                .andExpect(view().name("course/allCourses"))
                .andDo(MockMvcResultHandlers.print())
                .andReturn();
    }

    @Test
    void addCourseFormTest() throws Exception {
        this.mockMvc.perform(get("/admin/addCourse"))
                .andExpect(status().isOk())
                .andExpect(view().name("course/addCourse"))
                .andDo(MockMvcResultHandlers.print())
                .andReturn();
    }

    @Test
    void addCourseTest() throws Exception {
        when(courseRepository.createCourse(courseAbstractFactoryTest.createCourseInstance())).thenReturn(true);
        when(courseRepository.getAllCourses()).thenReturn(courseAbstractFactoryTest.createCourseListInstance());
        this.mockMvc.perform(post("/admin/addCourse")
                .flashAttr("course", courseAbstractFactoryTest.createCourseInstance())
                .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(view().name("course/allCourses"))
                .andDo(MockMvcResultHandlers.print())
                .andReturn();
        when(courseRepository.createCourse(courseAbstractFactoryTest.createCourseInstance())).thenReturn(false);
        when(courseRepository.getAllCourses()).thenReturn(courseAbstractFactoryTest.createCourseListInstance());
        this.mockMvc.perform(post("/admin/addCourse")
                .flashAttr("course", courseAbstractFactoryTest.createCourseInstance())
                .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(view().name("course/allCourses"))
                .andDo(MockMvcResultHandlers.print())
                .andReturn();
    }

    @Test
    void deleteCourseTest() throws Exception {
        String id = "CSCI5308";
        when(courseRepository.deleteCourse(id)).thenReturn(true);
        when(courseRepository.getAllCourses()).thenReturn(courseAbstractFactoryTest.createCourseListInstance());
        this.mockMvc.perform(post("/admin/deleteCourse")
                .param("id", "CSCI5308")
                .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(view().name("course/allCourses"))
                .andDo(MockMvcResultHandlers.print())
                .andReturn();
        when(courseRepository.deleteCourse(id)).thenReturn(false);
        when(courseRepository.getAllCourses()).thenReturn(courseAbstractFactoryTest.createCourseListInstance());
        this.mockMvc.perform(post("/admin/deleteCourse")
                .param("id", "CSCI5308")
                .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(view().name("course/allCourses"))
                .andDo(MockMvcResultHandlers.print())
                .andReturn();
        when(courseRepository.deleteCourse(id)).thenReturn(true);
        when(courseRepository.getAllCourses()).thenReturn(courseAbstractFactoryTest.createCourseListInstance());
        this.mockMvc.perform(get("/admin/deleteCourse")
                .param("id", "CSCI5308"))
                .andExpect(status().isOk())
                .andExpect(view().name("course/allCourses"))
                .andDo(MockMvcResultHandlers.print())
                .andReturn();
        when(courseRepository.deleteCourse(id)).thenReturn(false);
        when(courseRepository.getAllCourses()).thenReturn(courseAbstractFactoryTest.createCourseListInstance());
        this.mockMvc.perform(get("/admin/deleteCourse")
                .param("id", "CSCI5308"))
                .andExpect(status().isOk())
                .andExpect(view().name("course/allCourses"))
                .andDo(MockMvcResultHandlers.print())
                .andReturn();
    }

    @Test
    void courseDetailsTest() throws Exception {
        when(userCoursesRepository.getUserRoleByEmailId(anyString())).thenReturn(DomainConstants.instructorRole);
        this.mockMvc.perform(get("/courseDetails")
                .param("courseId", "CSCI5308")
                .param("courseName", "ASDC"))
                .andExpect(status().isOk())
                .andExpect(view().name("course/courseDetails"))
                .andDo(MockMvcResultHandlers.print())
                .andReturn();
        when(userCoursesRepository.getUserRoleByEmailId(anyString())).thenReturn(DomainConstants.tARole);
        this.mockMvc.perform(get("/courseDetails")
                .param("courseId", "CSCI5308")
                .param("courseName", "ASDC"))
                .andExpect(status().isOk())
                .andExpect(view().name("course/courseDetails"))
                .andDo(MockMvcResultHandlers.print())
                .andReturn();
        when(userCoursesRepository.getUserRoleByEmailId(anyString())).thenReturn(DomainConstants.guestRole);
        this.mockMvc.perform(get("/courseDetails")
                .param("courseId", "CSCI5308")
                .param("courseName", "ASDC"))
                .andExpect(status().isOk())
                .andExpect(view().name("course/courseDetails"))
                .andDo(MockMvcResultHandlers.print())
                .andReturn();
        String courseId = "1";
        when(userCoursesRepository.getUserRoleByEmailId(anyString())).thenReturn(DomainConstants.studentRole);
        ITestSurveyAbstractFactory surveyAbstractFactory = TestSurveyInjector.instance().getSurveyAbstractFactory();
        ISurveyRepository surveyRepository = surveyAbstractFactory.createSurveyRepositoryMock();
        SurveyInjector.instance().setSurveyRepository(surveyRepository);
        when(surveyRepository.isSurveyPublished(anyString())).thenReturn(false);
        IResponseRepository responseRepository = surveyAbstractFactory.createResponseRepositoryMock();
        IUser user = userAbstractFactoryTest.createUserInstance();
        user.setEmailId("padmeshdonthu@gmail.com");
        when(responseRepository.getResponseUser(anyString())).thenReturn(user);
        when(surveyRepository.getSurveyId(anyString())).thenReturn("1");
        this.mockMvc.perform(get("/courseDetails")
                .param("courseId", "CSCI5308")
                .param("courseName", "ASDC"))
                .andExpect(status().isOk())
                .andExpect(view().name("course/courseSurveyHome"))
                .andDo(MockMvcResultHandlers.print())
                .andReturn();
        when(surveyRepository.isSurveyCompleted("1", courseId)).thenReturn(false);
        this.mockMvc.perform(get("/courseDetails")
                .param("courseId", "CSCI5308")
                .param("courseName", "ASDC"))
                .andExpect(status().isOk())
                .andExpect(view().name("course/courseSurveyHome"))
                .andDo(MockMvcResultHandlers.print())
                .andReturn();
    }
}

