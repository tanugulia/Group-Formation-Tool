package CSCI5308.GroupFormationTool.GroupFormation;

public class GroupFormulaDBMock {

    private long questionId;

    private int similarity;

    private int matchWords;

    private int greaterThan;

    private int lesserThan;

    private int groupSize;

    public GroupFormulaDBMock() {
        questionId = 1;
        similarity = 1;
        matchWords = 1;
        greaterThan = 1;
        lesserThan = 1;
        groupSize = 2;
    }

    public IGroupFormula loadGroupFormula(IGroupFormula groupFormula) {
        groupFormula.setMatchWords(matchWords);
        groupFormula.setSimilarity(similarity);
        groupFormula.setGreaterThan(greaterThan);
        groupFormula.setLesserThan(lesserThan);
        groupFormula.setGroupSize(groupSize);
        groupFormula.setQuestionId(questionId);
        return groupFormula;
    }

}
