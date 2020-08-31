package CSCI5308.GroupFormationTool.Survey;

import CSCI5308.GroupFormationTool.Course.InstructorCourseController;
import CSCI5308.GroupFormationTool.Database.DatabaseInjector;
import CSCI5308.GroupFormationTool.Database.IDatabaseAbstractFactory;
import CSCI5308.GroupFormationTool.Database.StoredProcedure;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class SurveyFormulaRepository implements ISurveyFormulaRepository {

    private static final Logger log = LoggerFactory.getLogger(InstructorCourseController.class.getName());

    @Override
    public ArrayList<SurveyFormula> getSurveyDetailsToSetAlgorithm(String courseId) {
        StoredProcedure storedProcedure = null;
        ISurveyAbstractFactory surveyAbstractFactory = SurveyInjector.instance().getSurveyAbstractFactory();
        IDatabaseAbstractFactory databaseAbstractFactory = DatabaseInjector.instance().getDatabaseAbstractFactory();
        ArrayList<SurveyFormula> surveyDetails = surveyAbstractFactory.createSurveyFormulaArrayList();
        try {
            log.info("Calling the stored procedure sp_getSurveyDetailsToSetAlgo to fetch survey details for a course");
            storedProcedure = databaseAbstractFactory.
                    createStoredProcedureInstance("sp_getSurveyDetailsToSetAlgo(?)");
            storedProcedure.setInputStringParameter(1, courseId);
            ResultSet results = storedProcedure.executeWithResults();
            if (results != null) {
                while (results.next()) {
                    {
                        SurveyFormula surveyRow = surveyAbstractFactory.createSurveyFormulaObj();
                        surveyRow.setCourseId(results.getString("course_id"));
                        surveyRow.setSurveyId(results.getInt("survey_id"));
                        surveyRow.setQuestionId(results.getInt("question_id"));
                        surveyRow.setQuestionText(results.getString("question_text"));
                        surveyRow.setQuestionType(results.getInt("qtype_id"));
                        surveyDetails.add(surveyRow);
                    }
                }
            }

        } catch (SQLException exception) {
            log.error("Could not execute the Stored procedure sp_getSurveyDetailsToSetAlgo" +
                    "because of an SQL Exception " + exception.getLocalizedMessage());
        } finally {
            if (storedProcedure != null) {
                storedProcedure.removeConnections();
            }
        }
        return surveyDetails;
    }

    @Override
    public String getAlgorithmIdBySurveyId(int surveyId) {
        IDatabaseAbstractFactory databaseAbstractFactory = DatabaseInjector.instance().getDatabaseAbstractFactory();
        StoredProcedure storedProcedure = null;
        String algorithmId = null;
        try {
            log.info("Calling the stored procedure sp_getAlgoIdBySurveyId to fetch Algo id for given survey Id");
            storedProcedure = databaseAbstractFactory.createStoredProcedureInstance("sp_getAlgoIdBySurveyId(?)");
            storedProcedure.setInputIntParameter(1, surveyId);
            ResultSet results = storedProcedure.executeWithResults();
            if (results != null) {
                while (results.next()) {
                    {
                        algorithmId = (results.getString("algo_id"));
                    }
                }
            }

        } catch (SQLException exception) {
            log.error("Could not execute the Stored procedure sp_getAlgoIdBySurveyId" +
                    "because of an SQL Exception " + exception.getLocalizedMessage());
        } finally {
            if (storedProcedure != null) {
                storedProcedure.removeConnections();
            }
        }
        return algorithmId;
    }

    @Override
    public void updateSurveyGroupSize(int groupSize, int surveyId, String courseId) {
        IDatabaseAbstractFactory databaseAbstractFactory = DatabaseInjector.instance().getDatabaseAbstractFactory();
        StoredProcedure storedProcedure = null;
        try {
            log.info("Calling the stored procedure sp_updateGroupSizeBySurveyId to update group size for given survey Id");
            storedProcedure = databaseAbstractFactory.createStoredProcedureInstance("sp_updateGroupSizeBySurveyId(?,?,?)");
            storedProcedure.setInputIntParameter(1, groupSize);
            storedProcedure.setInputIntParameter(2, surveyId);
            storedProcedure.setInputStringParameter(3, courseId);
            storedProcedure.execute();
        } catch (SQLException exception) {
            log.error("Could not execute the Stored procedure sp_updateGroupSizeBySurveyId" +
                    "because of an SQL Exception " + exception.getLocalizedMessage());
        } finally {
            if (storedProcedure != null) {
                storedProcedure.removeConnections();
            }
        }
    }

    @Override
    public Boolean createAlgorithm(SurveyFormulaList surveyFormulaList, String generatedAlgorithmId, int surveyId) {
        IDatabaseAbstractFactory databaseAbstractFactory = DatabaseInjector.instance().getDatabaseAbstractFactory();
        StoredProcedure storedProcedure = null;
        for (SurveyFormula surveyFormula : surveyFormulaList.getSurveyRules()) {
            try {
                log.info("Calling the stored procedure sp_createAlgo to create Survey formula for given surveyId");
                storedProcedure = databaseAbstractFactory.createStoredProcedureInstance("sp_createAlgo(?, ?, ?, ?, ?, ?, ?, ?)");
                storedProcedure.setInputStringParameter(1, generatedAlgorithmId);
                storedProcedure.setInputIntParameter(2, surveyId);
                storedProcedure.setInputIntParameter(3, surveyFormula.getQuestionId());
                if (surveyFormula.isCompareSimilarity()) {
                    storedProcedure.setInputIntParameter(4, 1);
                } else {
                    storedProcedure.setInputIntParameter(4, 0);
                }
                storedProcedure.setInputIntParameter(5, 0);
                if (surveyFormula.isFreeTextSimilarity()) {
                    storedProcedure.setInputIntParameter(6, 1);
                } else {
                    storedProcedure.setInputIntParameter(6, 0);
                }
                storedProcedure.setInputIntParameter(7, surveyFormula.getNumericLessThan());
                storedProcedure.setInputIntParameter(8, surveyFormula.getNumericGreaterThan());
                storedProcedure.execute();
            } catch (SQLException exception) {
                log.error("Could not execute the Stored procedure sp_createAlgo" +
                        "because of an SQL Exception " + exception.getLocalizedMessage());
                return false;
            } finally {
                if (storedProcedure != null) {
                    storedProcedure.removeConnections();
                }
            }
        }
        return true;
    }

    @Override
    public Boolean deleteExistingAlgorithm(String algorithmId) {
        StoredProcedure storedProcedure = null;
        IDatabaseAbstractFactory databaseAbstractFactory = DatabaseInjector.instance().getDatabaseAbstractFactory();
        boolean status = true;
        try {
            log.info("Calling the stored procedure sp_deleteAlgo to delete the given algo by algoId");
            storedProcedure = databaseAbstractFactory.createStoredProcedureInstance("sp_deleteAlgo(?)");
            storedProcedure.setInputStringParameter(1, algorithmId);
            storedProcedure.execute();
            status = storedProcedure.getParameter(2);
        } catch (SQLException exception) {
            status = false;
            log.error("Could not execute the Stored procedure sp_deleteAlgo" +
                    "because of an SQL Exception " + exception.getLocalizedMessage());
        } finally {
            if (storedProcedure != null) {
                storedProcedure.removeConnections();
            }
        }
        return status;
    }

    @Override
    public Boolean updateAlgorithmId(String generatedAlgorithmId, int surveyId) {
        IDatabaseAbstractFactory databaseAbstractFactory = DatabaseInjector.instance().getDatabaseAbstractFactory();
        StoredProcedure storedProcedure = null;
        Boolean status = true;
        try {
            log.info("Calling the stored procedure sp_updateAlgoIdBySurveyId to update algo id for the given survey Id");
            storedProcedure = databaseAbstractFactory.createStoredProcedureInstance("sp_updateAlgoIdBySurveyId(?,?)");
            storedProcedure.setInputStringParameter(1, generatedAlgorithmId);
            storedProcedure.setInputIntParameter(2, surveyId);
            storedProcedure.execute();
        } catch (SQLException exception) {
            log.error("Could not execute the Stored procedure sp_updateAlgoIdBySurveyId" +
                    "because of an SQL Exception " + exception.getLocalizedMessage());
            status = false;
        } finally {
            if (storedProcedure != null) {
                storedProcedure.removeConnections();
            }
        }
        return status;
    }
}
