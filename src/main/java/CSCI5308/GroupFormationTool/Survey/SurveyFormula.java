package CSCI5308.GroupFormationTool.Survey;

import CSCI5308.GroupFormationTool.Course.InstructorCourseController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.UUID;

public class SurveyFormula implements ISurveyFormula {

    private static final Logger log = LoggerFactory.getLogger(InstructorCourseController.class.getName());

    private String courseId;

    private int surveyId;

    private int questionId;

    private String questionText;

    private int questionType;

    private boolean compareSimilarity;

    private boolean compareDisimilarity;

    private int numericGreaterThan;

    private int numericLessThan;

    private boolean freeTextSimilarity;

    private boolean freeTextDisimilarity;

    public SurveyFormula() {
        this.courseId = null;
        this.surveyId = 0;
        this.questionId = 0;
        this.questionText = null;
        this.questionType = 0;
        this.compareSimilarity = false;
        this.compareDisimilarity = false;
        this.numericGreaterThan = 0;
        this.numericLessThan = 0;
        this.freeTextSimilarity = false;
        this.freeTextDisimilarity = false;
    }

    @Override
    public String getCourseId() {
        return courseId;
    }

    @Override
    public void setCourseId(String courseId) {
        this.courseId = courseId;
    }

    @Override
    public int getSurveyId() {
        return surveyId;
    }

    @Override
    public void setSurveyId(int surveyId) {
        this.surveyId = surveyId;
    }

    @Override
    public int getQuestionId() {
        return questionId;
    }

    @Override
    public void setQuestionId(int questionId) {
        this.questionId = questionId;
    }

    @Override
    public String getQuestionText() {
        return questionText;
    }

    @Override
    public void setQuestionText(String questionText) {
        this.questionText = questionText;
    }

    @Override
    public int getQuestionType() {
        return questionType;
    }

    @Override
    public void setQuestionType(int questionType) {
        this.questionType = questionType;
    }

    @Override
    public boolean isCompareSimilarity() {
        return compareSimilarity;
    }

    @Override
    public void setCompareSimilarity(boolean compareSimilarity) {
        this.compareSimilarity = compareSimilarity;
    }

    @Override
    public int getNumericGreaterThan() {
        return numericGreaterThan;
    }

    @Override
    public void setNumericGreaterThan(int numericGreaterThan) {
        this.numericGreaterThan = numericGreaterThan;
    }

    @Override
    public int getNumericLessThan() {
        return numericLessThan;
    }

    @Override
    public void setNumericLessThan(int numericLessThan) {
        this.numericLessThan = numericLessThan;
    }

    @Override
    public boolean isFreeTextSimilarity() {
        return freeTextSimilarity;
    }

    @Override
    public void setFreeTextSimilarity(boolean freeTextSimilarity) {
        this.freeTextSimilarity = freeTextSimilarity;
    }

    @Override
    public boolean isCompareDisimilarity() {
        return compareDisimilarity;
    }

    @Override
    public void setCompareDisimilarity(boolean compareDisimilarity) {
        this.compareDisimilarity = compareDisimilarity;
    }

    @Override
    public boolean isFreeTextDisimilarity() {
        return freeTextDisimilarity;
    }

    @Override
    public void setFreeTextDisimilarity(boolean freeTextDisimilarity) {
        this.freeTextDisimilarity = freeTextDisimilarity;
    }

    @Override
    public ArrayList<SurveyFormula> getSurveyDetailsToSetAlgorithm(String courseId) {
        ISurveyFormulaRepository surveyFormulaRepository = SurveyInjector.instance().getSurveyFormulaRepository();
        return surveyFormulaRepository.getSurveyDetailsToSetAlgorithm(courseId);
    }

    @Override
    public boolean createSurveyFormula(String courseId, int surveyId, int groupSize,
                                       SurveyFormulaList surveyFormulaList) {
        boolean status;
        ISurveyFormulaRepository surveyFormulaRepository = SurveyInjector.instance().getSurveyFormulaRepository();
        log.info("Checking if survey formula exists for survey:" + surveyId);
        String algorithmId = surveyFormulaRepository.getAlgorithmIdBySurveyId(surveyId);
        UUID uuid = UUID.randomUUID();
        String generatedAlgorithmId = uuid.toString();
        if (algorithmId == null) {
            log.info("Adding new survey formula by calling surveyFormulaRepository");
            surveyFormulaRepository.updateAlgorithmId(generatedAlgorithmId, surveyId);
            surveyFormulaRepository.updateSurveyGroupSize(groupSize, surveyId, courseId);
            status = surveyFormulaRepository.createAlgorithm(surveyFormulaList, generatedAlgorithmId, surveyId);
        } else {
            log.warn("Deleting the existing algo for survey:" + surveyId);
            surveyFormulaRepository.deleteExistingAlgorithm(algorithmId);
            log.info("Adding new survey formula by calling surveyFormulaRepository");
            surveyFormulaRepository.updateAlgorithmId(generatedAlgorithmId, surveyId);
            surveyFormulaRepository.updateSurveyGroupSize(groupSize, surveyId, courseId);
            status = surveyFormulaRepository.createAlgorithm(surveyFormulaList, generatedAlgorithmId, surveyId);
        }
        return status;
    }

}
