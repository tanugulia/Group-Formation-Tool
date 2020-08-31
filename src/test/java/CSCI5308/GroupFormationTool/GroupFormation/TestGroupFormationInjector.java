package CSCI5308.GroupFormationTool.GroupFormation;

public class TestGroupFormationInjector {

    private static TestGroupFormationInjector instance = null;

    private ITestGroupFormationAbstractFactory groupFormationAbstractFactory;

    private TestGroupFormationInjector() {
        groupFormationAbstractFactory = new TestGroupFormationAbstractFactory();
    }

    public static TestGroupFormationInjector instance() {

        if (instance == null) {
            return new TestGroupFormationInjector();
        }
        return instance;
    }

    public ITestGroupFormationAbstractFactory getGroupFormationAbstractFactory() {
        return groupFormationAbstractFactory;
    }
}
