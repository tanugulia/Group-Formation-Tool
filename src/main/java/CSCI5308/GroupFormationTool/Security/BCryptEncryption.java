package CSCI5308.GroupFormationTool.Security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class BCryptEncryption implements IPasswordEncryptor {

    private static final Logger log = LoggerFactory.getLogger(BCryptEncryption.class.getName());

    private BCryptPasswordEncoder encode;

    public BCryptEncryption() {
        ISecurityAbstractFactory securityAbstractFactory = new SecurityAbstractFactory();
        this.encode = securityAbstractFactory.createBCryptPasswordEncoder();
    }

    @Override
    public String encoder(String password) {
        log.info("Encrypting the password of the user using the BCrypt Encryptor");
        return encode.encode(password);
    }

    @Override
    public boolean passwordMatch(String password, String encryptedPassword) {
        log.info("Checking if the password plain text matches with its encrypted one for authentication");
        return encode.matches(password, encryptedPassword);
    }
}
