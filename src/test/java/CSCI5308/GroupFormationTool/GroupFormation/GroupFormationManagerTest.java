package CSCI5308.GroupFormationTool.GroupFormation;

import CSCI5308.GroupFormationTool.Common.DomainConstants;
import CSCI5308.GroupFormationTool.Question.IQuestion;
import CSCI5308.GroupFormationTool.Question.ITestQuestionAbstractFactory;
import CSCI5308.GroupFormationTool.Question.TestQuestionInjector;
import CSCI5308.GroupFormationTool.Survey.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

public class GroupFormationManagerTest {

    private ITestGroupFormationAbstractFactory testGroupFormationAbstractFactory = TestGroupFormationInjector.
            instance().getGroupFormationAbstractFactory();

    private GroupFormationRepository groupFormationRepository;

    private IGroupFormationManager groupFormationManager;

    @BeforeEach
    void init() {
        groupFormationRepository = testGroupFormationAbstractFactory.createGroupFormationRepositoryMock();
        GroupFormationInjector.instance().setGroupFormationRepository(groupFormationRepository);
        groupFormationManager = testGroupFormationAbstractFactory.createGroupFormationManagerInstance();
    }

    @Test
    void getGroupsForCourseTest() {
        String courseId = "CSCI 5308";
        when(groupFormationRepository.getGroupsForCourse(courseId)).
                thenReturn(testGroupFormationAbstractFactory.createGroupsInstance());
        assertTrue(groupFormationManager.getGroupsForCourse(courseId).size() == 0);
    }

    @Test
    void deleteGroupsForCourse() {
        String courseId = "CSCI 5308";
        doNothing().when(groupFormationRepository).deleteGroups(courseId);
        groupFormationManager.deleteGroups(courseId);
    }

    @Test
    void insertGroupsToCourse() {
        String courseId = "CSCI 5308";
        HashMap<Integer, ArrayList<Long>> teams = testGroupFormationAbstractFactory.getTeamsInstance();
        ArrayList<Long> studentIds = testGroupFormationAbstractFactory.createUserIdListInstance();
        studentIds.add((long) 1);
        doNothing().when(groupFormationRepository).insertUserToGroups(1, courseId, (long) 1);
        groupFormationManager.insertUserToGroups(courseId, teams);
        teams.put(1, studentIds);
        doNothing().when(groupFormationRepository).insertUserToGroups(1, courseId, studentIds.get(0));
        groupFormationManager.insertUserToGroups(courseId, teams);
    }

