package CSCI5308.GroupFormationTool.GroupFormation;

import CSCI5308.GroupFormationTool.Survey.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import java.util.ArrayList;
import java.util.HashMap;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@WebMvcTest(GroupFormationController.class)
public class GroupFormationControllerTest {

    private ITestGroupFormationAbstractFactory groupFormationAbstractFactory = TestGroupFormationInjector.instance().
            getGroupFormationAbstractFactory();

    private ITestSurveyAbstractFactory surveyAbstractFactory = TestSurveyInjector.instance().getSurveyAbstractFactory();

    private GroupFormationManager groupFormationManager;

    private SurveyRepository surveyRepository;

    @Autowired
    private MockMvc mockMvc;

    @Test
    void getGroupsTest() throws Exception {
        groupFormationManager = groupFormationAbstractFactory.createGroupFormationMock();
        ISurvey survey = surveyAbstractFactory.createSurveyInstance();
        GroupFormationInjector.instance().setGroupFormationManager(groupFormationManager);
        surveyRepository = surveyAbstractFactory.createSurveyRepositoryMock();
        SurveyInjector.instance().setSurveyRepository(surveyRepository);
        String courseId = "CSCI 5308";
        String courseName = "SDC";
        HashMap<Integer, ArrayList<Long>> teams = groupFormationAbstractFactory.getTeamsInstance();
        when(surveyRepository.checkIfSurveyCreated(courseId)).thenReturn(true);
        when(surveyRepository.checkIfSurveyPublished(courseId)).thenReturn(true);
        when(surveyRepository.checkIfSurveyHasFormula(courseId)).thenReturn(true);
        doNothing().when(groupFormationManager).deleteGroups(courseId);
        when(groupFormationManager.formGroups(courseId)).thenReturn(teams);
        doNothing().when(groupFormationManager).insertUserToGroups(courseId, teams);
        when(groupFormationManager.getGroupsForCourse(courseId)).thenReturn(groupFormationAbstractFactory.
                createGroupsInstance());
        this.mockMvc.perform(get("/groupFormation/getGroups")
                .param("courseId", courseId)
                .param("courseName", courseName))
                .andExpect(status().is(200))
                .andExpect(view().name("group/groupDetails"))
                .andDo(MockMvcResultHandlers.print())
                .andReturn();
        when(surveyRepository.checkIfSurveyCreated(courseId)).thenReturn(false);
        when(surveyRepository.checkIfSurveyPublished(courseId)).thenReturn(true);
        when(surveyRepository.checkIfSurveyHasFormula(courseId)).thenReturn(true);
        when(groupFormationManager.getGroupsForCourse(courseId)).thenReturn(groupFormationAbstractFactory.
                createGroupsInstance());
        this.mockMvc.perform(get("/groupFormation/getGroups")
                .param("courseId", courseId)
                .param("courseName", courseName))
                .andExpect(status().is(200))
                .andExpect(view().name("group/groupDetails"))
                .andDo(MockMvcResultHandlers.print())
                .andReturn();
        when(surveyRepository.checkIfSurveyCreated(courseId)).thenReturn(true);
        when(surveyRepository.checkIfSurveyPublished(courseId)).thenReturn(false);
        when(surveyRepository.checkIfSurveyHasFormula(courseId)).thenReturn(true);
        when(groupFormationManager.getGroupsForCourse(courseId)).thenReturn(groupFormationAbstractFactory.
                createGroupsInstance());
        this.mockMvc.perform(get("/groupFormation/getGroups")
                .param("courseId", courseId)
                .param("courseName", courseName))
                .andExpect(status().is(200))
                .andExpect(view().name("group/groupDetails"))
                .andDo(MockMvcResultHandlers.print())
                .andReturn();
        when(surveyRepository.checkIfSurveyCreated(courseId)).thenReturn(true);
        when(surveyRepository.checkIfSurveyPublished(courseId)).thenReturn(true);
        when(surveyRepository.checkIfSurveyHasFormula(courseId)).thenReturn(false);
        when(groupFormationManager.getGroupsForCourse(courseId)).thenReturn(groupFormationAbstractFactory.
                createGroupsInstance());
        this.mockMvc.perform(get("/groupFormation/getGroups")
                .param("courseId", courseId)
                .param("courseName", courseName))
                .andExpect(status().is(200))
                .andExpect(view().name("group/groupDetails"))
                .andDo(MockMvcResultHandlers.print())
                .andReturn();
    }
}
