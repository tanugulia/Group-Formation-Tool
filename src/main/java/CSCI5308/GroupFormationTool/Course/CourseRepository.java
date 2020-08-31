package CSCI5308.GroupFormationTool.Course;

import CSCI5308.GroupFormationTool.Database.DatabaseInjector;
import CSCI5308.GroupFormationTool.Database.IDatabaseAbstractFactory;
import CSCI5308.GroupFormationTool.Database.StoredProcedure;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class CourseRepository implements ICourseRepository {

    private static final Logger Log = LoggerFactory.getLogger(CourseRepository.class.getName());

    @Override
    public ArrayList<ICourse> getAllCourses() {
        StoredProcedure storedProcedure = null;
        IDatabaseAbstractFactory databaseAbstractFactory = DatabaseInjector.instance().getDatabaseAbstractFactory();
        ICourseAbstractFactory courseAbstractFactory = CourseInjector.instance().getCourseAbstractFactory();
        ArrayList<ICourse> courseList = courseAbstractFactory.createCourseListInstance();
        try {
            Log.info("Calling stored procedure sp_getAllCourseDetails to get all course details");
            storedProcedure = databaseAbstractFactory.createStoredProcedureInstance("sp_getAllCourseDetails");
            ResultSet results = storedProcedure.executeWithResults();
            if (results != null) {
                while (results.next()) {
                    {
                        ICourse course = courseAbstractFactory.createCourseInstance();
                        course.setId(results.getString("course_id"));
                        course.setName(results.getString("course_name"));
                        course.setDescription(results.getString("course_details"));
                        course.setCredits(results.getInt("course_credits"));
                        courseList.add(course);
                    }
                }
            }
        } catch (SQLException exception) {
            Log.error("Could not execute the Stored procedure sp_getAllCourseDetails" +
                    " because of an SQL Exception " + exception.getLocalizedMessage());
        } finally {
            if (storedProcedure != null) {
                storedProcedure.removeConnections();
            }
        }
        return courseList;
    }

    @Override
    public boolean createCourse(ICourse course) {
        StoredProcedure storedProcedure = null;
        IDatabaseAbstractFactory databaseAbstractFactory = DatabaseInjector.instance().getDatabaseAbstractFactory();
        boolean status = true;
        try {
            Log.info("Calling stored procedure sp_createCourse to create a course");
            storedProcedure = databaseAbstractFactory.createStoredProcedureInstance
                    ("sp_createCourse(?,?,?,?,?)");
            storedProcedure.setInputStringParameter(1, course.getId());
            storedProcedure.setInputStringParameter(2, course.getName());
            storedProcedure.setInputIntParameter(3, course.getCredits());
            storedProcedure.setInputStringParameter(4, course.getDescription());
            storedProcedure.registerOutputParameterBoolean(5);
            storedProcedure.execute();
            status = storedProcedure.getParameter(5);
        } catch (SQLException exception) {
            Log.error("Could not execute the Stored procedure sp_getAllCourseDetails" +
                    " because of an SQL Exception " + exception.getLocalizedMessage());
        } finally {
            if (storedProcedure != null) {
                storedProcedure.removeConnections();
            }
        }
        return status;
    }

    @Override
    public boolean deleteCourse(String id) {
        StoredProcedure storedProcedure = null;
        IDatabaseAbstractFactory databaseAbstractFactory = DatabaseInjector.instance().getDatabaseAbstractFactory();
        boolean status = true;
        try {
            Log.info("Calling stored procedure sp_deleteACourse to delete a course by courseId " + id);
            storedProcedure = databaseAbstractFactory.createStoredProcedureInstance("sp_deleteACourse(?,?)");
            storedProcedure.setInputStringParameter(1, id);
            storedProcedure.registerOutputParameterBoolean(2);
            storedProcedure.execute();
            status = storedProcedure.getParameter(2);
        } catch (SQLException exception) {
            Log.error("Could not execute the Stored procedure sp_getAllCourseDetails" +
                    " because of an SQL Exception " + exception.getLocalizedMessage());
        } finally {
            if (storedProcedure != null) {
                storedProcedure.removeConnections();
            }
        }
        return status;
    }

    public ICourse getCourseById(String courseId) {
        StoredProcedure storedProcedure = null;
        IDatabaseAbstractFactory databaseAbstractFactory = DatabaseInjector.instance().getDatabaseAbstractFactory();
        ICourseAbstractFactory courseAbstractFactory = CourseInjector.instance().getCourseAbstractFactory();
        ICourse course = courseAbstractFactory.createCourseInstance();
        try {
            Log.info("Calling stored procedure sp_getCourseById to get a course details by Course Id " + courseId);
            storedProcedure = databaseAbstractFactory.createStoredProcedureInstance("sp_getCourseById(?)");
            storedProcedure.setInputStringParameter(1, courseId);
            ResultSet results = storedProcedure.executeWithResults();
            if (results != null) {
                while (results.next()) {
                    {
                        course.setId(results.getString("course_id"));
                        course.setName(results.getString("course_name"));
                        course.setDescription(results.getString("course_details"));
                        course.setCredits(results.getInt("course_credits"));
                    }
                }
            }
        } catch (SQLException exception) {
            Log.error("Could not execute the Stored procedure sp_getAllCourseDetails" +
                    " because of an SQL Exception " + exception.getLocalizedMessage());
        } finally {
            if (storedProcedure != null) {
                storedProcedure.removeConnections();
            }
        }
        return course;
    }

}
