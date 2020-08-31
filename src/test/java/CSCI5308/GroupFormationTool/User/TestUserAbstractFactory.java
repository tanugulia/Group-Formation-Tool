package CSCI5308.GroupFormationTool.User;

import java.util.ArrayList;

import static org.mockito.Mockito.mock;

public class TestUserAbstractFactory implements ITestUserAbstractFactory {

    @Override
    public IUser createUserInstance() {
        return new User();
    }

    @Override
    public ArrayList<IUser> createUserListInstance() {
        return new ArrayList<IUser>();
    }

    @Override
    public UserDBMock createUserDBMock() {
        return new UserDBMock();
    }

    @Override
    public UserRepository createUserRepositoryMock() {
        return mock(UserRepository.class);
    }

    @Override
    public ArrayList<Long> createUserIdsList() {
        return new ArrayList<>();
    }
}
