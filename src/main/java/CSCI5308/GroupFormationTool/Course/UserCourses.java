package CSCI5308.GroupFormationTool.Course;

import CSCI5308.GroupFormationTool.Question.Question;
import CSCI5308.GroupFormationTool.User.IUser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;

public class UserCourses implements IUserCourses {

    private static final Logger Log = LoggerFactory.getLogger(Question.class.getName());

    private IUserCoursesRepository userCoursesRepository;

    private String courseId;

    private String bannerId;

    private String courseName;

    private String courseDescription;

    private String userRole;

    public UserCourses() {
        courseId = null;
        courseName = null;
        courseDescription = null;
        bannerId = null;
        userRole = null;
    }

    @Override
    public String getCourseId() {
        return courseId;
    }

    @Override
    public void setCourseId(String courseId) {
        this.courseId = courseId;
    }

    @Override
    public String getBannerId() {
        return bannerId;
    }

    @Override
    public void setBannerId(String bannerId) {
        this.bannerId = bannerId;
    }

    @Override
    public String getCourseName() {
        return courseName;
    }

    @Override
    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    @Override
    public String getCourseDescription() {
        return courseDescription;
    }

    @Override
    public void setCourseDescription(String courseDescription) {
        this.courseDescription = courseDescription;
    }

    @Override
    public String getUserRole() {
        return userRole;
    }

    @Override
    public void setUserRole(String userRole) {
        this.userRole = userRole;
    }

    @Override
    public String getUserRoleByEmailId(String emailId) {
        userCoursesRepository = CourseInjector.instance().getUserCoursesRepository();
        Log.info("Get the role of a user by email Id");
        return userCoursesRepository.getUserRoleByEmailId(emailId);
    }

    @Override
    public ArrayList<ICourse> getStudentCourses(String emailId) {
        userCoursesRepository = CourseInjector.instance().getUserCoursesRepository();
        Log.info("Get the courses enrolled by a student using student's email Id");
        return userCoursesRepository.getStudentCourses(emailId);
    }

    @Override
    public ArrayList<ICourse> getTACourses(String emailId) {
        userCoursesRepository = CourseInjector.instance().getUserCoursesRepository();
        Log.info("Get the courses for a TA using email Id");
        return userCoursesRepository.getTACourses(emailId);
    }

    @Override
    public ArrayList<IUser> usersCurrentlyNotInstructorsForCourse(String courseId) {
        userCoursesRepository = CourseInjector.instance().getUserCoursesRepository();
        Log.info("The function call return the users that are not enrolled as an instructor for a course");
        return userCoursesRepository.usersCurrentlyNotInstructorsForCourse(courseId);
    }

    @Override
    public boolean addInstructorsToCourse(Long instructor, String courseId) {
        userCoursesRepository = CourseInjector.instance().getUserCoursesRepository();
        Log.info("Function call to add an instructor for a course using course Id and email Id");
        return userCoursesRepository.addInstructorsToCourse(instructor, courseId);
    }

    @Override
    public ArrayList<ICourse> getInstructorCourses(String emailId) {
        userCoursesRepository = CourseInjector.instance().getUserCoursesRepository();
        Log.info("Function call to get instructors for a course using email Id");
        return userCoursesRepository.getInstructorCourses(emailId);
    }

    @Override
    public ArrayList<IUser> getTAForCourse(String courseId) {
        userCoursesRepository = CourseInjector.instance().getUserCoursesRepository();
        Log.info("Function call to get TA for a course using course Id");
        return userCoursesRepository.getTAForCourse(courseId);
    }

    @Override
    public boolean enrollTAForCourseUsingEmailId(IUser user, String courseId) {
        userCoursesRepository = CourseInjector.instance().getUserCoursesRepository();
        Log.info("Function call to enroll a TA for the course using email id");
        return userCoursesRepository.enrollTAForCourseUsingEmailId(user, courseId);
    }

    @Override
    public ArrayList<IUser> getInstructorsForCourse(String courseId) {
        userCoursesRepository = CourseInjector.instance().getUserCoursesRepository();
        Log.info("Function call to get instructor for a course using course Id");
        return userCoursesRepository.getInstructorsForCourse(courseId);
    }

}
