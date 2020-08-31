package CSCI5308.GroupFormationTool.Course;

import java.util.ArrayList;

public interface ICourse {

    String getId();

    void setId(String id);

    String getName();

    void setName(String name);

    int getCredits();

    void setCredits(int credits);

    String getDescription();

    void setDescription(String description);

    ArrayList<ICourse> getAllCourses();

    ICourse getCourseById(String courseId);

    boolean deleteCourse(String courseId);

    boolean createCourse();

}
