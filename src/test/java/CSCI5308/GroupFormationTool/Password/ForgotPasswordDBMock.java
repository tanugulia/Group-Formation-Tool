package CSCI5308.GroupFormationTool.Password;

import CSCI5308.GroupFormationTool.User.IUser;

public class ForgotPasswordDBMock implements IForgotPasswordRepository {

    @Override
    public boolean addToken(IUser user, String token) {
        return user != null && !token.equals("") && !token.equals(null);
    }

    @Override
    public String getToken(IUser user) {
        if (user == null) {
            return null;
        } else {
            return "token";
        }
    }

    @Override
    public boolean updateToken(IUser user, String token) {
        return user != null && !token.equals("") && !token.equals(null);
    }

    @Override
    public boolean updatePassword(IUser user, String password) {
        if (user == null || password.equals("") || password.equals(null)) {
            user.setPassword(password);
            return false;
        } else {
            return true;
        }
    }

    @Override
    public boolean deleteToken(IUser user, String token) {
        return user != null && !token.equals("") && !token.equals(null);
    }

    @Override
    public IUser getUserId(IUser user) {
        if (user != null && user.getId() == 123) {
            return user;
        } else {
            return null;
        }
    }

    @Override
    public IUser getEmailByToken(IUser user, String token) {
        if (user != null && user.getEmailId().equalsIgnoreCase("haard.shah@dal.ca")) {
            return user;
        } else {
            return null;
        }
    }

}
