package CSCI5308.GroupFormationTool.Database;

import java.sql.SQLException;

public class DatabaseAbstractFactory implements IDatabaseAbstractFactory {

    @Override
    public StoredProcedure createStoredProcedureInstance(String name) throws SQLException {
        return new StoredProcedure(name);
    }

    @Override
    public IDBConfiguration createDBConfigurationInstance() {
        return new DBConfiguration();
    }
}
