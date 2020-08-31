package CSCI5308.GroupFormationTool.Password;

import CSCI5308.GroupFormationTool.Common.DomainConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;

public class Policy implements IPolicy {

    private static final Logger log = LoggerFactory.getLogger(Policy.class.getName());

    private int id;

    private String setting;

    private String value;

    private int enabled;

    private IPolicyRepository policyRepository;

    @Override
    public int getId() {
        return id;
    }

    @Override
    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String getSetting() {
        return setting;
    }

    @Override
    public void setSetting(String setting) {
        this.setting = setting;
    }

    @Override
    public String getValue() {
        return value;
    }

    @Override
    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public int getEnabled() {
        return enabled;
    }

    @Override
    public void setEnabled(int enabled) {
        this.enabled = enabled;
    }


    @Override
    public String passwordSPolicyCheck(String password) {
        log.info("Calling the Policy Repository to check if the password follows enabled security policies");
        policyRepository = PasswordInjector.instance().getPolicyRepository();
        ArrayList<IPolicy> policies = policyRepository.passwordSPolicyCheck(password);
        return checkPasswordSecurity(password, policies);
    }

    @Override
    public ArrayList<IPolicy> getPolicies() {
        log.info("Calling the Policy Repository to fetch all the password security policies from database");
        policyRepository = PasswordInjector.instance().getPolicyRepository();
        ArrayList<IPolicy> policies = policyRepository.getPolicies();
        return policies;
    }

    private String checkPasswordSecurity(String password, ArrayList<IPolicy> policies) {
        log.info("Checking if the password follows enabled security policies");
        int passwordSettingEnabled = 1;
        String errorMessage = null;
        int upperCaseCharacters = 0;
        int lowerCaseCharacters = 0;
        int specialCharacters = 0;
        for (int index = 0; index < password.length(); index++) {
            char character = password.charAt(index);
            if (character >= 'A' && character <= 'Z') {
                upperCaseCharacters++;
            } else if (character >= 'a' && character <= 'z') {
                lowerCaseCharacters++;
            } else if ((character >= '0' && character <= '9') == false) {
                specialCharacters++;
            }
        }
        for (int counter = 0; counter < policies.size(); counter++) {
            IPolicy policy = policies.get(counter);
            if (errorMessage != null) {
                break;
            }
            int id = policy.getId();
            String val = policy.getValue();
            int enabled = policy.getEnabled();
            if (enabled == passwordSettingEnabled) {
                switch (id) {
                    case 0:
                        if (password.length() < Integer.parseInt(val)) {
                            log.error("Minimum password length security policy is violated");
                            errorMessage = DomainConstants.passwordMinimumLength + val;
                        }
                        break;
                    case 1:
                        if (password.length() > Integer.parseInt(val)) {
                            log.error("Maximum password length security policy is violated");
                            errorMessage = DomainConstants.passwordMaximumLength + val;
                        }
                        break;
                    case 2:
                        if (upperCaseCharacters < Integer.parseInt(val)) {
                            log.error("Minimum upper case characters required password security policy is violated");
                            errorMessage = DomainConstants.passwordUppercaseMinimumLength + val;
                        }
                        break;
                    case 3:
                        if (lowerCaseCharacters < Integer.parseInt(val)) {
                            log.error("Minimum lower case characters required password security policy is violated");
                            errorMessage = DomainConstants.passwordLowercaseMinimumLength + val;
                        }
                        break;
                    case 4:
                        if (specialCharacters < Integer.parseInt(val)) {
                            log.error("Minimum special symbols required password security policy is violated");
                            errorMessage = DomainConstants.passwordSpecialSymbolsMinimumLength + val;
                        }
                        break;
                    case 5:
                        for (int i = 0; i < val.length(); i++) {
                            if (password != null && password.indexOf(val.charAt(i)) >= 0) {
                                log.error("Characters not allowed in password security policy is violated");
                                errorMessage = "" + val + DomainConstants.passwordCharactersNotAllowed;
                                break;
                            }
                        }
                }
            }
        }
        return errorMessage;
    }

}
