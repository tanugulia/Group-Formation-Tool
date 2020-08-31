package CSCI5308.GroupFormationTool.GroupFormation;

public class GroupFormationInjector {

    private static GroupFormationInjector instance = null;

    private IGroupFormationAbstractFactory groupFormationAbstractFactory;

    private IGroupFormationRepository groupFormationRepository;

    private IGroupFormationManager groupFormationManager;

    private GroupFormationInjector() {
        groupFormationAbstractFactory = new GroupFormationAbstractFactory();
        groupFormationManager = groupFormationAbstractFactory.createGroupFormationManagerInstance();
        groupFormationRepository = groupFormationAbstractFactory.createGroupFormationRepositoryInstance();
    }

    public static GroupFormationInjector instance() {
        if (instance == null) {
            instance = new GroupFormationInjector();
        }
        return instance;
    }

    public IGroupFormationAbstractFactory getGroupFormationAbstractFactory() {
        return groupFormationAbstractFactory;
    }

    public IGroupFormationRepository getGroupFormationRepository() {
        return groupFormationRepository;
    }

    public void setGroupFormationRepository(IGroupFormationRepository groupFormationRepository) {
        this.groupFormationRepository = groupFormationRepository;
    }

    public IGroupFormationManager getGroupFormationManager() {
        return groupFormationManager;
    }

    public void setGroupFormationManager(IGroupFormationManager groupFormationManager) {
        this.groupFormationManager = groupFormationManager;
    }

}
