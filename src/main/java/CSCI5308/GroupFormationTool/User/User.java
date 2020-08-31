package CSCI5308.GroupFormationTool.User;

import CSCI5308.GroupFormationTool.Common.DomainConstants;
import CSCI5308.GroupFormationTool.Password.IPasswordAbstractFactory;
import CSCI5308.GroupFormationTool.Password.IPasswordHistoryManager;
import CSCI5308.GroupFormationTool.Password.IPolicy;
import CSCI5308.GroupFormationTool.Password.PasswordInjector;
import CSCI5308.GroupFormationTool.Security.IPasswordEncryptor;
import CSCI5308.GroupFormationTool.Security.SecurityInjector;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class User implements IUser {

    private static final Logger log = LoggerFactory.getLogger(User.class.getName());

    private IUserRepository userRepository;

    private IPasswordEncryptor encryptor;

    private IPolicy policyInstance;

    private IPasswordHistoryManager passwordHistoryManager;

    private long id;

    private String firstName;

    private String lastName;

    private String bannerId;

    private String emailId;

    private String password;

    private String confirmPassword;

    public User() {
        id = -1;

        firstName = null;

        lastName = null;

        bannerId = null;

        emailId = null;

        password = null;

        confirmPassword = null;
    }

    @Override
    public long getId() {
        return id;
    }

    @Override
    public void setId(long id) {
        this.id = id;
    }

    @Override
    public String getFirstName() {
        return firstName;
    }

    @Override
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    @Override
    public String getLastName() {
        return lastName;
    }

    @Override
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    @Override
    public String getBannerId() {
        return bannerId;
    }

    @Override
    public void setBannerId(String bannerId) {
        this.bannerId = bannerId;
    }

    @Override
    public String getEmailId() {
        return emailId;
    }

    @Override
    public void setEmailId(String emailId) {
        this.emailId = emailId;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String getConfirmPassword() {
        return confirmPassword;
    }

    @Override
    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }

    @Override
    public String createUser(IUser user) {
        log.info("Creating the user and saving it to the database");
        IPasswordAbstractFactory passwordAbstractFactory = PasswordInjector.instance().getPasswordAbstractFactory();
        String errorMessage = null;
        if (checkForValues(user) == false) {
            log.warn("User cannot be created due to some invalid details");
            errorMessage = DomainConstants.signupInvalidDetails;
        }
        policyInstance = passwordAbstractFactory.createPolicyInstance();
        String password = user.getPassword();
        String passwordSecurityError = policyInstance.passwordSPolicyCheck(password);
        if (passwordSecurityError != null) {
            log.warn("User cannot be created due to violation of password security policy");
            errorMessage = passwordSecurityError;
            return errorMessage;
        }
        if ((user.getPassword().equals(user.getConfirmPassword())) == false) {
            log.warn("User cannot be created due to mismatch of confirm password field");
            errorMessage = DomainConstants.passwordsDontMatch;
            return errorMessage;
        }
        userRepository = UserInjector.instance().getUserRepository();
        passwordHistoryManager = PasswordInjector.instance().getPasswordHistoryManager();
        encryptor = SecurityInjector.instance().getPasswordEncryptor();
        user.setPassword(encryptor.encoder(user.getPassword()));
        IUser userByEmailId = userRepository.getUserByEmailId(user);
        if (userByEmailId == null) {
            log.info("Calling the user repository function to save the new user to Database");
            userRepository.createUser(user);
            IUser userWithUserId = userRepository.getUserIdByEmailId(user);
            passwordHistoryManager.addPasswordHistory(userWithUserId, user.getPassword());
        } else {
            log.warn("User cannot be created as the user with same details already exists");
            errorMessage = DomainConstants.userAlreadyExists
                    .replace("[[emailId]]", user.getEmailId());
        }
        return errorMessage;
    }

    @Override
    public boolean checkCurrentUserIsAdmin(String emailId) {
        log.info("Checking if current user is admin");
        userRepository = UserInjector.instance().getUserRepository();
        IUser adminDetails = userRepository.getAdminDetails();
        boolean outcome = adminDetails.getEmailId().equalsIgnoreCase(emailId);
        return outcome;
    }

    private boolean checkForValues(IUser user) {
        log.info("Checking for invalid user data");
        boolean outcome = true;
        if (user.getFirstName().isEmpty() || user.getLastName().isEmpty() || user.getEmailId().isEmpty()
                || user.getPassword().isEmpty() || user.getConfirmPassword().isEmpty()) {
            outcome = false;
        }
        return outcome;
    }
}
