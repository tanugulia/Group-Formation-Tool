package CSCI5308.GroupFormationTool.Password;

import CSCI5308.GroupFormationTool.User.IUser;

public interface IForgotPasswordManager {

    String notifyUser(IUser user);

    String updatePassword(IUser user, String token);
}
