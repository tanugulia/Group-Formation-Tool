package CSCI5308.GroupFormationTool.Mail;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;

public interface ITestMailManagerAbstractFactory {

    MailManager createMailManagerMock();

    JavaMailSenderImpl createJavaMailSenderInstance();

    SimpleMailMessage createSimpleMailMessageInstance();

    MailManager createMailManagerInstance();

}
