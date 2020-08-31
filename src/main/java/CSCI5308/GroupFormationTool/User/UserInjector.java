package CSCI5308.GroupFormationTool.User;

public class UserInjector {

    private static UserInjector instance = null;

    private IUserAbstractFactory userAbstractFactory;

    private IUserRepository userRepository;

    private UserInjector() {
        userAbstractFactory = new UserAbstractFactory();
        userRepository = userAbstractFactory.createUserRepositoryInstance();
    }

    public static UserInjector instance() {
        if (instance == null) {
            instance = new UserInjector();
        }
        return instance;
    }

    public IUserAbstractFactory getUserAbstractFactory() {
        return userAbstractFactory;
    }

    public void setUserAbstractFactory(IUserAbstractFactory userAbstractFactory) {
        this.userAbstractFactory = userAbstractFactory;
    }

    public IUserRepository getUserRepository() {
        return userRepository;
    }

    public void setUserRepository(IUserRepository userRepository) {
        this.userRepository = userRepository;
    }

}
