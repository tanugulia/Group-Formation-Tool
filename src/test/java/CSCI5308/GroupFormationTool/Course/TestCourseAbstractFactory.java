package CSCI5308.GroupFormationTool.Course;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static org.mockito.Mockito.mock;

public class TestCourseAbstractFactory implements ITestCourseAbstractFactory {

    @Override
    public StudentCSV createStudentCSVInstance() {
        return new StudentCSV();
    }

    @Override
    public ArrayList<StudentCSV> createStudentCSVListInstance() {
        return new ArrayList<StudentCSV>();
    }

    @Override
    public ICourse createCourseInstance() {
        return new Course();
    }

    @Override
    public ArrayList<ICourse> createCourseListInstance() {
        return new ArrayList<ICourse>();
    }

    @Override
    public IUserCourses createUserCoursesInstance() {
        return new UserCourses();
    }

    @Override
    public ArrayList<IUserCourses> createUserCoursesListInstance() {
        return new ArrayList<IUserCourses>();
    }

    @Override
    public CourseDBMock createCourseDBMock() {
        return new CourseDBMock();
    }

    @Override
    public StudentDBMock createStudentDBMock() {
        return new StudentDBMock();
    }

    @Override
    public CourseRepository createCourseRepositoryMock() {
        return mock(CourseRepository.class);
    }

    @Override
    public UserCoursesRepository createUserCoursesRepositoryMock() {
        return mock(UserCoursesRepository.class);
    }

    @Override
    public StudentRepository createStudentRepositoryMock() {
        return mock(StudentRepository.class);
    }

    @Override
    public StudentCSV createStudentCSVInstanceParameterized
            (String firstName, String lastName, String email, String bannerId, String password) {
        return new StudentCSV(firstName, lastName, email, bannerId, password);
    }

    @Override
    public HashMap<Integer, List<StudentCSV>> createStudentListHashMap() {
        return new HashMap<Integer, List<StudentCSV>>();
    }

    @Override
    public UserCoursesDBMock createUserCoursesDBMock() {
        return new UserCoursesDBMock();
    }

}
