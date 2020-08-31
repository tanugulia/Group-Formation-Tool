package CSCI5308.GroupFormationTool.Course;

import java.util.List;
import java.util.Map;

public interface IStudentRepository {

    Map<Integer, List<StudentCSV>> createStudent(List<StudentCSV> student, String courseId);
}
