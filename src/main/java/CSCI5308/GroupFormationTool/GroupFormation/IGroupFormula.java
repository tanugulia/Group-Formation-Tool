package CSCI5308.GroupFormationTool.GroupFormation;

public interface IGroupFormula {

    long getQuestionId();

    void setQuestionId(long questionId);

    int getSimilarity();

    void setSimilarity(int similarity);

    int getMatchWords();

    void setMatchWords(int matchWords);

    int getGreaterThan();

    void setGreaterThan(int greaterThan);

    int getLesserThan();

    void setLesserThan(int lesserThan);

    int getGroupSize();

    void setGroupSize(int groupSize);
}
