package CSCI5308.GroupFormationTool.Course;

import CSCI5308.GroupFormationTool.User.ITestUserAbstractFactory;
import CSCI5308.GroupFormationTool.User.IUser;
import CSCI5308.GroupFormationTool.User.TestUserInjector;

import java.util.ArrayList;

public class UserCoursesDBMock implements IUserCoursesRepository {

    private String courseId;

    private String bannerId;

    private String courseName;

    private String courseDescription;

    private String userRole;

    private String id;

    private String name;

    private int credits;

    private String description;

    private ITestCourseAbstractFactory courseAbstractFactoryTest = TestCourseInjector.instance().
            getCourseAbstractFactory();

    private ITestUserAbstractFactory userAbstractFactoryTest = TestUserInjector.instance().getUserAbstractFactory();

    public UserCoursesDBMock() {
        courseId = "CSCI5308";
        courseName = "Adv SDC";
        courseDescription = "sample";
        bannerId = "B00854462";
        userRole = "student";
        id = "CSCI5308";
        name = "Adv SDC";
        credits = 3;
        description = "sample";
    }

    public IUserCourses loadCourses(IUserCourses userCourses) {
        userCourses.setBannerId(bannerId);
        userCourses.setCourseDescription(courseDescription);
        userCourses.setCourseId(courseId);
        userCourses.setCourseName(courseName);
        userCourses.setUserRole(userRole);
        return userCourses;
    }

    @Override
    public String getUserRoleByEmailId(String emailId) {
        if (emailId.equals("student@dal.ca")) {
            return "Student";
        } else if (emailId.equals("ta@dal.ca")) {
            return "TA";
        } else if (emailId.equals("instructor@dal.ca")) {
            return "Instructor";
        }
        return "Guest";
    }

    @Override
    public ArrayList<ICourse> getStudentCourses(String emailId) {
        ArrayList<ICourse> studentCourseList = courseAbstractFactoryTest.createCourseListInstance();
        ICourse course = courseAbstractFactoryTest.createCourseInstance();
        course.setCredits(credits);
        course.setDescription(description);
        course.setName(name);
        course.setId(id);
        studentCourseList.add(course);
        return studentCourseList;
    }

    @Override
    public ArrayList<ICourse> getTACourses(String emailId) {
        ArrayList<ICourse> taCourseList = courseAbstractFactoryTest.createCourseListInstance();
        ICourse course = courseAbstractFactoryTest.createCourseInstance();
        course.setCredits(credits);
        course.setDescription(description);
        course.setName(name);
        course.setId(id);
        taCourseList.add(course);
        return taCourseList;
    }

    public ArrayList<IUser> usersCurrentlyNotInstructorsForCourse(String courseId) {
        ArrayList<IUser> usersCurrentlyNotInstructorsForCourse = userAbstractFactoryTest.createUserListInstance();
        IUser userCurrentlyNotInstructorsForCourse = userAbstractFactoryTest.createUserInstance();
        userCurrentlyNotInstructorsForCourse.setBannerId(bannerId);
        userCurrentlyNotInstructorsForCourse.setEmailId("stu@gmail.com");
        userCurrentlyNotInstructorsForCourse.setFirstName("John");
        userCurrentlyNotInstructorsForCourse.setLastName("sam");
        usersCurrentlyNotInstructorsForCourse.add(userCurrentlyNotInstructorsForCourse);
        return usersCurrentlyNotInstructorsForCourse;
    }

    @Override
    public boolean addInstructorsToCourse(Long instructor, String courseId) {
        IUser instructorUser = userAbstractFactoryTest.createUserInstance();
        instructorUser.setBannerId(bannerId);
        instructorUser.setEmailId("stu@gmail.com");
        instructorUser.setFirstName("John");
        instructorUser.setLastName("sam");
        instructorUser.setId(instructor);
        return true;
    }

    @Override
    public ArrayList<ICourse> getInstructorCourses(String emailId) {
        ArrayList<ICourse> instCourseList = courseAbstractFactoryTest.createCourseListInstance();
        ICourse course = courseAbstractFactoryTest.createCourseInstance();
        course.setCredits(credits);
        course.setDescription(description);
        course.setName(name);
        course.setId(id);
        instCourseList.add(course);
        return instCourseList;
    }

    @Override
    public ArrayList<IUser> getTAForCourse(String courseId) {
        ArrayList<IUser> taList = userAbstractFactoryTest.createUserListInstance();
        IUser ta = userAbstractFactoryTest.createUserInstance();
        ta.setBannerId(bannerId);
        ta.setEmailId("stu@gmail.com");
        ta.setFirstName("John");
        ta.setLastName("sam");
        taList.add(ta);
        return taList;
    }

    @Override
    public ArrayList<IUser> getInstructorsForCourse(String courseId) {
        ArrayList<IUser> instructorList = userAbstractFactoryTest.createUserListInstance();
        IUser instructor = userAbstractFactoryTest.createUserInstance();
        instructor.setBannerId(bannerId);
        instructor.setEmailId("stu@gmail.com");
        instructor.setFirstName("John");
        instructor.setLastName("sam");
        instructorList.add(instructor);
        return instructorList;
    }

    @Override
    public boolean enrollTAForCourseUsingEmailId(IUser user, String courseId) {
        return courseId == "1";
    }

    @Override
    public boolean getUserRoleForCourse(String userId, String courseId) {
        IUserCourses userCourses = courseAbstractFactoryTest.createUserCoursesInstance();
        userCourses.setBannerId(bannerId);
        userCourses.setCourseDescription(courseDescription);
        userCourses.setCourseId(courseId);
        userCourses.setCourseName(courseName);
        userCourses.setUserRole(userRole);
        return true;
    }

}
