package CSCI5308.GroupFormationTool.Course;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@WebMvcTest(GuestController.class)
public class GuestControllerTest {

    private ITestCourseAbstractFactory courseAbstractFactoryTest = TestCourseInjector.instance().
            getCourseAbstractFactory();

    private CourseRepository courseRepository;

    @Autowired
    private MockMvc mockMvc;

    @BeforeEach
    void init() {
        courseRepository = courseAbstractFactoryTest.createCourseRepositoryMock();
        CourseInjector.instance().setCourseRepository(courseRepository);
    }

    @Test
    void guestCoursesTest() throws Exception {
        when(courseRepository.getAllCourses()).thenReturn(courseAbstractFactoryTest.createCourseListInstance());
        this.mockMvc.perform(get("/guest/guestCourses"))
                .andExpect(status().isOk())
                .andExpect(view().name("course/guestCourses"))
                .andDo(MockMvcResultHandlers.print())
                .andReturn();
    }
}
