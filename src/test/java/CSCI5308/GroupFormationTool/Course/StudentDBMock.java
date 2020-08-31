package CSCI5308.GroupFormationTool.Course;

import java.util.List;
import java.util.Map;

public class StudentDBMock implements IStudentRepository {

    private String firstName;

    private String lastName;

    private String email;

    private String bannerId;

    private String password;

    private ITestCourseAbstractFactory courseAbstractFactoryTest = TestCourseInjector.instance().
            getCourseAbstractFactory();

    public StudentDBMock() {
        firstName = "Tanu";
        lastName = "Gulia";
        email = "tn300318@dal.ca";
        bannerId = "B00839890";
        password = "password1234";
    }

    @Override
    public Map<Integer, List<StudentCSV>> createStudent(List<StudentCSV> student, String courseId) {

        StudentCSV newUsers = courseAbstractFactoryTest.createStudentCSVInstance();
        List<StudentCSV> oldUsersList = courseAbstractFactoryTest.createStudentCSVListInstance();
        List<StudentCSV> newUsersList = courseAbstractFactoryTest.createStudentCSVListInstance();
        newUsers.setFirstName(firstName);
        newUsers.setLastName(lastName);
        newUsers.setEmail(email);
        newUsers.setBannerId(bannerId);
        newUsers.setPassword(password);
        newUsersList.add(newUsers);
        StudentCSV oldUsers = courseAbstractFactoryTest.createStudentCSVInstance();
        oldUsers.setFirstName("Padmesh");
        oldUsers.setLastName("Donthu");
        oldUsers.setEmail("padmeshdonth@gmail.com");
        oldUsers.setBannerId("B00854462");
        oldUsers.setPassword("testsample");
        oldUsersList.add(oldUsers);
        Map<Integer, List<StudentCSV>> map = courseAbstractFactoryTest.createStudentListHashMap();
        map.put(0, newUsersList);
        map.put(1, oldUsersList);
        return map;
    }

    public IStudentCSV loadStudentCSVWithID(IStudentCSV studentCSV) {
        studentCSV.setBannerId(bannerId);
        studentCSV.setEmail(email);
        studentCSV.setFirstName(firstName);
        studentCSV.setLastName(lastName);
        studentCSV.setPassword(password);
        return studentCSV;
    }

}
