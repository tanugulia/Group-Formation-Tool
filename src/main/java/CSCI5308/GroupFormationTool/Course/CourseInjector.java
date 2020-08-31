package CSCI5308.GroupFormationTool.Course;

public class CourseInjector {

    private static CourseInjector instance = null;

    private ICourseAbstractFactory courseAbstractFactory;

    private ICourseRepository courseRepository;

    private IUserCoursesRepository userCoursesRepository;

    private IStudentRepository studentRepository;

    private CourseInjector() {
        courseAbstractFactory = new CourseAbstractFactory();
        courseRepository = courseAbstractFactory.createCourseRepository();
        userCoursesRepository = courseAbstractFactory.createUserCoursesRepository();
        studentRepository = courseAbstractFactory.createStudentRepository();
    }

    public static CourseInjector instance() {
        if (instance == null) {
            instance = new CourseInjector();
        }
        return instance;
    }

    public ICourseAbstractFactory getCourseAbstractFactory() {
        return courseAbstractFactory;
    }

    public void setCourseAbstractFactory(ICourseAbstractFactory courseAbstractFactory) {
        this.courseAbstractFactory = courseAbstractFactory;
    }

    public ICourseRepository getCourseRepository() {
        return courseRepository;
    }

    public void setCourseRepository(ICourseRepository courseRepository) {
        this.courseRepository = courseRepository;
    }

    public IUserCoursesRepository getUserCoursesRepository() {
        return userCoursesRepository;
    }

    public void setUserCoursesRepository(IUserCoursesRepository userCoursesRepository) {
        this.userCoursesRepository = userCoursesRepository;
    }

    public IStudentRepository getStudentRepository() {
        return studentRepository;
    }

    public void setStudentRepository(IStudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

}
