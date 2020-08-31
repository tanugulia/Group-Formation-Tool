package CSCI5308.GroupFormationTool.Mail;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;

public class MailInjector {

    private static MailInjector instance = null;

    private IMailAbstractFactory mailAbstractFactory;

    private IMailManager mailManager;

    private SimpleMailMessage mailMessage;

    private JavaMailSenderImpl mailSender;

    private MailInjector() {
        mailAbstractFactory = new MailAbstractFactory();
        mailManager = mailAbstractFactory.createMailManagerInstance();
        mailMessage = mailAbstractFactory.createSimpleMailMessageInstance();
        mailSender = mailAbstractFactory.createJavaMailSenderInstance();
    }

    public static MailInjector instance() {
        if (instance == null) {
            instance = new MailInjector();
        }
        return instance;
    }

    public IMailManager getMailManager() {
        return mailManager;
    }

    public void setMailManager(IMailManager mailManager) {
        this.mailManager = mailManager;
    }

    public SimpleMailMessage getMailMessage() {
        return mailMessage;
    }

    public JavaMailSenderImpl getMailSender() {
        return mailSender;
    }

}
