package CSCI5308.GroupFormationTool.Course;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;

public class Course implements ICourse {

    private static final Logger Log = LoggerFactory.getLogger(Course.class.getName());

    private String id;

    private String name;

    private int credits;

    private String description;

    private ICourseRepository courseRepository;

    public Course() {
        id = null;
        name = null;
        credits = 0;
        description = null;
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public int getCredits() {
        return credits;
    }

    @Override
    public void setCredits(int credits) {
        this.credits = credits;
    }

    @Override
    public String getDescription() {
        return description;
    }

    @Override
    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public ArrayList<ICourse> getAllCourses() {
        Log.info("Calling the courseRepository function to get all courses");
        courseRepository = CourseInjector.instance().getCourseRepository();
        return courseRepository.getAllCourses();
    }

    @Override
    public ICourse getCourseById(String courseId) {
        Log.info("Calling the courseRepository function to get courses details by course id: " + courseId);
        courseRepository = CourseInjector.instance().getCourseRepository();
        return courseRepository.getCourseById(courseId);
    }

    public boolean createCourse() {
        Log.info("Creating a new course and storing it in the database");
        courseRepository = CourseInjector.instance().getCourseRepository();
        return courseRepository.createCourse(this);
    }

    @Override
    public boolean deleteCourse(String courseId) {
        Log.info("Calling the deleteCourse repository function to delete the course" + courseId + "from the Database");
        courseRepository = CourseInjector.instance().getCourseRepository();
        return courseRepository.deleteCourse(courseId);
    }

}
