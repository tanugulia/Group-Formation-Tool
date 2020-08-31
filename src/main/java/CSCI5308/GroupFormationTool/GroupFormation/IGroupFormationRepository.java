package CSCI5308.GroupFormationTool.GroupFormation;

import CSCI5308.GroupFormationTool.User.IUser;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.TreeMap;

public interface IGroupFormationRepository {

    TreeMap<Integer, ArrayList<IUser>> getGroupsForCourse(String courseId);

    void insertUserToGroups(Integer key, String courseId, Long student);

    void deleteGroups(String courseId);

    HashMap<Long, IGroupFormula> getGroupFormationLogic(String courseId);
}
