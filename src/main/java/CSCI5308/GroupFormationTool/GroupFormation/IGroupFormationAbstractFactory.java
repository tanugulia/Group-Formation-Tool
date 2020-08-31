package CSCI5308.GroupFormationTool.GroupFormation;

import CSCI5308.GroupFormationTool.Question.IQuestion;
import CSCI5308.GroupFormationTool.User.IUser;
import org.apache.commons.text.similarity.LevenshteinDetailedDistance;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.TreeMap;

public interface IGroupFormationAbstractFactory {

    IGroupFormationManager createGroupFormationManagerInstance();

    GroupFormationRepository createGroupFormationRepositoryInstance();

    TreeMap<Integer, ArrayList<IUser>> createGroupsForCourseInstance();

    HashMap<Long, Integer> createIndexUserIdToIndexInstance();

    HashMap<Integer, Long> createIndexUserIndexToUserIdInstance();

    HashMap<Integer, ArrayList<IQuestion>> createQuestionsBasedOnTypeInstance();

    HashMap<Long, ArrayList<ArrayList<Double>>> finalMatricesInstance();

    HashMap<String, HashMap<Integer, Integer>> additionalMappingsInstance();

    ArrayList<ArrayList<Double>> getMatrixInstance(int size);

    IGroupFormula createGroupFormulaInstance();

    HashMap<Long, IGroupFormula> createGroupLogicInstance();

    HashMap<Integer, Integer> createXValuesInstance();

    HashMap<Integer, ArrayList<Integer>> getTeamsInstance();

    HashMap<Integer, ArrayList<Long>> getFormedGroupsInstance();

    ArrayList<Long> getTeamSize(Integer teamSize);

    ArrayList<Double> createRowInstance(int students);

    LevenshteinDetailedDistance createLevenshteinInstance();

    ArrayList<Integer> createStudentListInstance();

    HashMap<Integer, Double> getMapForSorting();

    HashMap<Integer, Double> createLinkedListHashMap();
}
