package CSCI5308.GroupFormationTool.Mail;

import CSCI5308.GroupFormationTool.Common.DomainConstants;
import CSCI5308.GroupFormationTool.Course.ITestCourseAbstractFactory;
import CSCI5308.GroupFormationTool.Course.StudentCSV;
import CSCI5308.GroupFormationTool.Course.TestCourseInjector;
import CSCI5308.GroupFormationTool.User.ITestUserAbstractFactory;
import CSCI5308.GroupFormationTool.User.IUser;
import CSCI5308.GroupFormationTool.User.TestUserInjector;
import org.junit.jupiter.api.Test;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;


public class MailManagerTest {

    private MailManager mailManager;

    private JavaMailSenderImpl javaMailSender;

    private SimpleMailMessage mailMessage;

    private ITestMailManagerAbstractFactory mailManagerAbstractFactoryTest = TestMailInjector.instance().
            getMailManagerAbstractFactory();

    private ITestUserAbstractFactory userAbstractFactoryTest = TestUserInjector.instance().getUserAbstractFactory();

    private ITestCourseAbstractFactory courseAbstractFactoryTest = TestCourseInjector.instance().
            getCourseAbstractFactory();

    @Test
    void setupMailSenderTest() {
        mailManager = mailManagerAbstractFactoryTest.createMailManagerInstance();
        javaMailSender = mailManagerAbstractFactoryTest.createJavaMailSenderInstance();
        mailMessage = mailManagerAbstractFactoryTest.createSimpleMailMessageInstance();
        assertTrue(mailManager.setupMailSender(javaMailSender) instanceof JavaMailSenderImpl);
        assertTrue(mailManager.setupMailSender(javaMailSender).getHost().equals(DomainConstants.smtpHost));
        assertFalse(mailManager.setupMailSender(javaMailSender).getPassword().isEmpty());
    }

    @Test
    void sendForgotPasswordMailTest() {
        mailManager = mailManagerAbstractFactoryTest.createMailManagerMock();
        IUser user = userAbstractFactoryTest.createUserInstance();
        user.setId(123);
        user.setBannerId("B00827531");
        user.setEmailId("haard.shah@dal.ca");
        user.setFirstName("haard");
        user.setLastName("shah");
        user.setPassword("pswd12345");
        user.setConfirmPassword("pswd12345");
        String token = "sample token";
        when(mailManager.sendForgotPasswordMail(user, token)).thenReturn(true);
        assertTrue(mailManager.sendForgotPasswordMail(user, token));
    }

    @Test
    void sendBatchMailTest() {
        mailManager = mailManagerAbstractFactoryTest.createMailManagerMock();
        ArrayList<StudentCSV> studentCSVList = courseAbstractFactoryTest.createStudentCSVListInstance();
        StudentCSV studentCSV = courseAbstractFactoryTest.createStudentCSVInstance();
        studentCSV.setFirstName("Padmesh");
        studentCSV.setLastName("Donthu");
        studentCSV.setBannerId("B00854462");
        studentCSV.setEmail("padmeshdonthu@gmail.com");
        studentCSVList.add(studentCSV);
        studentCSV = courseAbstractFactoryTest.createStudentCSVInstance();
        studentCSV.setFirstName("Padmesh");
        studentCSV.setLastName("Kumar");
        studentCSV.setBannerId("B00854461");
        studentCSV.setEmail("padmeshd1996@gmail.com");
        studentCSVList.add(studentCSV);
        when(mailManager.sendBatchMail(studentCSVList, "CSCI 5308")).thenReturn(true);
        assertTrue(mailManager.sendBatchMail(studentCSVList, "CSCI 5308"));
    }
}
