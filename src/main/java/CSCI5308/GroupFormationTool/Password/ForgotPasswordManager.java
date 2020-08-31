package CSCI5308.GroupFormationTool.Password;

import CSCI5308.GroupFormationTool.Common.DomainConstants;
import CSCI5308.GroupFormationTool.Mail.IMailManager;
import CSCI5308.GroupFormationTool.Mail.MailInjector;
import CSCI5308.GroupFormationTool.Security.IPasswordEncryptor;
import CSCI5308.GroupFormationTool.Security.SecurityInjector;
import CSCI5308.GroupFormationTool.User.IUser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ForgotPasswordManager implements IForgotPasswordManager {

    private static final Logger Log = LoggerFactory.getLogger(ForgotPasswordManager.class.getName());

    private IForgotPasswordRepository forgotPasswordRepository;

    private ITokenGenerator tokenGenerator;

    private IPasswordHistoryManager passwordHistoryManager;

    private IMailManager mailManager;

    private IPasswordEncryptor encryptor;

    private IPolicy policyInstance;

    @Override
    public String notifyUser(IUser user) {
        forgotPasswordRepository = PasswordInjector.instance().getForgotPasswordRepository();
        tokenGenerator = PasswordInjector.instance().getTokenGenerator();
        mailManager = MailInjector.instance().getMailManager();
        encryptor = SecurityInjector.instance().getPasswordEncryptor();
        String errorMessage = null;
        IUser userByEmailId = forgotPasswordRepository.getUserId(user);
        if (userByEmailId != null) {
            String token = forgotPasswordRepository.getToken(userByEmailId);
            if (token.equals("")) {
                Log.info("User is adding new token");
                token = tokenGenerator.generator();
                forgotPasswordRepository.addToken(userByEmailId, token);
            } else {
                Log.info("User is updating existing token");
                token = tokenGenerator.generator();
                forgotPasswordRepository.updateToken(userByEmailId, token);
            }
            mailManager.sendForgotPasswordMail(userByEmailId, token);
        } else {
            errorMessage = DomainConstants.userDoesNotExists
                    .replace("[[emailId]]", user.getEmailId());
        }
        return errorMessage;
    }

    @Override
    public String updatePassword(IUser user, String token) {
        IPasswordAbstractFactory passwordAbstractFactory = PasswordInjector.instance().getPasswordAbstractFactory();
        policyInstance = passwordAbstractFactory.createPolicyInstance();
        String password = user.getPassword();
        String passwordSecurityError = policyInstance.passwordSPolicyCheck(password);
        String errorMessage = null;
        if (passwordSecurityError != null) {
            errorMessage = passwordSecurityError;
            return errorMessage;
        }
        if ((user.getPassword().equals(user.getConfirmPassword())) == false) {
            errorMessage = DomainConstants.passwordsDontMatch;
            return errorMessage;
        }
        boolean isHistoryViolated = false;
        forgotPasswordRepository = PasswordInjector.instance().getForgotPasswordRepository();
        passwordHistoryManager = PasswordInjector.instance().getPasswordHistoryManager();
        encryptor = SecurityInjector.instance().getPasswordEncryptor();
        IUser userByEmailId = forgotPasswordRepository.getEmailByToken(user, token);
        if (userByEmailId == null) {
            errorMessage = DomainConstants.tokenExpiredMessage;
            return errorMessage;
        }
        isHistoryViolated = passwordHistoryManager.isHistoryViolated(userByEmailId, user.getPassword());
        if (isHistoryViolated) {
            Log.info("User violated history while updating password");
            errorMessage = DomainConstants.passwordHistoryMessage
                    .replace("[[history]]", passwordHistoryManager.
                            getSettingValue(DomainConstants.passwordHistory));
            return errorMessage;
        }
        String encrypted_password = encryptor.encoder(user.getPassword());
        forgotPasswordRepository.updatePassword(userByEmailId, encrypted_password);
        passwordHistoryManager.addPasswordHistory(userByEmailId, encrypted_password);
        return null;
    }
}
