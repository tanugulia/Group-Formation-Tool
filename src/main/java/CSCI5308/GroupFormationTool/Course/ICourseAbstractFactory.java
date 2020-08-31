package CSCI5308.GroupFormationTool.Course;

import com.opencsv.bean.CsvToBean;
import org.apache.commons.text.RandomStringGenerator;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public interface ICourseAbstractFactory {

    ICourse createCourseInstance();

    ArrayList<ICourse> createCourseListInstance();

    IUserCourses createUserCoursesInstance();

    ICourseRepository createCourseRepository();

    IUserCoursesRepository createUserCoursesRepository();

    IStudentRepository createStudentRepository();

    StudentCSV createStudentCSVInstance();

    ArrayList<StudentCSV> createStudentCSVListInstance();

    HashMap<Integer, List<StudentCSV>> createStudentHashMapInstance();

    CsvToBean<StudentCSV> createCsvToBeanBuilderInstance(Reader reader);

    BufferedReader createBufferedReaderInstance(InputStreamReader inputStreamReader);

    InputStreamReader createInputStreamInstance(InputStream inputStream);

    RandomStringGenerator createRandomStringGeneratorInstance();
}
