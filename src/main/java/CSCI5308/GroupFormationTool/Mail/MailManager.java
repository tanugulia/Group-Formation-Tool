package CSCI5308.GroupFormationTool.Mail;

import CSCI5308.GroupFormationTool.Common.DomainConstants;
import CSCI5308.GroupFormationTool.Course.StudentCSV;
import CSCI5308.GroupFormationTool.User.IUser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.scheduling.annotation.Async;

import java.util.List;
import java.util.Properties;

public class MailManager implements IMailManager {

    private static final Logger Log = LoggerFactory.getLogger(MailManager.class.getName());

    private JavaMailSenderImpl mailSender;

    private SimpleMailMessage message;

    @Override
    public void sendEmail(JavaMailSender javaMailSender, SimpleMailMessage message) {
        javaMailSender.send(message);
    }

    @Override
    public JavaMailSenderImpl setupMailSender(JavaMailSenderImpl mailSender) {
        mailSender.setHost(DomainConstants.smtpHost);
        mailSender.setPort(DomainConstants.smtpPort);
        mailSender.setUsername(DomainConstants.mailUserName);
        mailSender.setPassword(DomainConstants.mailPassword);
        Properties props = mailSender.getJavaMailProperties();
        props.put("mail.transport.protocol", "smtp");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.debug", "true");
        props.put("mail.smtp.ssl.trust", "smtp.gmail.com");
        Log.info("smtp connection for mail has been setup");
        return mailSender;
    }

    @Override
    public boolean sendForgotPasswordMail(IUser userByEmailId, String token) {
        mailSender = setupMailSender(MailInjector.instance().getMailSender());
        message = MailInjector.instance().getMailMessage();
        String URL = DomainConstants.domainUrl + "/resetPassword?token=" + token;
        message.setTo(userByEmailId.getEmailId());
        message.setSubject(DomainConstants.forgotPasswordSubject);
        message.setFrom(DomainConstants.mailUserName);
        message.setText(DomainConstants.forgotPasswordText + URL);
        sendEmail(mailSender, message);
        return true;
    }

    @Override
    @Async
    public boolean sendBatchMail(List<StudentCSV> users, String courseID) {
        mailSender = setupMailSender(MailInjector.instance().getMailSender());
        message = MailInjector.instance().getMailMessage();
        message.setSubject(DomainConstants.registrationSubject);
        message.setFrom(DomainConstants.mailUserName);
        for (int userCount = 0; userCount < users.size(); userCount++) {
            message.setTo(users.get(userCount).getEmail());
            message.setText("Hi,\n\nYou have been added to Group Formation Tool as a student in course " + courseID
                    + ".\n\n" + "Following are your login credentials:\n\nLogin using EmailId: "
                    + users.get(userCount).getEmail() + "\nPassword: " + users.get(userCount).getPassword()
                    + "\n\nTo login, go to : " + DomainConstants.domainUrl + "/login"
                    + "\n\n\nKind Regards,\nGroup Formation Tool Team-22");

            sendEmail(mailSender, message);
        }
        return true;
    }

}
