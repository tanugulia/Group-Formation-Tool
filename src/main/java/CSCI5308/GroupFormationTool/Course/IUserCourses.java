package CSCI5308.GroupFormationTool.Course;

import CSCI5308.GroupFormationTool.User.IUser;

import java.util.ArrayList;

public interface IUserCourses {

    String getCourseId();

    void setCourseId(String courseId);

    String getBannerId();

    void setBannerId(String bannerId);

    String getCourseName();

    void setCourseName(String courseName);

    String getCourseDescription();

    void setCourseDescription(String courseDescription);

    String getUserRole();

    void setUserRole(String userRole);

    ArrayList<ICourse> getStudentCourses(String emailId);

    String getUserRoleByEmailId(String emailId);

    ArrayList<ICourse> getTACourses(String emailId);

    ArrayList<IUser> usersCurrentlyNotInstructorsForCourse(String courseId);

    boolean addInstructorsToCourse(Long instructor, String courseId);

    ArrayList<ICourse> getInstructorCourses(String emailId);

    ArrayList<IUser> getInstructorsForCourse(String courseId);

    ArrayList<IUser> getTAForCourse(String courseId);

    boolean enrollTAForCourseUsingEmailId(IUser user, String courseId);

}
