package CSCI5308.GroupFormationTool.GroupFormation;

import CSCI5308.GroupFormationTool.Survey.IResponse;
import CSCI5308.GroupFormationTool.User.IUser;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.TreeMap;

public interface ITestGroupFormationAbstractFactory {

    IGroupFormationManager createGroupFormationManagerInstance();

    GroupFormationRepository createGroupFormationRepositoryMock();

    TreeMap<Integer, ArrayList<IUser>> createGroupsInstance();

    GroupFormationManager createGroupFormationMock();

    HashMap<Integer, ArrayList<Long>> getTeamsInstance();

    ArrayList<Long> createUserIdListInstance();

    HashMap<Long, IGroupFormula> createGroupLogicInstance();

    IGroupFormula createGroupInstance();

    HashMap<Long, HashMap<Long, IResponse>> studentWithQuestionAndAnswerInstance();

    HashMap<Long, IResponse> questionResponseInstance();

    IResponse createResponseInstance();

    List<String> createOptionList();

    HashMap<Integer, ArrayList<Long>> getFormedGroupsInstance();

    GroupFormulaDBMock createGroupFormulaDBMockInstance();

}
