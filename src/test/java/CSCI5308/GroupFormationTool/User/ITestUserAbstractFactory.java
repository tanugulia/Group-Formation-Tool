package CSCI5308.GroupFormationTool.User;

import java.util.ArrayList;

public interface ITestUserAbstractFactory {

    IUser createUserInstance();

    ArrayList<IUser> createUserListInstance();

    UserDBMock createUserDBMock();

    UserRepository createUserRepositoryMock();

    ArrayList<Long> createUserIdsList();

}

