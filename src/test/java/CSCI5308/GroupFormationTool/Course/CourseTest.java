package CSCI5308.GroupFormationTool.Course;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@SpringBootTest
public class CourseTest {

    private CourseRepository courseRepository;

    private ITestCourseAbstractFactory courseAbstractFactoryTest = TestCourseInjector.instance().
            getCourseAbstractFactory();

    @BeforeEach
    public void init() {
        courseRepository = courseAbstractFactoryTest.createCourseRepositoryMock();
        CourseInjector.instance().setCourseRepository(courseRepository);
    }

    public ICourse createDefaultCourse() {
        CourseDBMock courseDBMock = courseAbstractFactoryTest.createCourseDBMock();
        ICourse course = loadCourse(courseDBMock);
        return course;
    }

    public ICourse loadCourse(CourseDBMock courseDBMock) {
        ICourse course = courseAbstractFactoryTest.createCourseInstance();
        course = courseDBMock.loadCourses(course);
        return course;
    }

    @Test
    public void getIdTest() {
        ICourse course = createDefaultCourse();
        assertEquals("CSCI5308", course.getId());
    }

    @Test
    public void setIdTest() {
        ICourse course = courseAbstractFactoryTest.createCourseInstance();
        course.setId("CSCI5408");
        assertEquals("CSCI5408", course.getId());
    }

    @Test
    public void getNameTest() {
        ICourse course = createDefaultCourse();
        assertEquals("Adv SDC", course.getName());
    }

    @Test
    public void setNameTest() {
        ICourse course = courseAbstractFactoryTest.createCourseInstance();
        course.setName("Mobile");
        assertEquals("Mobile", course.getName());
    }

    @Test
    public void getCreditsTest() {
        ICourse course = createDefaultCourse();
        assertEquals(3, course.getCredits());
    }

    @Test
    public void setCreditsTest() {
        ICourse course = courseAbstractFactoryTest.createCourseInstance();
        course.setCredits(5);
        assertEquals(5, course.getCredits());
    }

    @Test
    public void getDescriptionTest() {
        ICourse course = createDefaultCourse();
        assertEquals("sample", course.getDescription());
    }

    @Test
    public void setDescriptionTest() {
        ICourse course = courseAbstractFactoryTest.createCourseInstance();
        course.setDescription("example");
        assertEquals("example", course.getDescription());
    }

    @Test
    void getAllCoursesTest() {
        ArrayList<ICourse> courses = courseAbstractFactoryTest.createCourseListInstance();
        ICourse course = courseAbstractFactoryTest.createCourseInstance();
        course.setName("Course 1");
        course.setCredits(3);
        course.setId("CSCI 5000");
        course.setDescription("Course 1 description");
        courses.add(course);
        course = courseAbstractFactoryTest.createCourseInstance();
        course.setName("Course 2");
        course.setCredits(4);
        course.setId("CSCI 6000");
        course.setDescription("Course 2 description");
        courses.add(course);
        when(courseRepository.getAllCourses()).thenReturn(courses);
        assertTrue(course.getAllCourses().size() == 2);
        assertFalse(course.getAllCourses().isEmpty());
        courses = courseAbstractFactoryTest.createCourseListInstance();
        when(courseRepository.getAllCourses()).thenReturn(courses);
        assertTrue(course.getAllCourses().isEmpty());
        assertFalse(course.getAllCourses().size() > 1);
    }

    @Test
    void getCourseByIdTest() {
        String courseId = "CSCI 5000";
        ICourse course = courseAbstractFactoryTest.createCourseInstance();
        course.setName("Course 1");
        course.setCredits(3);
        course.setId(courseId);
        course.setDescription("Course 1 description");
        when(courseRepository.getCourseById(courseId)).thenReturn(course);
        assertTrue(course.getCourseById(courseId).getId().equals(courseId));
        assertFalse(course.getCourseById(courseId).getCredits() == 0);
        courseId = "CSCI 9182";
        course = courseAbstractFactoryTest.createCourseInstance();
        when(courseRepository.getCourseById(courseId)).thenReturn(course);
        assertFalse(courseId.equals(course.getCourseById(courseId).getId()));
        assertTrue(course.getCourseById(courseId).getDescription() == null);
    }

    @Test
    void createCourseTest() {
        ICourse course = courseAbstractFactoryTest.createCourseInstance();
        course.setName("Course 1");
        course.setCredits(3);
        course.setId("CSCI 5200");
        course.setDescription("Course 1 description");
        when(courseRepository.createCourse(course)).thenReturn(true);
        assertTrue(course.createCourse());
        course = courseAbstractFactoryTest.createCourseInstance();
        course.setName("Course 1");
        course.setCredits(3);
        course.setId("CSCI 5200");
        course.setDescription("Course 1 description");
        when(courseRepository.createCourse(course)).thenReturn(false);
        assertFalse(course.createCourse());
    }

    @Test
    void deleteCourseTest() {
        String courseId = "CSCI 5100";
        ICourse course = courseAbstractFactoryTest.createCourseInstance();
        course.setDescription("Sample");
        course.setId(courseId);
        course.setName("New Course");
        when(courseRepository.deleteCourse(courseId)).thenReturn(course.getId().equals(courseId));
        assertTrue(course.deleteCourse(courseId));
        courseId = "CSCI 3220";
        course = courseAbstractFactoryTest.createCourseInstance();
        when(courseRepository.deleteCourse(courseId)).thenReturn(courseId.equals(course.getId()));
        assertFalse(course.deleteCourse(courseId));
    }
}
