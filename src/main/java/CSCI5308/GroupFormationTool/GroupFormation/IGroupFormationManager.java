package CSCI5308.GroupFormationTool.GroupFormation;

import CSCI5308.GroupFormationTool.User.IUser;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.TreeMap;

public interface IGroupFormationManager {

    TreeMap<Integer, ArrayList<IUser>> getGroupsForCourse(String courseId);

    HashMap<Integer, ArrayList<Long>> formGroups(String courseId);

    void insertUserToGroups(String courseId, HashMap<Integer, ArrayList<Long>> teams);

    void deleteGroups(String courseId);
}
