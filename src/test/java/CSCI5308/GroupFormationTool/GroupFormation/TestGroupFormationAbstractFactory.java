package CSCI5308.GroupFormationTool.GroupFormation;

import CSCI5308.GroupFormationTool.Survey.IResponse;
import CSCI5308.GroupFormationTool.Survey.Response;
import CSCI5308.GroupFormationTool.User.IUser;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.TreeMap;

import static org.mockito.Mockito.mock;

public class TestGroupFormationAbstractFactory implements ITestGroupFormationAbstractFactory {

    @Override
    public IGroupFormationManager createGroupFormationManagerInstance() {
        return new GroupFormationManager();
    }

    @Override
    public GroupFormationRepository createGroupFormationRepositoryMock() {
        return mock(GroupFormationRepository.class);
    }

    @Override
    public TreeMap<Integer, ArrayList<IUser>> createGroupsInstance() {
        return new TreeMap<Integer, ArrayList<IUser>>();
    }

    @Override
    public GroupFormationManager createGroupFormationMock() {
        return mock(GroupFormationManager.class);
    }

    @Override
    public HashMap<Integer, ArrayList<Long>> getTeamsInstance() {
        return new HashMap<Integer, ArrayList<Long>>();
    }

    @Override
    public ArrayList<Long> createUserIdListInstance() {
        return new ArrayList<Long>();
    }

    @Override
    public HashMap<Long, IGroupFormula> createGroupLogicInstance() {
        return new HashMap<Long, IGroupFormula>();
    }

    @Override
    public IGroupFormula createGroupInstance() {
        return new GroupFormula();
    }

    @Override
    public HashMap<Long, HashMap<Long, IResponse>> studentWithQuestionAndAnswerInstance() {
        return new HashMap<Long, HashMap<Long, IResponse>>();
    }

    @Override
    public HashMap<Long, IResponse> questionResponseInstance() {
        return new HashMap<Long, IResponse>();
    }

    @Override
    public IResponse createResponseInstance() {
        return new Response();
    }

    @Override
    public List<String> createOptionList() {
        return new ArrayList<String>();
    }

    @Override
    public HashMap<Integer, ArrayList<Long>> getFormedGroupsInstance() {
        return new HashMap<Integer, ArrayList<Long>>();
    }

    @Override
    public GroupFormulaDBMock createGroupFormulaDBMockInstance() {
        return new GroupFormulaDBMock();
    }
}
