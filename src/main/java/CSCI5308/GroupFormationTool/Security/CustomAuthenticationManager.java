package CSCI5308.GroupFormationTool.Security;

import CSCI5308.GroupFormationTool.Common.DomainConstants;
import CSCI5308.GroupFormationTool.User.IUser;
import CSCI5308.GroupFormationTool.User.IUserAbstractFactory;
import CSCI5308.GroupFormationTool.User.IUserRepository;
import CSCI5308.GroupFormationTool.User.UserInjector;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;

import java.util.List;

public class CustomAuthenticationManager implements AuthenticationManager {

    private static final String Admin_banner_id = "B00000000";

    private static final Logger log = LoggerFactory.getLogger(CustomAuthenticationManager.class.getName());

    private Authentication checkUser(String password, IUser user, Authentication authentication)
            throws AuthenticationException {
        IPasswordEncryptor passwordEncryptor = SecurityInjector.instance().getPasswordEncryptor();
        ISecurityAbstractFactory securityAbstractFactory = SecurityInjector.instance().getSecurityAbstractFactory();
        if (passwordEncryptor.passwordMatch(password, user.getPassword())) {
            List<GrantedAuthority> rights = securityAbstractFactory.createGrantedAuthorityListInstance();
            log.info("Assigning rights to the user based on the type");
            if (user.getBannerId().toUpperCase().equals(Admin_banner_id)) {
                rights.add(securityAbstractFactory.createSimpleGrantedAuthority(DomainConstants.AdminRole));
            } else {
                rights.add(securityAbstractFactory.createSimpleGrantedAuthority(DomainConstants.UserRole));
            }
            UsernamePasswordAuthenticationToken token;
            token = securityAbstractFactory.createUsernamePasswordAuthenticationToken(authentication.getPrincipal(),
                    authentication.getCredentials(), rights);
            return token;
        } else {
            log.error("The user credentials do not match the database stored values!");
            throw securityAbstractFactory.createBadCredentialsExceptionInstance("1000");
        }
    }

    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        ISecurityAbstractFactory securityAbstractFactory = SecurityInjector.instance().getSecurityAbstractFactory();
        IUserAbstractFactory userAbstractFactory = UserInjector.instance().getUserAbstractFactory();
        String emailId = authentication.getPrincipal().toString();
        String password = authentication.getCredentials().toString();
        IUserRepository userRepository = userAbstractFactory.createUserRepositoryInstance();
        IUser user = userAbstractFactory.createUserInstance();
        user.setEmailId(emailId);
        try {
            log.info("Fetching the user details by email id");
            user = userRepository.getUserByEmailId(user);
        } catch (Exception e) {
            log.error("The user with the email id mentioned does not exist!");
            throw securityAbstractFactory.createAuthenticationServiceExceptionInstance("1000");
        }
        if (user != null) {
            log.info("Checking if the user's password is correct and assigning rights");
            return checkUser(password, user, authentication);
        } else {
            log.error("The user credentials do not match the database stored values!");
            throw securityAbstractFactory.createBadCredentialsExceptionInstance("1001");
        }
    }
}