    @Test
    void formGroupsTest() {
        String courseId = "CSCI 6509";
        ITestSurveyAbstractFactory surveyAbstractFactory = TestSurveyInjector.instance().getSurveyAbstractFactory();
        ISurveyRepository surveyRepository = surveyAbstractFactory.createSurveyRepositoryMock();
        SurveyInjector.instance().setSurveyRepository(surveyRepository);
        ITestQuestionAbstractFactory questionAbstractFactory = TestQuestionInjector.instance().
                getQuestionAbstractFactory();
        ArrayList<IQuestion> questions = questionAbstractFactory.createQuestionListInstance();
        IQuestion question = questionAbstractFactory.createQuestionInstance();
        question.setId(40);
        question.setType(DomainConstants.MCQOne);
        questions.add(question);
        question = questionAbstractFactory.createQuestionInstance();
        question.setType(DomainConstants.numeric);
        question.setId(41);
        questions.add(question);
        question = questionAbstractFactory.createQuestionInstance();
        question.setType(DomainConstants.freeText);
        question.setId(42);
        questions.add(question);
        question = questionAbstractFactory.createQuestionInstance();
        question.setType(DomainConstants.MCQMultiple);
        question.setId(43);
        questions.add(question);
        when(surveyRepository.getQuestionsForSurvey(courseId)).thenReturn(questions);
        ArrayList<Long> usersWhoTookSurvey = testGroupFormationAbstractFactory.createUserIdListInstance();
        usersWhoTookSurvey.add((long) 75);
        usersWhoTookSurvey.add((long) 86);
        usersWhoTookSurvey.add((long) 87);
        usersWhoTookSurvey.add((long) 88);
        when(surveyRepository.getUsersWhoTookSurvey(courseId)).thenReturn(usersWhoTookSurvey);
        HashMap<Long, IGroupFormula> groupFormationLogic = testGroupFormationAbstractFactory.
                createGroupLogicInstance();
        IGroupFormula groupFormula = testGroupFormationAbstractFactory.createGroupInstance();
        groupFormula.setQuestionId(40);
        groupFormula.setGroupSize(2);
        groupFormula.setLesserThan(0);
        groupFormula.setGreaterThan(0);
        groupFormula.setSimilarity(0);
        groupFormula.setMatchWords(0);
        groupFormationLogic.put((long) 40, groupFormula);
        groupFormula = testGroupFormationAbstractFactory.createGroupInstance();
        groupFormula.setQuestionId(41);
        groupFormula.setGroupSize(2);
        groupFormula.setLesserThan(2);
        groupFormula.setGreaterThan(9);
        groupFormula.setSimilarity(0);
        groupFormula.setMatchWords(0);
        groupFormationLogic.put((long) 41, groupFormula);
        groupFormula = testGroupFormationAbstractFactory.createGroupInstance();
        groupFormula.setQuestionId(42);
        groupFormula.setGroupSize(2);
        groupFormula.setLesserThan(0);
        groupFormula.setGreaterThan(0);
        groupFormula.setSimilarity(0);
        groupFormula.setMatchWords(0);
        groupFormationLogic.put((long) 42, groupFormula);
        groupFormula = testGroupFormationAbstractFactory.createGroupInstance();
        groupFormula.setQuestionId(43);
        groupFormula.setGroupSize(2);
        groupFormula.setLesserThan(0);
        groupFormula.setGreaterThan(0);
        groupFormula.setSimilarity(0);
        groupFormula.setMatchWords(0);
        assertTrue(groupFormula.getQuestionId() == 43);
        groupFormationLogic.put((long) 43, groupFormula);
        when(groupFormationRepository.getGroupFormationLogic(courseId)).thenReturn(groupFormationLogic);
        HashMap<Long, HashMap<Long, IResponse>> studentWithQuestionAndAnswer = formResponses();
        when(surveyRepository.getAllStudentResponses(courseId)).thenReturn(studentWithQuestionAndAnswer);
        HashMap<Integer, ArrayList<Long>> getFormedGroupsInstance = groupFormationManager.formGroups(courseId);
        assertTrue(getFormedGroupsInstance.size() == 2);
    }

