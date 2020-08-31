package CSCI5308.GroupFormationTool.Mail;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import static org.mockito.Mockito.mock;

public class TestMailManagerAbstractFactory implements ITestMailManagerAbstractFactory {

    @Override
    public MailManager createMailManagerMock() {
        return mock(MailManager.class);
    }

    @Override
    public JavaMailSenderImpl createJavaMailSenderInstance() {
        return new JavaMailSenderImpl();
    }

    @Override
    public SimpleMailMessage createSimpleMailMessageInstance() {
        return new SimpleMailMessage();
    }

    @Override
    public MailManager createMailManagerInstance() {
        return new MailManager();
    }
}
