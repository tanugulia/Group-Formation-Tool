package CSCI5308.GroupFormationTool.Question;

import CSCI5308.GroupFormationTool.Database.DatabaseInjector;
import CSCI5308.GroupFormationTool.Database.IDatabaseAbstractFactory;
import CSCI5308.GroupFormationTool.Database.StoredProcedure;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class QuestionAdminRepository implements IQuestionAdminRepository {

    private static final Logger log = LoggerFactory.getLogger(QuestionAdminRepository.class.getName());

    @Override
    public ArrayList<IQuestion> getQuestionListForInstructor(String emailId) {
        IQuestionAbstractFactory questionAbstractFactory = QuestionInjector.instance().getQuestionAbstractFactory();
        IDatabaseAbstractFactory databaseAbstractFactory = DatabaseInjector.instance().getDatabaseAbstractFactory();
        StoredProcedure storedProcedure = null;
        ArrayList<IQuestion> questionList = questionAbstractFactory.createQuestionListInstance();
        try {
            log.info("Calling stored procedure sp_getQuestionsForInstructor to fetch the question bank " +
                    "of the instructor by emailId " + emailId);
            storedProcedure = databaseAbstractFactory.createStoredProcedureInstance
                    ("sp_getQuestionsForInstructor(?)");
            storedProcedure.setInputStringParameter(1, emailId);
            ResultSet results = storedProcedure.executeWithResults();
            if (results != null) {
                while (results.next()) {
                    {
                        IQuestion question = questionAbstractFactory.createQuestionInstance();
                        question.setId(results.getLong("question_id"));
                        question.setText(results.getString("question_text"));
                        question.setType(results.getInt("qtype_id"));
                        question.setTitle(results.getString("title"));
                        question.setCreatedDate(results.getDate("created_date"));
                        questionList.add(question);
                    }
                }
            }
        } catch (SQLException exception) {
            log.error("Could not execute the Stored procedure sp_getQuestionsForInstructor" +
                    " because of an SQL Exception " + exception.getLocalizedMessage());
        } finally {
            if (storedProcedure != null) {
                storedProcedure.removeConnections();
            }
        }
        return questionList;
    }

    @Override
    public IQuestion getQuestionById(long questionId) {
        StoredProcedure storedProcedure = null;
        IQuestionAbstractFactory questionAbstractFactory = QuestionInjector.instance().getQuestionAbstractFactory();
        IDatabaseAbstractFactory databaseAbstractFactory = DatabaseInjector.instance().getDatabaseAbstractFactory();
        IQuestion question = questionAbstractFactory.createQuestionInstance();
        try {
            log.info("Calling the stored procedure sp_getQuestionById to fetch a particular question by its Id " + questionId);
            storedProcedure = databaseAbstractFactory.createStoredProcedureInstance("sp_getQuestionById(?)");
            storedProcedure.setInputIntParameter(1, questionId);
            ResultSet results = storedProcedure.executeWithResults();
            if (results != null) {
                while (results.next()) {
                    {
                        question.setId(results.getLong("question_id"));
                        question.setText(results.getString("question_text"));
                        question.setType(results.getInt("qtype_id"));
                        question.setTitle(results.getString("title"));
                        question.setCreatedDate(results.getDate("created_date"));
                    }
                }
            }

        } catch (SQLException exception) {
            log.error("Could not execute the Stored procedure sp_getQuestionById" +
                    " because of an SQL Exception " + exception.getLocalizedMessage());
        } finally {
            if (storedProcedure != null) {
                storedProcedure.removeConnections();
            }
        }
        return question;
    }

    @Override
    public ArrayList<IChoice> getOptionsForTheQuestion(long questionId) {
        StoredProcedure storedProcedure = null;
        IQuestionAbstractFactory questionAbstractFactory = QuestionInjector.instance().getQuestionAbstractFactory();
        IDatabaseAbstractFactory databaseAbstractFactory = DatabaseInjector.instance().getDatabaseAbstractFactory();
        ArrayList<IChoice> choiceList = questionAbstractFactory.createChoiceListInstance();
        try {
            log.info("Calling the stored procedure sp_getOptionsForQuestion to fetch" +
                    " the option list for a multiple choice question by question id " + questionId);
            storedProcedure = databaseAbstractFactory.createStoredProcedureInstance
                    ("sp_getOptionsForQuestion(?)");
            storedProcedure.setInputIntParameter(1, questionId);
            ResultSet results = storedProcedure.executeWithResults();
            if (results != null) {
                while (results.next()) {
                    {
                        IChoice choice = questionAbstractFactory.createChoiceInstance();
                        choice.setText(results.getString("options_text"));
                        choice.setValue(results.getInt("options_value"));
                        choiceList.add(choice);
                    }
                }
            }
        } catch (SQLException exception) {
            log.error("Could not execute the Stored procedure sp_getOptionsForQuestion" +
                    " because of an SQL Exception " + exception.getLocalizedMessage());
        } finally {
            if (storedProcedure != null) {
                storedProcedure.removeConnections();
            }
        }
        return choiceList;
    }


    @Override
    public ArrayList<IQuestion> getSortedQuestionListForInstructor(String emailId, String sortField) {
        StoredProcedure storedProcedure = null;
        IQuestionAbstractFactory questionAbstractFactory = QuestionInjector.instance().getQuestionAbstractFactory();
        IDatabaseAbstractFactory databaseAbstractFactory = DatabaseInjector.instance().getDatabaseAbstractFactory();
        ArrayList<IQuestion> questionList = questionAbstractFactory.createQuestionListInstance();
        try {
            log.info("Calling stored procedure sp_getSortedQuestionsForInstructor to fetch the question bank " +
                    "of the instructor sorted by " + sortField);
            storedProcedure = databaseAbstractFactory.createStoredProcedureInstance
                    ("sp_getSortedQuestionsForInstructor(?,?)");
            storedProcedure.setInputStringParameter(1, emailId);
            storedProcedure.setInputStringParameter(2, sortField);
            ResultSet results = storedProcedure.executeWithResults();
            if (results != null) {
                while (results.next()) {
                    {
                        IQuestion question = questionAbstractFactory.createQuestionInstance();
                        question.setId(results.getLong("question_id"));
                        question.setText(results.getString("question_text"));
                        question.setType(results.getInt("qtype_id"));
                        question.setTitle(results.getString("title"));
                        question.setCreatedDate(results.getDate("created_date"));
                        questionList.add(question);
                    }
                }
            }
        } catch (SQLException exception) {
            log.error("Could not execute the Stored procedure sp_getSortedQuestionsForInstructor" +
                    " because of an SQL Exception " + exception.getLocalizedMessage());
        } finally {
            if (storedProcedure != null) {
                storedProcedure.removeConnections();
            }
        }
        return questionList;
    }
}
