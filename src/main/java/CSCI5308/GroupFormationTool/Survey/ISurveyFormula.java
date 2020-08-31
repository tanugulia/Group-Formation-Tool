package CSCI5308.GroupFormationTool.Survey;

import java.util.ArrayList;

public interface ISurveyFormula {

    String getCourseId();

    void setCourseId(String courseId);

    int getSurveyId();

    void setSurveyId(int surveyId);

    int getQuestionId();

    void setQuestionId(int questionId);

    String getQuestionText();

    void setQuestionText(String questionText);

    int getQuestionType();

    void setQuestionType(int questionType);

    boolean isCompareSimilarity();

    void setCompareSimilarity(boolean compareSimilarity);

    int getNumericGreaterThan();

    void setNumericGreaterThan(int numericGreaterThan);

    int getNumericLessThan();

    void setNumericLessThan(int numericLessThan);

    boolean isFreeTextSimilarity();

    void setFreeTextSimilarity(boolean freeTextSimilarity);

    ArrayList<SurveyFormula> getSurveyDetailsToSetAlgorithm(String courseId);

    boolean isCompareDisimilarity();

    void setCompareDisimilarity(boolean compareDisimilarity);

    boolean isFreeTextDisimilarity();

    void setFreeTextDisimilarity(boolean freeTextDisimilarity);

    boolean createSurveyFormula(String courseId, int surveyId, int groupSize, SurveyFormulaList surveyFormulaList);

}
