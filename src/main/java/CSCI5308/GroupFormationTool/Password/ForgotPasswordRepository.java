package CSCI5308.GroupFormationTool.Password;

import CSCI5308.GroupFormationTool.Database.DatabaseInjector;
import CSCI5308.GroupFormationTool.Database.IDatabaseAbstractFactory;
import CSCI5308.GroupFormationTool.Database.StoredProcedure;
import CSCI5308.GroupFormationTool.User.IUser;
import CSCI5308.GroupFormationTool.User.IUserAbstractFactory;
import CSCI5308.GroupFormationTool.User.UserInjector;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ForgotPasswordRepository implements IForgotPasswordRepository {

    private static final Logger Log = LoggerFactory.getLogger(ForgotPasswordRepository.class.getName());

    @Override
    public boolean addToken(IUser user, String token) {
        IDatabaseAbstractFactory databaseAbstractFactory = DatabaseInjector.instance().getDatabaseAbstractFactory();
        boolean tokenAdded = false;
        StoredProcedure storedProcedure = null;
        try {
            storedProcedure = databaseAbstractFactory.createStoredProcedureInstance("sp_addToken(?,?,?)");
            storedProcedure.setInputStringParameter(1, Long.toString(user.getId()));
            storedProcedure.setInputStringParameter(2, user.getEmailId());
            storedProcedure.setInputStringParameter(3, token);
            storedProcedure.execute();
            tokenAdded = true;
        } catch (SQLException exception) {
            Log.error("Could not execute the Stored procedure sp_addToken" +
                    " because of an SQL Exception " + exception.getLocalizedMessage());
        } finally {
            if (storedProcedure != null) {
                storedProcedure.removeConnections();
            }
        }
        return tokenAdded;
    }

    @Override
    public String getToken(IUser user) {
        IDatabaseAbstractFactory databaseAbstractFactory = DatabaseInjector.instance().getDatabaseAbstractFactory();
        String token = "";
        StoredProcedure storedProcedure = null;
        try {
            storedProcedure = databaseAbstractFactory.createStoredProcedureInstance("sp_getTokenByEmailId(?)");
            storedProcedure.setInputStringParameter(1, user.getEmailId());
            ResultSet results = storedProcedure.executeWithResults();
            if (results != null) {
                while (results.next()) {
                    {
                        token = (results.getString("temporary_lik"));
                    }
                }
            }
        } catch (SQLException exception) {
            Log.error("Could not execute the Stored procedure sp_getTokenByEmailId" +
                    " because of an SQL Exception " + exception.getLocalizedMessage());
        } finally {
            if (storedProcedure != null) {
                storedProcedure.removeConnections();
            }
        }
        return token;
    }

    @Override
    public boolean updatePassword(IUser user, String password) {
        IDatabaseAbstractFactory databaseAbstractFactory = DatabaseInjector.instance().getDatabaseAbstractFactory();
        boolean passwordUpdated = false;
        StoredProcedure storedProcedure = null;
        try {
            storedProcedure = databaseAbstractFactory.createStoredProcedureInstance("sp_updatePassword(?,?)");
            storedProcedure.setInputStringParameter(1, user.getEmailId());
            storedProcedure.setInputStringParameter(2, password);
            storedProcedure.execute();
            passwordUpdated = true;
        } catch (SQLException exception) {
            Log.error("Could not execute the Stored procedure sp_updatePassword" +
                    " because of an SQL Exception " + exception.getLocalizedMessage());
        } finally {
            if (storedProcedure != null) {
                storedProcedure.removeConnections();
            }
        }
        return passwordUpdated;
    }

    @Override
    public boolean deleteToken(IUser user, String token) {
        IDatabaseAbstractFactory databaseAbstractFactory = DatabaseInjector.instance().getDatabaseAbstractFactory();
        boolean tokenDeleted = false;
        StoredProcedure storedProcedure = null;
        try {
            storedProcedure = databaseAbstractFactory.createStoredProcedureInstance("sp_deleteToken(?)");
            storedProcedure.setInputStringParameter(1, token);
            storedProcedure.execute();
            tokenDeleted = true;
        } catch (SQLException exception) {
            Log.error("Could not execute the Stored procedure sp_deleteToken" +
                    " because of an SQL Exception " + exception.getLocalizedMessage());
        } finally {
            if (storedProcedure != null) {
                storedProcedure.removeConnections();
            }
        }
        return tokenDeleted;
    }

    @Override
    public boolean updateToken(IUser user, String token) {
        IDatabaseAbstractFactory databaseAbstractFactory = DatabaseInjector.instance().getDatabaseAbstractFactory();
        boolean tokenUpdated = false;
        StoredProcedure storedProcedure = null;
        try {
            storedProcedure = databaseAbstractFactory.createStoredProcedureInstance("sp_updateToken(?,?)");
            storedProcedure.setInputStringParameter(1, user.getEmailId());
            storedProcedure.setInputStringParameter(2, token);
            storedProcedure.execute();
            tokenUpdated = true;
        } catch (SQLException exception) {
            Log.error("Could not execute the Stored procedure sp_updateToken" +
                    " because of an SQL Exception " + exception.getLocalizedMessage());
        } finally {
            if (storedProcedure != null) {
                storedProcedure.removeConnections();
            }
        }
        return tokenUpdated;
    }

    @Override
    public IUser getUserId(IUser user) {
        IDatabaseAbstractFactory databaseAbstractFactory = DatabaseInjector.instance().getDatabaseAbstractFactory();
        IUserAbstractFactory userAbstractFactory = UserInjector.instance().getUserAbstractFactory();
        IUser userByEmailId = null;
        StoredProcedure storedProcedure = null;
        try {
            storedProcedure = databaseAbstractFactory.createStoredProcedureInstance("sp_getUserId(?)");
            storedProcedure.setInputStringParameter(1, user.getEmailId());
            ResultSet results = storedProcedure.executeWithResults();
            if (results != null) {
                while (results.next()) {
                    {
                        userByEmailId = userAbstractFactory.createUserInstance();
                        userByEmailId.setId(Long.parseLong(results.getString("user_id")));
                        userByEmailId.setBannerId(results.getString("banner_id"));
                        userByEmailId.setEmailId(results.getString("email"));
                        userByEmailId.setFirstName(results.getString("first_name"));
                        userByEmailId.setLastName(results.getString("last_name"));
                        userByEmailId.setPassword(results.getString("password"));
                    }
                }
            }
        } catch (SQLException exception) {
            Log.error("Could not execute the Stored procedure sp_getUserId" +
                    " because of an SQL Exception " + exception.getLocalizedMessage());
        } finally {
            if (storedProcedure != null) {
                storedProcedure.removeConnections();
            }
        }
        return userByEmailId;
    }

    @Override
    public IUser getEmailByToken(IUser user, String token) {
        IUser userByEmailId = null;
        StoredProcedure storedProcedure = null;
        IDatabaseAbstractFactory databaseAbstractFactory = DatabaseInjector.instance().getDatabaseAbstractFactory();
        IUserAbstractFactory userAbstractFactory = UserInjector.instance().getUserAbstractFactory();
        try {
            storedProcedure = databaseAbstractFactory.createStoredProcedureInstance("sp_getEmailByToken(?)");
            storedProcedure.setInputStringParameter(1, token);
            ResultSet results = storedProcedure.executeWithResults();
            if (results != null) {
                while (results.next()) {
                    {
                        userByEmailId = userAbstractFactory.createUserInstance();
                        userByEmailId.setId(Long.parseLong(results.getString("user_id")));
                        userByEmailId.setEmailId(results.getString("email"));
                    }
                }
            }
        } catch (SQLException exception) {
            Log.error("Could not execute the Stored procedure sp_getEmailByToken" +
                    " because of an SQL Exception " + exception.getLocalizedMessage());
        } finally {
            if (storedProcedure != null) {
                storedProcedure.removeConnections();
            }
        }
        return userByEmailId;
    }
}
