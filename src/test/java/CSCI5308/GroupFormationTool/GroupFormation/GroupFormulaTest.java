package CSCI5308.GroupFormationTool.GroupFormation;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class GroupFormulaTest {

    private ITestGroupFormationAbstractFactory groupFormationAbstractFactory = TestGroupFormationInjector.instance().
            getGroupFormationAbstractFactory();

    public IGroupFormula createDefaultResponse() {
        GroupFormulaDBMock groupFormulaDBMock = groupFormationAbstractFactory.createGroupFormulaDBMockInstance();
        IGroupFormula groupFormula = loadResponse(groupFormulaDBMock);
        return groupFormula;
    }

    public IGroupFormula loadResponse(GroupFormulaDBMock groupFormulaDBMock) {
        IGroupFormula groupFormula = groupFormationAbstractFactory.createGroupInstance();
        groupFormula = groupFormulaDBMock.loadGroupFormula(groupFormula);
        return groupFormula;
    }

    @Test
    void getQuestionId() {
        IGroupFormula groupFormula = createDefaultResponse();
        assertTrue(groupFormula.getQuestionId() == 1);
    }

    @Test
    void setQuestionId() {
        IGroupFormula groupFormula = groupFormationAbstractFactory.createGroupInstance();
        groupFormula.setQuestionId(2);
        assertTrue(groupFormula.getQuestionId() == 2);
    }

    @Test
    void getSimilarity() {
        IGroupFormula groupFormula = createDefaultResponse();
        assertTrue(groupFormula.getSimilarity() == 1);
    }

    @Test
    void setSimilarity() {
        IGroupFormula groupFormula = groupFormationAbstractFactory.createGroupInstance();
        groupFormula.setSimilarity(0);
        assertTrue(groupFormula.getSimilarity() == 0);
    }

    @Test
    void getMatchWords() {
        IGroupFormula groupFormula = createDefaultResponse();
        assertTrue(groupFormula.getMatchWords() == 1);
    }

    @Test
    void setMatchWords() {
        IGroupFormula groupFormula = groupFormationAbstractFactory.createGroupInstance();
        groupFormula.setMatchWords(2);
        assertTrue(groupFormula.getMatchWords() == 2);
    }

    @Test
    void getGreaterThan() {
        IGroupFormula groupFormula = createDefaultResponse();
        assertTrue(groupFormula.getGreaterThan() == 1);
    }

    @Test
    void setGreaterThan() {
        IGroupFormula groupFormula = groupFormationAbstractFactory.createGroupInstance();
        groupFormula.setGreaterThan(2);
        assertTrue(groupFormula.getGreaterThan() == 2);
    }

    @Test
    void getLesserThan() {
        IGroupFormula groupFormula = createDefaultResponse();
        assertTrue(groupFormula.getLesserThan() == 1);
    }

    @Test
    void setLesserThan() {
        IGroupFormula groupFormula = groupFormationAbstractFactory.createGroupInstance();
        groupFormula.setLesserThan(2);
        assertTrue(groupFormula.getLesserThan() == 2);
    }

    @Test
    void getGroupSize() {
        IGroupFormula groupFormula = createDefaultResponse();
        assertTrue(groupFormula.getGroupSize() == 2);
    }

    @Test
    void setGroupSize() {
        IGroupFormula groupFormula = groupFormationAbstractFactory.createGroupInstance();
        groupFormula.setGroupSize(2);
        assertTrue(groupFormula.getGroupSize() == 2);
    }
}
