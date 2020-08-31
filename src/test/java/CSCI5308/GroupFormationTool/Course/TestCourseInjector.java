package CSCI5308.GroupFormationTool.Course;

public class TestCourseInjector {

    private static TestCourseInjector instance = null;

    private ITestCourseAbstractFactory courseAbstractFactory;

    private TestCourseInjector() {
        courseAbstractFactory = new TestCourseAbstractFactory();
    }

    public static TestCourseInjector instance() {
        if (instance == null) {
            return new TestCourseInjector();
        }
        return instance;
    }

    public ITestCourseAbstractFactory getCourseAbstractFactory() {
        return courseAbstractFactory;
    }
}
