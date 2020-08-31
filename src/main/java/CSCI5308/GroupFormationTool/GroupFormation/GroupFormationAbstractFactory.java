package CSCI5308.GroupFormationTool.GroupFormation;

import CSCI5308.GroupFormationTool.Question.IQuestion;
import CSCI5308.GroupFormationTool.User.IUser;
import org.apache.commons.text.similarity.LevenshteinDetailedDistance;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.TreeMap;

public class GroupFormationAbstractFactory implements IGroupFormationAbstractFactory {

    @Override
    public IGroupFormationManager createGroupFormationManagerInstance() {
        return new GroupFormationManager();
    }

    @Override
    public GroupFormationRepository createGroupFormationRepositoryInstance() {
        return new GroupFormationRepository();
    }

    @Override
    public TreeMap<Integer, ArrayList<IUser>> createGroupsForCourseInstance() {
        return new TreeMap<Integer, ArrayList<IUser>>();
    }

    @Override
    public HashMap<Long, Integer> createIndexUserIdToIndexInstance() {
        return new HashMap<Long, Integer>();
    }

    @Override
    public HashMap<Integer, Long> createIndexUserIndexToUserIdInstance() {
        return new HashMap<Integer, Long>();
    }

    @Override
    public HashMap<Integer, ArrayList<IQuestion>> createQuestionsBasedOnTypeInstance() {
        return new HashMap<Integer, ArrayList<IQuestion>>();
    }

    @Override
    public HashMap<Long, ArrayList<ArrayList<Double>>> finalMatricesInstance() {
        return new HashMap<Long, ArrayList<ArrayList<Double>>>();
    }

    @Override
    public HashMap<String, HashMap<Integer, Integer>> additionalMappingsInstance() {
        return new HashMap<String, HashMap<Integer, Integer>>();
    }

    @Override
    public ArrayList<ArrayList<Double>> getMatrixInstance(int size) {
        return new ArrayList<ArrayList<Double>>(size);
    }

    @Override
    public IGroupFormula createGroupFormulaInstance() {
        return new GroupFormula();
    }

    @Override
    public HashMap<Long, IGroupFormula> createGroupLogicInstance() {
        return new HashMap<Long, IGroupFormula>();
    }

    @Override
    public HashMap<Integer, Integer> createXValuesInstance() {
        return new HashMap<Integer, Integer>();
    }

    @Override
    public HashMap<Integer, ArrayList<Integer>> getTeamsInstance() {
        return new HashMap<Integer, ArrayList<Integer>>();
    }

    @Override
    public HashMap<Integer, ArrayList<Long>> getFormedGroupsInstance() {
        return new HashMap<Integer, ArrayList<Long>>();
    }

    @Override
    public ArrayList<Long> getTeamSize(Integer teamSize) {
        return new ArrayList<Long>(teamSize);
    }

    @Override
    public ArrayList<Double> createRowInstance(int students) {
        return new ArrayList<Double>();
    }

    @Override
    public LevenshteinDetailedDistance createLevenshteinInstance() {
        return new LevenshteinDetailedDistance();
    }

    @Override
    public ArrayList<Integer> createStudentListInstance() {
        return new ArrayList<Integer>();
    }

    @Override
    public HashMap<Integer, Double> getMapForSorting() {
        return new HashMap<Integer, Double>();
    }

    @Override
    public HashMap<Integer, Double> createLinkedListHashMap() {
        return new LinkedHashMap<Integer, Double>();
    }

}
