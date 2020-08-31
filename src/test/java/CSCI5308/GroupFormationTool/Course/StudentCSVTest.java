package CSCI5308.GroupFormationTool.Course;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@SpringBootTest
public class StudentCSVTest {

    private StudentCSV studentCSV;

    private StudentRepository studentRepository;

    private ITestCourseAbstractFactory courseAbstractFactoryTest = TestCourseInjector.instance().
            getCourseAbstractFactory();

    @BeforeEach
    public void init() {
        studentCSV = courseAbstractFactoryTest.createStudentCSVInstance();
        studentRepository = courseAbstractFactoryTest.createStudentRepositoryMock();
        CourseInjector.instance().setStudentRepository(studentRepository);
    }

    private IStudentCSV createDefaultStudentCSV() {
        StudentDBMock studentDBMock = courseAbstractFactoryTest.createStudentDBMock();
        IStudentCSV studentCSV = loadStudentCSV(studentDBMock);
        return studentCSV;
    }

    private IStudentCSV loadStudentCSV(StudentDBMock studentDBMock) {
        IStudentCSV studentCSV = courseAbstractFactoryTest.createStudentCSVInstance();
        studentCSV = studentDBMock.loadStudentCSVWithID(studentCSV);
        return studentCSV;
    }

    @Test
    public void getFirstNameTest() {
        IStudentCSV studentCSV = createDefaultStudentCSV();
        assertEquals("Tanu", studentCSV.getFirstName());
    }

    @Test
    public void setFirstNameTest() {
        IStudentCSV studentCSV = courseAbstractFactoryTest.createStudentCSVInstance();
        studentCSV.setFirstName("Padmesh");
        assertEquals("Padmesh", studentCSV.getFirstName());
    }

    @Test
    public void getLastNameTest() {
        IStudentCSV studentCSV = createDefaultStudentCSV();
        assertEquals("Gulia", studentCSV.getLastName());
    }

    @Test
    public void setLastNameTest() {
        IStudentCSV studentCSV = courseAbstractFactoryTest.createStudentCSVInstance();
        studentCSV.setLastName("Donthu");
        assertEquals("Donthu", studentCSV.getLastName());
    }

    @Test
    public void getBannerIdTest() {
        IStudentCSV studentCSV = createDefaultStudentCSV();
        assertEquals("B00839890", studentCSV.getBannerId());
    }

    @Test
    public void setBannerIdTest() {
        IStudentCSV studentCSV = courseAbstractFactoryTest.createStudentCSVInstance();
        studentCSV.setBannerId("B0000000");
        assertEquals("B0000000", studentCSV.getBannerId());
    }

    @Test
    public void getEmailIdTest() {
        IStudentCSV studentCSV = createDefaultStudentCSV();
        assertEquals("tn300318@dal.ca", studentCSV.getEmail());
    }

    @Test
    public void setEmailIdTest() {
        IStudentCSV studentCSV = courseAbstractFactoryTest.createStudentCSVInstance();
        studentCSV.setEmail("padmeshdonthu@gmail.com");
        assertEquals("padmeshdonthu@gmail.com", studentCSV.getEmail());
    }

    @Test
    public void getPasswordTest() {
        IStudentCSV studentCSV = createDefaultStudentCSV();
        assertEquals("password1234", studentCSV.getPassword());
    }

    @Test
    public void setPasswordTest() {
        IStudentCSV studentCSV = courseAbstractFactoryTest.createStudentCSVInstance();
        studentCSV.setPassword("password");
        assertEquals("password", studentCSV.getPassword());
    }

    @Test
    public void StudentCSVParameterizedTest() {
        IStudentCSV studentCSV = courseAbstractFactoryTest.createStudentCSVInstanceParameterized("Padmesh",
                "Donthu", "padmeshdonthu@gmail.com",
                "B00854462", "sample123");
        assertEquals("Padmesh", studentCSV.getFirstName());
        assertFalse(studentCSV.getLastName() == null);
        assertTrue(studentCSV.getEmail().equals("padmeshdonthu@gmail.com"));
        assertFalse(studentCSV.getPassword().isEmpty());
        assertTrue(studentCSV.getPassword().length() < 200);
        assertFalse(studentCSV.getBannerId().isEmpty());
        assertTrue(studentCSV.getBannerId().length() < 100);
    }

    @Test
    void createStudent() {
        String data = "firstName,lastName,email,bannerId\n";
        data += "padmesh,donthu,padmeshdonthu@gmail.com,B00854462";
        String finalData = data;
        MultipartFile multipartFile = new MultipartFile() {

            @Override
            public String getName() {
                return "sample";
            }

            @Override
            public String getOriginalFilename() {
                return "sample";
            }

            @Override
            public String getContentType() {
                return MediaType.TEXT_PLAIN_VALUE;
            }

            @Override
            public boolean isEmpty() {
                return false;
            }

            @Override
            public long getSize() {
                return finalData.length();
            }

            @Override
            public byte[] getBytes() throws IOException {
                return finalData.getBytes();
            }

            @Override
            public InputStream getInputStream() throws IOException {
                InputStream inputStream = new ByteArrayInputStream(finalData.getBytes());
                return inputStream;
            }

            @Override
            public void transferTo(File dest) throws IOException, IllegalStateException {
            }
        };

        String courseId = "CSCI 5308";
        ArrayList<StudentCSV> studentCSVs = courseAbstractFactoryTest.createStudentCSVListInstance();
        HashMap<Integer, List<StudentCSV>> studentLists = courseAbstractFactoryTest.createStudentListHashMap();
        when(studentRepository.createStudent(studentCSVs, courseId)).thenReturn(studentLists);
        assertTrue(studentCSV.createStudent(multipartFile, courseId).size() == 0);
    }

}
