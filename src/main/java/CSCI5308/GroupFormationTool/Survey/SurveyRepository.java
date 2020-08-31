package CSCI5308.GroupFormationTool.Survey;

import CSCI5308.GroupFormationTool.Common.DomainConstants;
import CSCI5308.GroupFormationTool.Database.DatabaseInjector;
import CSCI5308.GroupFormationTool.Database.IDatabaseAbstractFactory;
import CSCI5308.GroupFormationTool.Database.StoredProcedure;
import CSCI5308.GroupFormationTool.Question.*;
import CSCI5308.GroupFormationTool.User.IUserAbstractFactory;
import CSCI5308.GroupFormationTool.User.UserInjector;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class SurveyRepository implements ISurveyRepository {

    private static final Logger log = LoggerFactory.getLogger(SurveyRepository.class.getName());

    private IQuestionAdminRepository questionAdminRepository;

    @Override
    public boolean checkIfSurveyCreated(String courseId) {
        IDatabaseAbstractFactory databaseAbstractFactory = DatabaseInjector.instance().getDatabaseAbstractFactory();
        StoredProcedure storedProcedure = null;
        boolean status = false;
        try {
            log.info("Calling stored procedure sp_checkIfSurveyCreated to fetch the groups for " +
                    "the course with parameters " + courseId);
            storedProcedure = databaseAbstractFactory.createStoredProcedureInstance
                    ("sp_checkIfSurveyCreated(?,?)");
            storedProcedure.setInputStringParameter(1, courseId);
            storedProcedure.registerOutputParameterBoolean(2);
            storedProcedure.execute();
            status = storedProcedure.getParameter(2);
        } catch (SQLException exception) {
            log.error("Could not execute the Stored procedure sp_checkIfSurveyCreated" +
                    " because of an SQL Exception " + exception.getLocalizedMessage());
            return false;
        } finally {
            if (storedProcedure != null) {
                storedProcedure.removeConnections();
            }
        }
        return status;
    }

    @Override
    public String getSurveyId(String courseId) {
        String surveyId = null;
        IDatabaseAbstractFactory databaseAbstractFactory = DatabaseInjector.instance().getDatabaseAbstractFactory();
        StoredProcedure storedProcedure = null;
        try {
            log.info("Calling stored procedure sp_getSurveyId to fetch the surveyId for the course " + courseId);
            storedProcedure = databaseAbstractFactory.createStoredProcedureInstance
                    ("sp_getSurveyId(?)");
            storedProcedure.setInputStringParameter(1, courseId);
            ResultSet results = storedProcedure.executeWithResults();
            if (results != null) {
                while (results.next()) {
                    {
                        surveyId = (results.getString("survey_id"));
                    }
                }
            }
        } catch (SQLException exception) {
            log.error("Could not execute the Stored procedure sp_getSurveyId" +
                    " because of an SQL Exception " + exception.getLocalizedMessage());
        } finally {
            if (storedProcedure != null) {
                storedProcedure.removeConnections();
            }
        }
        return surveyId;
    }

    @Override
    public boolean deleteQuestionFromSurvey(long questionId, long surveyId) {
        StoredProcedure storedProcedure = null;
        IDatabaseAbstractFactory databaseAbstractFactory = DatabaseInjector.instance().getDatabaseAbstractFactory();
        try {
            log.info("Calling stored procedure sp_deleteSurveyQuestion to delete a " +
                    "survey question by passing  " + questionId + "and" + surveyId);
            storedProcedure = databaseAbstractFactory.createStoredProcedureInstance
                    ("sp_deleteSurveyQuestion(?,?)");
            storedProcedure.setInputIntParameter(1, surveyId);
            storedProcedure.setInputIntParameter(2, questionId);
            storedProcedure.execute();
        } catch (SQLException exception) {
            log.error("Could not execute the Stored procedure sp_deleteSurveyQuestion" +
                    " because of an SQL Exception " + exception.getLocalizedMessage());
            return false;
        } finally {
            if (storedProcedure != null) {
                storedProcedure.removeConnections();
            }
        }
        return true;
    }

    @Override
    public ArrayList<IQuestion> getSurveyQuestionListForInstructor(String emailId, int surveyId, String questionTitle) {
        IQuestionAbstractFactory questionAbstractFactory = QuestionInjector.instance().getQuestionAbstractFactory();
        IDatabaseAbstractFactory databaseAbstractFactory = DatabaseInjector.instance().getDatabaseAbstractFactory();
        StoredProcedure storedProcedure = null;
        ArrayList<IQuestion> questionList = questionAbstractFactory.createQuestionListInstance();
        try {
            log.info("Calling stored procedure sp_getSurveyQuestionsForInstructor to get a " +
                    "list of survey questions for the instructor by  " + emailId + " , " + surveyId +
                    " and " + questionTitle);
            storedProcedure = databaseAbstractFactory.createStoredProcedureInstance
                    ("sp_getSurveyQuestionsForInstructor(?,?,?)");
            storedProcedure.setInputStringParameter(1, emailId);
            storedProcedure.setInputIntParameter(2, surveyId);
            storedProcedure.setInputStringParameter(3, questionTitle);
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
            log.error("Could not execute the Stored procedure sp_getSurveyQuestionsForInstructor" +
                    " because of an SQL Exception " + exception.getLocalizedMessage());
        } finally {
            if (storedProcedure != null) {
                storedProcedure.removeConnections();
            }
        }
        return questionList;
    }

    @Override
    public ArrayList<IQuestion> getSurveyQuestionListForTA(ArrayList<Long> instructorIds, int surveyId, String questionTitle) {
        IQuestionAbstractFactory questionAbstractFactory = QuestionInjector.instance().getQuestionAbstractFactory();
        IDatabaseAbstractFactory databaseAbstractFactory = DatabaseInjector.instance().getDatabaseAbstractFactory();
        StoredProcedure storedProcedure = null;
        ArrayList<IQuestion> questionList = questionAbstractFactory.createQuestionListInstance();
        try {
            log.info("Calling stored procedure sp_getSurveyQuestionsForTA to get a list of survey " +
                    "questions for the TA for survey " + surveyId + " and question title " + questionTitle);
            storedProcedure = databaseAbstractFactory.createStoredProcedureInstance
                    ("sp_getSurveyQuestionsForTA(?,?,?)");
            storedProcedure.setInputStringParameter(1, StringUtils.join(instructorIds, ','));
            storedProcedure.setInputIntParameter(2, surveyId);
            storedProcedure.setInputStringParameter(3, questionTitle);
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
            log.error("Could not execute the Stored procedure sp_getSurveyQuestionsForTA" +
                    " because of an SQL Exception " + exception.getLocalizedMessage());
        } finally {
            if (storedProcedure != null) {
                storedProcedure.removeConnections();
            }
        }
        return questionList;
    }

    @Override
    public int createSurvey(String courseId) {
        StoredProcedure storedProcedure = null;
        int surveyId = DomainConstants.invalidSurveyId;
        IDatabaseAbstractFactory databaseAbstractFactory = DatabaseInjector.instance().getDatabaseAbstractFactory();
        try {
            log.info("create a survey for the course " + courseId);
            storedProcedure = databaseAbstractFactory.createStoredProcedureInstance
                    ("sp_createSurvey(?,?)");
            storedProcedure.setInputStringParameter(1, courseId);
            storedProcedure.registerOutputParameterLong(2);
            storedProcedure.execute();
            surveyId = (int) storedProcedure.getParameterLong(2);
        } catch (SQLException exception) {
            log.error("Could not execute the Stored procedure sp_createSurvey" +
                    " because of an SQL Exception " + exception.getLocalizedMessage());
            surveyId = DomainConstants.invalidSurveyId;
        } finally {
            if (storedProcedure != null) {
                storedProcedure.removeConnections();
            }
        }
        return surveyId;
    }

    @Override
    public boolean addQuestionToSurvey(long questionId, long surveyId) {
        StoredProcedure storedProcedure = null;
        IDatabaseAbstractFactory databaseAbstractFactory = DatabaseInjector.instance().getDatabaseAbstractFactory();
        boolean status = true;
        try {
            log.info("Calling stored procedure sp_addQuestionToSurvey Adding a question "
                    + questionId + " to the survey " + surveyId);
            storedProcedure = databaseAbstractFactory.createStoredProcedureInstance
                    ("sp_addQuestionToSurvey(?,?,?)");
            storedProcedure.setInputIntParameter(1, surveyId);
            storedProcedure.setInputIntParameter(2, questionId);
            storedProcedure.registerOutputParameterBoolean(3);
            storedProcedure.execute();
            status = storedProcedure.getParameter(3);
        } catch (SQLException exception) {
            log.error("Could not execute the Stored procedure sp_addQuestionToSurvey" +
                    " because of an SQL Exception " + exception.getLocalizedMessage());
            status = false;
        } finally {
            if (storedProcedure != null) {
                storedProcedure.removeConnections();
            }
        }
        return status;
    }

    @Override
    public boolean isSurveyPublished(String surveyId) {
        IDatabaseAbstractFactory databaseAbstractFactory = DatabaseInjector.instance().getDatabaseAbstractFactory();
        StoredProcedure storedProcedure = null;
        boolean published = false;
        try {
            log.info("Calling stored procedure sp_checkSurveyPublished to check if survey " + surveyId + " is published or not ");
            storedProcedure = databaseAbstractFactory.createStoredProcedureInstance
                    ("sp_checkSurveyPublished(?)");
            storedProcedure.setInputStringParameter(1, surveyId);
            ResultSet results = storedProcedure.executeWithResults();
            if (results.next()) {
                published = true;
            }
        } catch (SQLException exception) {
            log.error("Could not execute the Stored procedure sp_checkSurveyPublished" +
                    " because of an SQL Exception " + exception.getLocalizedMessage());
        } finally {
            if (storedProcedure != null) {
                storedProcedure.removeConnections();
            }
        }
        return published;
    }

    @Override
    public int getSurveyIdByCourseId(String courseId) {
        IDatabaseAbstractFactory databaseAbstractFactory = DatabaseInjector.instance().getDatabaseAbstractFactory();
        StoredProcedure storedProcedure = null;
        int status = DomainConstants.invalidSurveyId;
        try {
            log.info("Calling stored procedure sp_getSurveyId to fetch the groups for the course " + courseId);
            storedProcedure = databaseAbstractFactory.createStoredProcedureInstance
                    ("sp_getSurveyId(?)");
            storedProcedure.setInputStringParameter(1, courseId);
            ResultSet results = storedProcedure.executeWithResults();
            if (results != null) {
                while (results.next()) {
                    status = (int) results.getLong("survey_id");
                }
            }
        } catch (SQLException exception) {
            log.error("Could not execute the Stored procedure sp_getSurveyId" +
                    " because of an SQL Exception " + exception.getLocalizedMessage());
            status = (int) DomainConstants.sqlError;
        } finally {
            if (storedProcedure != null) {
                storedProcedure.removeConnections();
            }
        }
        return status;
    }

    @Override
    public boolean publishSurvey(String courseId) {
        IDatabaseAbstractFactory databaseAbstractFactory = DatabaseInjector.instance().getDatabaseAbstractFactory();
        StoredProcedure storedProcedure = null;
        boolean status = false;
        try {
            log.info("Calling stored procedure sp_publishSurvey to fetch the groups for the course " + courseId);
            storedProcedure = databaseAbstractFactory.createStoredProcedureInstance
                    ("sp_publishSurvey(?,?)");
            storedProcedure.setInputStringParameter(1, courseId);
            storedProcedure.registerOutputParameterBoolean(2);
            storedProcedure.execute();
            status = storedProcedure.getParameter(2);
        } catch (SQLException exception) {
            log.error("Could not execute the Stored procedure sp_publishSurvey" +
                    " because of an SQL Exception " + exception.getLocalizedMessage());
            status = false;
        } finally {
            if (storedProcedure != null) {
                storedProcedure.removeConnections();
            }
        }
        return status;
    }

    @Override
    public boolean checkIfSurveyHasFormula(String courseId) {
        IDatabaseAbstractFactory databaseAbstractFactory = DatabaseInjector.instance().getDatabaseAbstractFactory();
        StoredProcedure storedProcedure = null;
        boolean status = false;
        try {
            log.info("Calling stored procedure sp_checkIfSurveyHasFormula to fetch the groups for the course" + courseId);
            storedProcedure = databaseAbstractFactory.createStoredProcedureInstance
                    ("sp_checkIfSurveyHasFormula(?,?)");
            storedProcedure.setInputStringParameter(1, courseId);
            storedProcedure.registerOutputParameterBoolean(2);
            storedProcedure.execute();
            status = storedProcedure.getParameter(2);
        } catch (SQLException exception) {
            log.error("Could not execute the Stored procedure checkIfSurveyHasFormula" +
                    " because of an SQL Exception " + exception.getLocalizedMessage());
            status = false;
        } finally {
            if (storedProcedure != null) {
                storedProcedure.removeConnections();
            }
        }
        return status;
    }

    @Override
    public boolean checkIfSurveyPublished(String courseId) {
        IDatabaseAbstractFactory databaseAbstractFactory = DatabaseInjector.instance().getDatabaseAbstractFactory();
        StoredProcedure storedProcedure = null;
        boolean status = false;
        try {
            log.info("Calling stored procedure sp_checkIfSurveyPublished to fetch the groups for the course " + courseId);
            storedProcedure = databaseAbstractFactory.createStoredProcedureInstance
                    ("sp_checkIfSurveyPublished(?,?)");
            storedProcedure.setInputStringParameter(1, courseId);
            storedProcedure.registerOutputParameterBoolean(2);
            storedProcedure.execute();
            status = storedProcedure.getParameter(2);
        } catch (SQLException exception) {
            log.error("Could not execute the Stored procedure sp_checkIfSurveyPublished" +
                    " because of an SQL Exception " + exception.getLocalizedMessage());
            status = false;
        } finally {
            if (storedProcedure != null) {
                storedProcedure.removeConnections();
            }
        }
        return status;
    }

    @Override
    public boolean isSurveyCompleted(String surveyId, String userId) {
        IDatabaseAbstractFactory databaseAbstractFactory = DatabaseInjector.instance().getDatabaseAbstractFactory();
        StoredProcedure storedProcedure = null;
        boolean completed = false;
        try {
            log.info("Calling stored procedure sp_checkSurveyCompleted to check if survey "
                    + surveyId + "is completed or not by the user " + userId);
            storedProcedure = databaseAbstractFactory.createStoredProcedureInstance
                    ("sp_checkSurveyCompleted(?,?)");
            storedProcedure.setInputStringParameter(1, surveyId);
            storedProcedure.setInputStringParameter(2, userId);
            ResultSet results = storedProcedure.executeWithResults();
            if (results.next()) {
                completed = true;
            }
        } catch (SQLException exception) {
            log.error("Could not execute the Stored procedure sp_checkSurveyCompleted" +
                    " because of an SQL Exception " + exception.getLocalizedMessage());
        } finally {
            if (storedProcedure != null) {
                storedProcedure.removeConnections();
            }
        }
        return completed;
    }

    @Override
    public ArrayList<IQuestion> getSurveyQuestions(String surveyId) {
        IQuestionAbstractFactory questionAbstractFactory = QuestionInjector.instance().getQuestionAbstractFactory();
        IDatabaseAbstractFactory databaseAbstractFactory = DatabaseInjector.instance().getDatabaseAbstractFactory();
        ISurveyAbstractFactory surveyAbstractFactory = SurveyInjector.instance().getSurveyAbstractFactory();
        ArrayList<IQuestion> surveyQuestions = surveyAbstractFactory.createSurveyQuestionListInstance();
        StoredProcedure storedProcedure = null;
        try {
            log.info("Calling stored procedure sp_getSurveyQuestions to get questions for the survey " + surveyId);
            storedProcedure = databaseAbstractFactory.createStoredProcedureInstance
                    ("sp_getSurveyQuestions(?)");
            storedProcedure.setInputStringParameter(1, surveyId);
            ResultSet results = storedProcedure.executeWithResults();
            if (results != null) {
                while (results.next()) {
                    {
                        IQuestion question = questionAbstractFactory.createQuestionInstance();
                        question.setId(Long.parseLong(results.getString("question_id")));
                        question.setTitle(results.getString("title"));
                        question.setText(results.getString("question_text"));
                        question.setType(Integer.parseInt(results.getString("qtype_id")));
                        if (question.getType() == DomainConstants.MCQOne ||
                                question.getType() == DomainConstants.MCQMultiple) {
                            questionAdminRepository = QuestionInjector.instance().getQuestionAdminRepository();
                            ArrayList<IChoice> choices = questionAdminRepository.getOptionsForTheQuestion(question.getId());
                            question.setChoices(choices);
                        }
                        surveyQuestions.add(question);
                    }
                }
            }
        } catch (SQLException exception) {
            log.error("Could not execute the Stored procedure sp_getSurveyQuestions" +
                    " because of an SQL Exception " + exception.getLocalizedMessage());
        } finally {
            if (storedProcedure != null) {
                storedProcedure.removeConnections();
            }
        }

        return surveyQuestions;
    }

    @Override
    public ArrayList<IQuestion> getQuestionsForSurvey(String courseId) {
        StoredProcedure storedProcedure = null;
        IQuestionAbstractFactory questionAbstractFactory = QuestionInjector.instance().getQuestionAbstractFactory();
        IDatabaseAbstractFactory databaseAbstractFactory = DatabaseInjector.instance().getDatabaseAbstractFactory();
        ArrayList<IQuestion> questionList = questionAbstractFactory.createQuestionListInstance();
        try {
            log.info("Calling stored procedure sp_getSurveyQuestionsForCourse to get questions " +
                    "for the survey by course Id" + courseId);
            storedProcedure = databaseAbstractFactory.createStoredProcedureInstance
                    ("sp_getSurveyQuestionsForCourse(?)");
            storedProcedure.setInputStringParameter(1, courseId);
            ResultSet results = storedProcedure.executeWithResults();
            if (results != null) {
                while (results.next()) {
                    {
                        IQuestion question = questionAbstractFactory.createQuestionInstance();
                        question.setId(results.getLong("question_id"));
                        question.setText(results.getString("question_text"));
                        question.setType(results.getInt("qtype_id"));
                        question.setTitle(results.getString("title"));
                        questionList.add(question);
                        if (question.getType() == DomainConstants.MCQOne ||
                                question.getType() == DomainConstants.MCQMultiple) {
                            questionAdminRepository = QuestionInjector.instance().getQuestionAdminRepository();
                            ArrayList<IChoice> choices = questionAdminRepository.
                                    getOptionsForTheQuestion(question.getId());
                            question.setChoices(choices);
                        } else {
                            question.setChoices(null);
                        }
                    }
                }
            }
        } catch (SQLException exception) {
            log.error("Could not execute the Stored procedure sp_getSurveyQuestionsForCourse" +
                    " because of an SQL Exception " + exception.getLocalizedMessage());
        } finally {
            if (storedProcedure != null) {
                storedProcedure.removeConnections();
            }
        }
        return questionList;
    }

    @Override
    public ArrayList<Long> getUsersWhoTookSurvey(String courseId) {
        IDatabaseAbstractFactory databaseAbstractFactory = DatabaseInjector.instance().getDatabaseAbstractFactory();
        StoredProcedure storedProcedure = null;
        IUserAbstractFactory userAbstractFactory = UserInjector.instance().getUserAbstractFactory();
        ArrayList<Long> userIdList = userAbstractFactory.createUserIdList();
        try {
            storedProcedure = databaseAbstractFactory.createStoredProcedureInstance
                    ("sp_getUsersWhoTookSurvey(?)");
            storedProcedure.setInputStringParameter(1, courseId);
            ResultSet results = storedProcedure.executeWithResults();
            if (results != null) {
                while (results.next()) {
                    {
                        userIdList.add(results.getLong("user_id"));
                    }
                }
            }
        } catch (SQLException exception) {
            log.error("Could not execute the Stored procedure sp_getSurveyQuestionsForTA" +
                    " because of an SQL Exception " + exception.getLocalizedMessage());
        } finally {
            if (storedProcedure != null) {
                storedProcedure.removeConnections();
            }
        }
        return userIdList;
    }

    @Override
    public HashMap<Long, HashMap<Long, IResponse>> getAllStudentResponses(String courseId) {
        IDatabaseAbstractFactory databaseAbstractFactory = DatabaseInjector.instance().getDatabaseAbstractFactory();
        ISurveyAbstractFactory surveyAbstractFactory = SurveyInjector.instance().getSurveyAbstractFactory();
        StoredProcedure storedProcedure = null;
        HashMap<Long, HashMap<Long, IResponse>> allStudentResponses = surveyAbstractFactory.
                createAllStudentResponsesInstance();
        HashMap<Long, IResponse> questionResponse = surveyAbstractFactory.createQuestionResponseInstance();
        IQuestionAbstractFactory questionAbstractFactory = QuestionInjector.instance().getQuestionAbstractFactory();
        log.info("Calling stored procedure sp_getAllStudentResponses to get all students' responses for a survey");
        try {
            storedProcedure = databaseAbstractFactory.createStoredProcedureInstance
                    ("sp_getAllStudentResponses(?)");
            storedProcedure.setInputStringParameter(1, courseId);
            ResultSet results = storedProcedure.executeWithResults();
            if (results != null) {
                while (results.next()) {
                    {
                        IResponse response = surveyAbstractFactory.createResponseInstance();
                        Long userId = results.getLong("user_id");
                        response.setQuestionId(results.getLong("question_id"));
                        response.setQuestionType(results.getInt("qtype_id"));
                        long option = results.getLong("option_id");
                        if (allStudentResponses.containsKey(userId)) {
                            questionResponse = allStudentResponses.get(userId);
                            if (option != 0) {
                                if (questionResponse.containsKey(response.getQuestionId())) {
                                    IResponse tempResponse = questionResponse.get(response.getQuestionId());
                                    List<String> options = tempResponse.getOptions();
                                    options.add(String.valueOf(option));
                                    tempResponse.setOptions(options);
                                    questionResponse.put(response.getQuestionId(), tempResponse);
                                } else {
                                    IResponse tempResponse = surveyAbstractFactory.createResponseInstance();
                                    List<String> options = surveyAbstractFactory.createOptionList();
                                    options.add(String.valueOf(option));
                                    tempResponse.setOptions(options);
                                    questionResponse.put(response.getQuestionId(), tempResponse);
                                }
                            } else {
                                response.setOptions(null);
                                response.setAnswerText(results.getString("answer_text"));
                                questionResponse.put(response.getQuestionId(), response);
                            }
                        } else {
                            questionResponse = surveyAbstractFactory.createQuestionResponseInstance();
                            if (option != 0) {
                                List<String> options = surveyAbstractFactory.createOptionList();
                                options.add(String.valueOf(option));
                                response.setOptions(options);
                            } else {
                                response.setOptions(null);
                                response.setAnswerText("answer_text");
                            }
                            questionResponse.put(response.getQuestionId(), response);
                        }
                        allStudentResponses.put(userId, questionResponse);
                    }
                }
            }
        } catch (SQLException exception) {
            log.error("Could not execute the Stored procedure sp_getAllStudentResponses" +
                    " because of an SQL Exception " + exception.getLocalizedMessage());
        } finally {
            if (storedProcedure != null) {
                storedProcedure.removeConnections();
            }
        }
        return allStudentResponses;
    }

}








