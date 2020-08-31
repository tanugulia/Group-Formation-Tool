package CSCI5308.GroupFormationTool.Mail;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;

public interface IMailAbstractFactory {

    IMailManager createMailManagerInstance();

    SimpleMailMessage createSimpleMailMessageInstance();

    JavaMailSenderImpl createJavaMailSenderInstance();

}
