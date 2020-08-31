package CSCI5308.GroupFormationTool.Security;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class BCryptEncryptionTest implements IPasswordEncryptor {

    @Override
    public String encoder(String rawPassword) {
        return "encrypted";
    }

    @Override
    public boolean passwordMatch(String rawPassword, String encryptedPassword) {
        if (null == rawPassword || rawPassword.isEmpty()) {
            return false;
        }
        if (null == encryptedPassword || encryptedPassword.isEmpty()) {
            return false;
        }
        return encryptedPassword.equals("encrypted");
    }

    @Test
    void encryptPasswordTest() {
        String password = "padmesh1234";
        String encryptedPassword = encoder(password);
        assertEquals(true, passwordMatch(password, encryptedPassword));
    }
}
