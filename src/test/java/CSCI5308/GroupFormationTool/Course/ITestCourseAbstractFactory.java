package CSCI5308.GroupFormationTool.Course;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public interface ITestCourseAbstractFactory {

    StudentCSV createStudentCSVInstance();

    ArrayList<StudentCSV> createStudentCSVListInstance();

    ICourse createCourseInstance();

    ArrayList<ICourse> createCourseListInstance();

    IUserCourses createUserCoursesInstance();

    ArrayList<IUserCourses> createUserCoursesListInstance();

    CourseDBMock createCourseDBMock();

    StudentDBMock createStudentDBMock();

    CourseRepository createCourseRepositoryMock();

    UserCoursesRepository createUserCoursesRepositoryMock();

    StudentRepository createStudentRepositoryMock();

    StudentCSV createStudentCSVInstanceParameterized
            (String firstName, String lastName, String email, String bannerId, String password);

    HashMap<Integer, List<StudentCSV>> createStudentListHashMap();

    UserCoursesDBMock createUserCoursesDBMock();

}
