package CSCI5308.GroupFormationTool.Database;

import java.sql.SQLException;

public interface IDatabaseAbstractFactory {

    StoredProcedure createStoredProcedureInstance(String name) throws SQLException;

    IDBConfiguration createDBConfigurationInstance();
}
