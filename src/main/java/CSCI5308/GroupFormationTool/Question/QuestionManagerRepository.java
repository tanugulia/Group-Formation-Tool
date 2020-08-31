package CSCI5308.GroupFormationTool.Question;

import CSCI5308.GroupFormationTool.Database.DatabaseInjector;
import CSCI5308.GroupFormationTool.Database.IDatabaseAbstractFactory;
import CSCI5308.GroupFormationTool.Database.StoredProcedure;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.SQLException;

public class QuestionManagerRepository implements IQuestionManagerRepository {

    private static final Logger log = LoggerFactory.getLogger(QuestionManagerRepository.class.getName());

    @Override
    public long createQuestion(IQuestion question) {
        IDatabaseAbstractFactory databaseAbstractFactory = DatabaseInjector.instance().getDatabaseAbstractFactory();
        StoredProcedure storedProcedure = null;
        long questionId = -1;
        try {
            log.info("Calling stored procedure sp_createQuestion to save a question " +
                    "created by the instructor to the database");
            storedProcedure = databaseAbstractFactory.createStoredProcedureInstance
                    ("sp_createQuestion(?,?,?,?,?)");
            storedProcedure.setInputStringParameter(1, question.getTitle());
            storedProcedure.setInputStringParameter(2, question.getText());
            storedProcedure.setInputStringParameter(3, question.getInstructor().getEmailId());
            storedProcedure.setInputIntParameter(4, question.getType());
            storedProcedure.registerOutputParameterLong(5);
            storedProcedure.execute();
            questionId = storedProcedure.getParameterLong(5);
        } catch (SQLException ex) {
            log.error("Could not execute the Stored procedure sp_createQuestion" +
                    " because of an SQL Exception " + ex.getLocalizedMessage());
            return -1;
        } finally {
            if (storedProcedure != null) {
                storedProcedure.removeConnections();
            }
        }
        if (question.getChoices() != null) {
            for (IChoice choice : question.getChoices()) {
                if (saveChoice(choice, questionId) == false) {
                    return -1;
                }
            }
        }
        return questionId;
    }

    private boolean saveChoice(IChoice choice, long questionId) {
        IDatabaseAbstractFactory databaseAbstractFactory = DatabaseInjector.instance().getDatabaseAbstractFactory();
        StoredProcedure storedProcedure = null;
        try {
            log.info("Calling stored procedure sp_saveOptions to save the options of a question " +
                    "created by the instructor to the database");
            storedProcedure = databaseAbstractFactory.createStoredProcedureInstance("sp_saveOptions(?,?,?)");
            storedProcedure.setInputStringParameter(1, choice.getText());
            storedProcedure.setInputIntParameter(2, choice.getValue());
            storedProcedure.setInputIntParameter(3, questionId);
            storedProcedure.execute();
        } catch (SQLException ex) {
            log.error("Could not execute the Stored procedure sp_saveOptions" +
                    " because of an SQL Exception " + ex.getLocalizedMessage());
            return false;
        } finally {
            if (storedProcedure != null) {
                storedProcedure.removeConnections();
            }
        }
        return true;
    }

    public boolean deleteQuestion(long questionId) {
        IDatabaseAbstractFactory databaseAbstractFactory = DatabaseInjector.instance().getDatabaseAbstractFactory();
        StoredProcedure storedProcedure = null;
        boolean status = true;
        try {
            log.info("Calling stored procedure sp_deleteAQuestion to delete a question " +
                    "of the instructor by question id " + questionId);
            storedProcedure = databaseAbstractFactory.createStoredProcedureInstance("sp_deleteAQuestion(?,?)");
            storedProcedure.setInputIntParameter(1, questionId);
            storedProcedure.registerOutputParameterBoolean(2);
            storedProcedure.execute();
            status = storedProcedure.getParameter(2);
        } catch (SQLException ex) {
            log.error("Could not execute the Stored procedure sp_deleteAQuestion" +
                    " because of an SQL Exception " + ex.getLocalizedMessage());
        } finally {
            if (storedProcedure != null) {
                storedProcedure.removeConnections();
            }
        }
        return status;
    }

}
