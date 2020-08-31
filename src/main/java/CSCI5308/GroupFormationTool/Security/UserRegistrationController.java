package CSCI5308.GroupFormationTool.Security;

import CSCI5308.GroupFormationTool.Common.DomainConstants;
import CSCI5308.GroupFormationTool.Password.IPasswordAbstractFactory;
import CSCI5308.GroupFormationTool.Password.IPolicy;
import CSCI5308.GroupFormationTool.Password.PasswordInjector;
import CSCI5308.GroupFormationTool.User.IUser;
import CSCI5308.GroupFormationTool.User.IUserAbstractFactory;
import CSCI5308.GroupFormationTool.User.User;
import CSCI5308.GroupFormationTool.User.UserInjector;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.ArrayList;

@Controller
public class UserRegistrationController implements WebMvcConfigurer {

    private static final Logger log = LoggerFactory.getLogger(UserRegistrationController.class.getName());

    @PostMapping("/register")
    public ModelAndView createUser(User user) {
        IUserAbstractFactory userAbstractFactory = UserInjector.instance().getUserAbstractFactory();
        IUser userInstance = userAbstractFactory.createUserInstance();
        ModelAndView modelAndView = null;
        String errorMessage = null;
        log.info("Creating the user and saving the new user to the database");
        errorMessage = userInstance.createUser(user);
        if (null == errorMessage) {
            log.info("Redirecting to user login page after successful registration");
            modelAndView = new ModelAndView("user/login");
        } else {
            if (errorMessage.equals(DomainConstants.signupInvalidDetails)) {
                log.warn("One or more fields are not filled by the user during registration!");
                modelAndView = new ModelAndView("user/signup");
                modelAndView.addObject("invalidDetails", errorMessage);
            } else if (errorMessage.equals(DomainConstants.userAlreadyExists
                    .replace("[[emailId]]", user.getEmailId()))) {
                log.error("The user trying to register already exists!");
                modelAndView = new ModelAndView("user/signup");
                modelAndView.addObject("userAlreadyExists", errorMessage);
            } else {
                log.warn("The password entered does not satisfy the password policies!");
                modelAndView = new ModelAndView("user/signup");
                modelAndView.addObject("passwordError", errorMessage);
            }
        }
        return modelAndView;
    }

    @GetMapping("/register")
    public String register(User user, Model model) {
        IPasswordAbstractFactory passwordAbstractFactory = PasswordInjector.instance().getPasswordAbstractFactory();
        IPolicy policyInstance = passwordAbstractFactory.createPolicyInstance();
        log.info("Fetching the password policies of the system");
        ArrayList<IPolicy> policies = policyInstance.getPolicies();
        model.addAttribute("policies", policies);
        return "user/signup";
    }
}