    private HashMap<Long, HashMap<Long, IResponse>> formResponses() {
        HashMap<Long, HashMap<Long, IResponse>> studentWithQuestionAndAnswer = testGroupFormationAbstractFactory.
                studentWithQuestionAndAnswerInstance();
        HashMap<Long, IResponse> studentQuestions = testGroupFormationAbstractFactory.questionResponseInstance();
        IResponse response = testGroupFormationAbstractFactory.createResponseInstance();
        response.setQuestionId(40);
        response.setSurveyId(2);
        List<String> options = testGroupFormationAbstractFactory.createOptionList();
        options.add("32");
        response.setOptions(options);
        studentQuestions.put((long) 40, response);
        response = testGroupFormationAbstractFactory.createResponseInstance();
        response.setQuestionId(41);
        response.setSurveyId(2);
        response.setAnswerText("3");
        response.setOptions(null);
        studentQuestions.put((long) 41, response);
        response = testGroupFormationAbstractFactory.createResponseInstance();
        response.setQuestionId(42);
        response.setSurveyId(2);
        response.setAnswerText("3");
        response.setOptions(null);
        studentQuestions.put((long) 42, response);
        response = testGroupFormationAbstractFactory.createResponseInstance();
        response.setQuestionId(43);
        response.setSurveyId(2);
        options = testGroupFormationAbstractFactory.createOptionList();
        options.add("34");
        options.add("36");
        response.setOptions(options);
        studentQuestions.put((long) 43, response);
        studentWithQuestionAndAnswer.put((long) 75, studentQuestions);
        studentQuestions = testGroupFormationAbstractFactory.questionResponseInstance();
        response = testGroupFormationAbstractFactory.createResponseInstance();
        response.setQuestionId(40);
        response.setSurveyId(2);
        options = testGroupFormationAbstractFactory.createOptionList();
        options.add("33");
        response.setOptions(options);
        studentQuestions.put((long) 40, response);
        response = testGroupFormationAbstractFactory.createResponseInstance();
        response.setQuestionId(41);
        response.setSurveyId(2);
        response.setAnswerText("7");
        response.setOptions(null);
        studentQuestions.put((long) 41, response);
        response = testGroupFormationAbstractFactory.createResponseInstance();
        response.setQuestionId(42);
        response.setSurveyId(2);
        response.setAnswerText("Hi");
        response.setOptions(null);
        studentQuestions.put((long) 42, response);
        response = testGroupFormationAbstractFactory.createResponseInstance();
        response.setQuestionId(43);
        response.setSurveyId(2);
        options = testGroupFormationAbstractFactory.createOptionList();
        options.add("35");
        options.add("37");
        response.setOptions(options);
        studentQuestions.put((long) 43, response);
        studentWithQuestionAndAnswer.put((long) 86, studentQuestions);
        studentQuestions = testGroupFormationAbstractFactory.questionResponseInstance();
        response = testGroupFormationAbstractFactory.createResponseInstance();
        response.setQuestionId(40);
        response.setSurveyId(2);
        options = testGroupFormationAbstractFactory.createOptionList();
        options.add("33");
        response.setOptions(options);
        studentQuestions.put((long) 40, response);
        response = testGroupFormationAbstractFactory.createResponseInstance();
        response.setQuestionId(41);
        response.setSurveyId(2);
        response.setAnswerText("7");
        response.setOptions(null);
        studentQuestions.put((long) 41, response);
        response = testGroupFormationAbstractFactory.createResponseInstance();
        response.setQuestionId(42);
        response.setSurveyId(2);
        response.setAnswerText("Hi");
        response.setOptions(null);
        studentQuestions.put((long) 42, response);
        response = testGroupFormationAbstractFactory.createResponseInstance();
        response.setQuestionId(43);
        response.setSurveyId(2);
        options = testGroupFormationAbstractFactory.createOptionList();
        options.add("35");
        options.add("37");
        response.setOptions(options);
        studentQuestions.put((long) 43, response);
        studentWithQuestionAndAnswer.put((long) 88, studentQuestions);
        studentQuestions = testGroupFormationAbstractFactory.questionResponseInstance();
        response = testGroupFormationAbstractFactory.createResponseInstance();
        response.setQuestionId(40);
        response.setSurveyId(2);
        options = testGroupFormationAbstractFactory.createOptionList();
        options.add("32");
        response.setOptions(options);
        studentQuestions.put((long) 40, response);
        response = testGroupFormationAbstractFactory.createResponseInstance();
        response.setQuestionId(41);
        response.setSurveyId(2);
        response.setAnswerText("3");
        response.setOptions(null);
        studentQuestions.put((long) 41, response);
        response = testGroupFormationAbstractFactory.createResponseInstance();
        response.setQuestionId(42);
        response.setSurveyId(2);
        response.setAnswerText("3");
        response.setOptions(null);
        studentQuestions.put((long) 42, response);
        response = testGroupFormationAbstractFactory.createResponseInstance();
        response.setQuestionId(43);
        response.setSurveyId(2);
        options = testGroupFormationAbstractFactory.createOptionList();
        options.add("34");
        options.add("36");
        response.setOptions(options);
        studentQuestions.put((long) 43, response);
        studentWithQuestionAndAnswer.put((long) 87, studentQuestions);
        return studentWithQuestionAndAnswer;
    }
}
