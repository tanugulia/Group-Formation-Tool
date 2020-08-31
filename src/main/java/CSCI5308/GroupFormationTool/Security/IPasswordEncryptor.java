package CSCI5308.GroupFormationTool.Security;

public interface IPasswordEncryptor {

    String encoder(String password);

    boolean passwordMatch(String password, String encryptedPassword);

}
