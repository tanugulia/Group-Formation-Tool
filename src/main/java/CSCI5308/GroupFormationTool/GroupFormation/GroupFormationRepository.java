package CSCI5308.GroupFormationTool.GroupFormation;

import CSCI5308.GroupFormationTool.Database.DatabaseInjector;
import CSCI5308.GroupFormationTool.Database.IDatabaseAbstractFactory;
import CSCI5308.GroupFormationTool.Database.StoredProcedure;
import CSCI5308.GroupFormationTool.User.IUser;
import CSCI5308.GroupFormationTool.User.IUserAbstractFactory;
import CSCI5308.GroupFormationTool.User.UserInjector;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.TreeMap;

public class GroupFormationRepository implements IGroupFormationRepository {

    private static final Logger log = LoggerFactory.getLogger(GroupFormationRepository.class.getName());

    @Override
    public TreeMap<Integer, ArrayList<IUser>> getGroupsForCourse(String courseId) {
        IGroupFormationAbstractFactory groupFormationAbstractFactory = GroupFormationInjector.instance().
                getGroupFormationAbstractFactory();
        IUserAbstractFactory userAbstractFactory = UserInjector.instance().getUserAbstractFactory();
        IDatabaseAbstractFactory databaseAbstractFactory = DatabaseInjector.instance().getDatabaseAbstractFactory();
        TreeMap<Integer, ArrayList<IUser>> groups = groupFormationAbstractFactory.createGroupsForCourseInstance();
        ArrayList<IUser> userList = userAbstractFactory.createUserListInstance();
        StoredProcedure storedProcedure = null;
        try {
            log.info("Calling stored procedure sp_getGroupsForCourse to fetch the groups for the course");
            storedProcedure = databaseAbstractFactory.createStoredProcedureInstance
                    ("sp_getGroupsForCourse(?)");
            storedProcedure.setInputStringParameter(1, courseId);
            ResultSet results = storedProcedure.executeWithResults();
            if (results != null) {
                while (results.next()) {
                    int groupNumber = results.getInt("groupNumber");
                    IUser user = userAbstractFactory.createUserInstance();
                    user.setBannerId(results.getString("bannerId"));
                    user.setFirstName(results.getString("firstName"));
                    user.setLastName(results.getString("lastName"));
                    user.setEmailId(results.getString("emailId"));
                    user.setId(results.getLong("userId"));
                    if (groups.containsKey(groupNumber)) {
                        userList = groups.get(groupNumber);
                    } else {
                        userList = userAbstractFactory.createUserListInstance();
                    }
                    userList.add(user);
                    groups.put(groupNumber, userList);
                }
            }
        } catch (SQLException exception) {
            log.error("Could not execute the Stored procedure sp_getGroupsForCourse" +
                    " because of an SQL Exception " + exception.getLocalizedMessage());
        } finally {
            if (storedProcedure != null) {
                storedProcedure.removeConnections();
            }
        }
        return groups;
    }

    @Override
    public void insertUserToGroups(Integer groupNumber, String courseId, Long studentId) {
        IDatabaseAbstractFactory databaseAbstractFactory = DatabaseInjector.instance().getDatabaseAbstractFactory();
        StoredProcedure storedProcedure = null;
        try {
            log.info("Calling stored procedure sp_insertGroupsForCourse to fetch the groups for the course");
            storedProcedure = databaseAbstractFactory.createStoredProcedureInstance
                    ("sp_insertGroupsForCourse(?,?,?)");
            storedProcedure.setInputIntParameter(1, studentId);
            storedProcedure.setInputStringParameter(2, courseId);
            storedProcedure.setInputIntParameter(3, groupNumber);
            storedProcedure.execute();
        } catch (SQLException exception) {
            log.error("Could not execute the Stored procedure sp_insertGroupsForCourse" +
                    " because of an SQL Exception " + exception.getLocalizedMessage());
        } finally {
            if (storedProcedure != null) {
                storedProcedure.removeConnections();
            }
        }
    }

    @Override
    public void deleteGroups(String courseId) {
        IDatabaseAbstractFactory databaseAbstractFactory = DatabaseInjector.instance().getDatabaseAbstractFactory();
        StoredProcedure storedProcedure = null;
        try {
            log.info("Calling stored procedure sp_deleteGroupsForCourse to fetch the groups for the course");
            storedProcedure = databaseAbstractFactory.createStoredProcedureInstance
                    ("sp_deleteGroupsForCourse(?)");
            storedProcedure.setInputStringParameter(1, courseId);
            storedProcedure.execute();
        } catch (SQLException exception) {
            log.error("Could not execute the Stored procedure sp_deleteGroupsForCourse" +
                    " because of an SQL Exception " + exception.getLocalizedMessage());
        } finally {
            if (storedProcedure != null) {
                storedProcedure.removeConnections();
            }
        }
    }

    @Override
    public HashMap<Long, IGroupFormula> getGroupFormationLogic(String courseId) {
        IDatabaseAbstractFactory databaseAbstractFactory = DatabaseInjector.instance().getDatabaseAbstractFactory();
        IGroupFormationAbstractFactory groupFormationAbstractFactory = GroupFormationInjector.instance().
                getGroupFormationAbstractFactory();
        StoredProcedure storedProcedure = null;
        HashMap<Long, IGroupFormula> groupLogic = groupFormationAbstractFactory.createGroupLogicInstance();
        try {
            log.info("Calling stored procedure sp_getGroupFormationLogic to fetch the groups for the course");
            storedProcedure = databaseAbstractFactory.createStoredProcedureInstance
                    ("sp_getGroupFormationLogic(?)");
            storedProcedure.setInputStringParameter(1, courseId);
            ResultSet results = storedProcedure.executeWithResults();
            if (results != null) {
                while (results.next()) {
                    IGroupFormula groupFormula = groupFormationAbstractFactory.createGroupFormulaInstance();
                    groupFormula.setQuestionId(results.getLong("question_id"));
                    groupFormula.setSimilarity(results.getInt("similarity_factor"));
                    groupFormula.setMatchWords(results.getInt("match_words"));
                    groupFormula.setLesserThan(results.getInt("less_than"));
                    groupFormula.setGreaterThan(results.getInt("greater_than"));
                    groupFormula.setGroupSize(results.getInt("group_size"));
                    groupLogic.put(groupFormula.getQuestionId(), groupFormula);
                }
            }
        } catch (SQLException exception) {
            log.error("Could not execute the Stored procedure sp_getGroupFormationLogic" +
                    " because of an SQL Exception " + exception.getLocalizedMessage());
        } finally {
            if (storedProcedure != null) {
                storedProcedure.removeConnections();
            }
        }
        return groupLogic;
    }
}
