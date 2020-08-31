package CSCI5308.GroupFormationTool.Course;

import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import org.apache.commons.text.RandomStringGenerator;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class CourseAbstractFactory implements ICourseAbstractFactory {

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
    public ICourseRepository createCourseRepository() {
        return new CourseRepository();
    }

    @Override
    public IUserCoursesRepository createUserCoursesRepository() {
        return new UserCoursesRepository();
    }

    @Override
    public IStudentRepository createStudentRepository() {
        return new StudentRepository();
    }

    @Override
    public StudentCSV createStudentCSVInstance() {
        return new StudentCSV();
    }

    @Override
    public ArrayList<StudentCSV> createStudentCSVListInstance() {
        return new ArrayList<StudentCSV>();
    }

    @Override
    public HashMap<Integer, List<StudentCSV>> createStudentHashMapInstance() {
        return new HashMap<Integer, List<StudentCSV>>();
    }

    @Override
    public CsvToBean<StudentCSV> createCsvToBeanBuilderInstance(Reader reader) {
        return new CsvToBeanBuilder<StudentCSV>(reader).withType(StudentCSV.class)
                .withIgnoreLeadingWhiteSpace(true).build();
    }

    @Override
    public BufferedReader createBufferedReaderInstance(InputStreamReader inputStreamReader) {
        return new BufferedReader(inputStreamReader);
    }

    @Override
    public InputStreamReader createInputStreamInstance(InputStream inputStream) {
        return new InputStreamReader(inputStream);
    }

    @Override
    public RandomStringGenerator createRandomStringGeneratorInstance() {
        return new RandomStringGenerator.Builder().withinRange(33, 45).build();
    }
}
