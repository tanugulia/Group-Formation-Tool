package CSCI5308.GroupFormationTool.Password;

import CSCI5308.GroupFormationTool.Database.DatabaseInjector;
import CSCI5308.GroupFormationTool.Database.IDatabaseAbstractFactory;
import CSCI5308.GroupFormationTool.Database.StoredProcedure;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class PolicyRepository implements IPolicyRepository {

    private static final Logger log = LoggerFactory.getLogger(PolicyRepository.class.getName());

    @Override
    public ArrayList<IPolicy> passwordSPolicyCheck(String password) {
        IDatabaseAbstractFactory databaseAbstractFactory = DatabaseInjector.instance().getDatabaseAbstractFactory();
        IPasswordAbstractFactory passwordAbstractFactory = PasswordInjector.instance().getPasswordAbstractFactory();
        ArrayList<IPolicy> policies = passwordAbstractFactory.createPolicyListInstance();
        StoredProcedure storedProcedure = null;
        IPolicy policy = null;
        try {
            log.info("Calling stored procedure sp_getPasswordConfigSettings to fetch the policies from database");
            storedProcedure = databaseAbstractFactory.createStoredProcedureInstance
                    ("sp_getPasswordConfigSettings");
            ResultSet results = storedProcedure.executeWithResults();
            if (results != null) {
                while (results.next()) {
                    {
                        policy = passwordAbstractFactory.createPolicyInstance();
                        policy.setId(results.getInt("pSetting_id"));
                        policy.setSetting(results.getString("pSetting"));
                        policy.setValue(results.getString("pSetting_value"));
                        policy.setEnabled(results.getInt("is_enabled"));
                        policies.add(policy);
                    }
                }
            }
        } catch (SQLException exception) {
            log.error("Could not execute the Stored procedure sp_getPasswordConfigSettings" +
                    " because of an SQL Exception " + exception.getLocalizedMessage());
        } finally {
            if (storedProcedure != null) {
                storedProcedure.removeConnections();
            }
        }
        return policies;
    }

    @Override
    public ArrayList<IPolicy> getPolicies() {
        IDatabaseAbstractFactory databaseAbstractFactory = DatabaseInjector.instance().getDatabaseAbstractFactory();
        IPasswordAbstractFactory passwordAbstractFactory = PasswordInjector.instance().getPasswordAbstractFactory();
        ArrayList<IPolicy> policies = passwordAbstractFactory.createPolicyListInstance();
        StoredProcedure storedProcedure = null;
        IPolicy policy = null;
        try {
            log.info("Calling stored procedure sp_getPolicies to fetch all the policies from database");
            storedProcedure = databaseAbstractFactory.createStoredProcedureInstance("sp_getPolicies");
            ResultSet results = storedProcedure.executeWithResults();
            if (results != null) {
                while (results.next()) {
                    {
                        policy = passwordAbstractFactory.createPolicyInstance();
                        policy.setId(results.getInt("pSetting_id"));
                        policy.setSetting(results.getString("pSetting"));
                        policy.setValue(results.getString("pSetting_value"));
                        policies.add(policy);
                    }
                }
            }
        } catch (SQLException exception) {
            log.error("Could not execute the Stored procedure sp_getPolicies" +
                    " because of an SQL Exception " + exception.getLocalizedMessage());
        } finally {
            if (storedProcedure != null) {
                storedProcedure.removeConnections();
            }
        }
        return policies;
    }
}
