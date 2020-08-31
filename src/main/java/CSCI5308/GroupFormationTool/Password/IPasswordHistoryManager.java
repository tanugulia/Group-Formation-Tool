package CSCI5308.GroupFormationTool.Password;

import CSCI5308.GroupFormationTool.User.IUser;

public interface IPasswordHistoryManager {

    boolean isHistoryViolated(IUser user, String enteredPassword);

    void addPasswordHistory(IUser user, String encrypted_password);

    String getSettingValue(String settingName);
}
