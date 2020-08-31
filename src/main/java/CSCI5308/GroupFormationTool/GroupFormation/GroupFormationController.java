package CSCI5308.GroupFormationTool.GroupFormation;

import CSCI5308.GroupFormationTool.Common.DomainConstants;
import CSCI5308.GroupFormationTool.Survey.ISurvey;
import CSCI5308.GroupFormationTool.Survey.ISurveyAbstractFactory;
import CSCI5308.GroupFormationTool.Survey.SurveyInjector;
import CSCI5308.GroupFormationTool.User.IUser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.TreeMap;

@Controller
public class GroupFormationController {

    private static final Logger log = LoggerFactory.getLogger(GroupFormationController.class.getName());

    private IGroupFormationManager groupFormationManager;

    @GetMapping(value = "/groupFormation/getGroups")
    public String getGroups(@RequestParam(value = "courseName") String courseName,
                            @RequestParam(value = "courseId") String courseId, Model model) {
        groupFormationManager = GroupFormationInjector.instance().getGroupFormationManager();
        IGroupFormationAbstractFactory groupFormationAbstractFactory = GroupFormationInjector.instance().
                getGroupFormationAbstractFactory();
        ISurveyAbstractFactory surveyAbstractFactory = SurveyInjector.instance().getSurveyAbstractFactory();
        ISurvey survey = surveyAbstractFactory.createSurveyInstance();
        long surveyId = survey.getSurveyIdByCourseId(courseId);
        TreeMap<Integer, ArrayList<IUser>> groups = groupFormationAbstractFactory.createGroupsForCourseInstance();
        log.info("Checking if the survey can be used to form groups");
        int outcome = survey.checkIfGroupsCanBeFormedForSurvey(courseId);
        if (outcome == DomainConstants.surveyNotCreated) {
            model.addAttribute("surveyNotCreated", "Survey not created yet!");
            model.addAttribute("showGroups", false);
        } else if (outcome == DomainConstants.surveyNotPublished) {
            model.addAttribute("surveyNotPublished", "Survey not published yet!");
            model.addAttribute("showGroups", false);
        } else if (outcome == DomainConstants.surveyNotHavingAlgorithm) {
            model.addAttribute("surveyNotHavingAlgorithm", "Survey does not have any group " +
                    "formation algorithm yet!");
            model.addAttribute("showGroups", false);
        } else {
            log.info("Getting the list of groups formed on the instructor's algorithm");
            groupFormationManager.deleteGroups(courseId);
            HashMap<Integer, ArrayList<Long>> teams = groupFormationManager.formGroups(courseId);
            groupFormationManager.insertUserToGroups(courseId, teams);
            groups = groupFormationManager.getGroupsForCourse(courseId);
            model.addAttribute("groups", groups);
            model.addAttribute("showGroups", true);
        }
        model.addAttribute("courseId", courseId);
        model.addAttribute("courseName", courseName);
        model.addAttribute("surveyId", surveyId);
        return "group/groupDetails";
    }
}