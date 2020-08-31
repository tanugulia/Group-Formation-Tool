package CSCI5308.GroupFormationTool.GroupFormation;

public class GroupFormula implements IGroupFormula {

    private long questionId;

    private int similarity;

    private int matchWords;

    private int greaterThan;

    private int lesserThan;

    private int groupSize;

    public long getQuestionId() {
        return questionId;
    }

    public void setQuestionId(long questionId) {
        this.questionId = questionId;
    }

    public int getSimilarity() {
        return similarity;
    }

    public void setSimilarity(int similarity) {
        this.similarity = similarity;
    }

    public int getMatchWords() {
        return matchWords;
    }

    public void setMatchWords(int matchWords) {
        this.matchWords = matchWords;
    }

    public int getGreaterThan() {
        return greaterThan;
    }

    public void setGreaterThan(int greaterThan) {
        this.greaterThan = greaterThan;
    }

    public int getLesserThan() {
        return lesserThan;
    }

    public void setLesserThan(int lesserThan) {
        this.lesserThan = lesserThan;
    }

    public int getGroupSize() {
        return groupSize;
    }

    public void setGroupSize(int groupSize) {
        this.groupSize = groupSize;
    }
}
