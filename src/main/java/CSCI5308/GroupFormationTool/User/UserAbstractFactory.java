package CSCI5308.GroupFormationTool.User;

import java.util.ArrayList;

public class UserAbstractFactory implements IUserAbstractFactory {

    @Override
    public IUser createUserInstance() {
        return new User();
    }

    @Override
    public ArrayList<IUser> createUserListInstance() {
        return new ArrayList<IUser>();
    }

    @Override
    public IUserRepository createUserRepositoryInstance() {
        return new UserRepository();
    }

    @Override
    public ArrayList<Long> createUserIdList() {
        return new ArrayList<Long>();
    }
}
