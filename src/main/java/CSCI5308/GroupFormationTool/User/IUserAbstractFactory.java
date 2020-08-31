package CSCI5308.GroupFormationTool.User;

import java.util.ArrayList;

public interface IUserAbstractFactory {

    IUser createUserInstance();

    ArrayList<IUser> createUserListInstance();

    IUserRepository createUserRepositoryInstance();

    ArrayList<Long> createUserIdList();
}
