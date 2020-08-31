package CSCI5308.GroupFormationTool.Password;

import CSCI5308.GroupFormationTool.User.IUser;

import java.util.ArrayList;

public interface IPasswordHistoryRepository {

    String getSettingValue(String settingName);

    boolean addPasswordHistory(IUser user, String password);

    ArrayList<String> getNPasswords(IUser user, String num);
}
