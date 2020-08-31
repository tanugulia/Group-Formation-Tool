package CSCI5308.GroupFormationTool.Database;

public class DatabaseInjector {

    private static DatabaseInjector instance = null;

    private IDatabaseAbstractFactory databaseAbstractFactory;

    private IDBConfiguration dbConfiguration;

    private DatabaseInjector() {
        databaseAbstractFactory = new DatabaseAbstractFactory();
        dbConfiguration = databaseAbstractFactory.createDBConfigurationInstance();
    }

    public static DatabaseInjector instance() {
        if (instance == null) {
            instance = new DatabaseInjector();
        }
        return instance;
    }

    public IDatabaseAbstractFactory getDatabaseAbstractFactory() {
        return databaseAbstractFactory;
    }

    public void setDatabaseAbstractFactory(IDatabaseAbstractFactory databaseAbstractFactory) {
        this.databaseAbstractFactory = databaseAbstractFactory;
    }

    public IDBConfiguration getDbConfiguration() {
        return dbConfiguration;
    }

}
