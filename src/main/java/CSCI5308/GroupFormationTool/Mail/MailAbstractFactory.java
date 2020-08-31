package CSCI5308.GroupFormationTool.Mail;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;

public class MailAbstractFactory implements IMailAbstractFactory {

    @Override
    public IMailManager createMailManagerInstance() {
        return new MailManager();
    }

    @Override
    public SimpleMailMessage createSimpleMailMessageInstance() {
        return new SimpleMailMessage();
    }

    @Override
    public JavaMailSenderImpl createJavaMailSenderInstance() {
        return new JavaMailSenderImpl();
    }
}
