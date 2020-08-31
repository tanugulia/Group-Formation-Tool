package CSCI5308.GroupFormationTool.GroupFormation;

import CSCI5308.GroupFormationTool.Common.DomainConstants;
import CSCI5308.GroupFormationTool.Question.IQuestion;
import CSCI5308.GroupFormationTool.Question.IQuestionAbstractFactory;
import CSCI5308.GroupFormationTool.Question.QuestionInjector;
import CSCI5308.GroupFormationTool.Survey.IResponse;
import CSCI5308.GroupFormationTool.Survey.ISurvey;
import CSCI5308.GroupFormationTool.Survey.ISurveyAbstractFactory;
import CSCI5308.GroupFormationTool.Survey.SurveyInjector;
import CSCI5308.GroupFormationTool.User.IUser;
import org.apache.commons.text.similarity.LevenshteinDetailedDistance;
import org.apache.commons.text.similarity.LevenshteinResults;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;
import java.util.stream.Collectors;

public class GroupFormationManager implements IGroupFormationManager {

    private static final Logger log = LoggerFactory.getLogger(GroupFormationManager.class.getName());

    private IGroupFormationRepository groupFormationRepository;

    @Override
    public TreeMap<Integer, ArrayList<IUser>> getGroupsForCourse(String courseId) {
        log.info("Fetching the groups for the course from the repository");
        groupFormationRepository = GroupFormationInjector.instance().getGroupFormationRepository();
        return groupFormationRepository.getGroupsForCourse(courseId);
    }

    @Override
    public HashMap<Integer, ArrayList<Long>> formGroups(String courseId) {
        IGroupFormationAbstractFactory groupFormationAbstractFactory = GroupFormationInjector.instance().
                getGroupFormationAbstractFactory();
        ISurveyAbstractFactory surveyAbstractFactory = SurveyInjector.instance().getSurveyAbstractFactory();
        IQuestionAbstractFactory questionAbstractFactory = QuestionInjector.instance().getQuestionAbstractFactory();
        ISurvey survey = surveyAbstractFactory.createSurveyInstance();
        groupFormationRepository = GroupFormationInjector.instance().getGroupFormationRepository();
        log.info("Fetching the survey questions");
        ArrayList<IQuestion> questions = survey.getQuestionsForSurvey(courseId);
        log.info("Fetching the list of students who took the survey");
        ArrayList<Long> userAnsweredSurveyBasedOnCourseId = survey.getUsersWhoTookSurvey(courseId);
        log.info("Fetching the group formation logic to form groups");
        HashMap<Long, IGroupFormula> groupLogic = groupFormationRepository.getGroupFormationLogic(courseId);
        log.info("Fetching all the student responses for the survey");
        HashMap<Long, HashMap<Long, IResponse>> studentWithQuestionAndAnswer = survey.
                getAllStudentResponses(courseId);
        HashMap<Long, Integer> indexUserIdToIndex = groupFormationAbstractFactory.createIndexUserIdToIndexInstance();
        HashMap<Integer, Long> indexUserIndexToUserID = groupFormationAbstractFactory.
                createIndexUserIndexToUserIdInstance();
        int index = 0;
        for (Long userId : userAnsweredSurveyBasedOnCourseId) {
            indexUserIdToIndex.put(userId, index);
            indexUserIndexToUserID.put(index, userId);
            index++;
        }
        HashMap<Integer, ArrayList<IQuestion>> questionsBasedOnType = groupFormationAbstractFactory.
                createQuestionsBasedOnTypeInstance();
        for (IQuestion question : questions) {
            if (questionsBasedOnType.containsKey(question.getType())) {
                questionsBasedOnType.get(question.getType()).add(question);
            } else {
                ArrayList<IQuestion> eachTypeQuestions = questionAbstractFactory.createQuestionListInstance();
                eachTypeQuestions.add(question);
                questionsBasedOnType.put(question.getType(), eachTypeQuestions);
            }
        }
        HashMap<Long, ArrayList<ArrayList<Double>>> finalMatrices = groupFormationAbstractFactory.
                finalMatricesInstance();
        HashMap<String, HashMap<Integer, Integer>> additionalMappings = groupFormationAbstractFactory.
                additionalMappingsInstance();
        for (Map.Entry<Integer, ArrayList<IQuestion>> entry : questionsBasedOnType.entrySet()) {
            if (entry.getKey() == DomainConstants.freeText) {
                HashMap<Long, ArrayList<ArrayList<Double>>> typeMatrices = formTextMatrixForAllStudents(
                        entry.getValue(), userAnsweredSurveyBasedOnCourseId.size(),
                        studentWithQuestionAndAnswer, groupLogic);
                finalMatrices.put((long) DomainConstants.freeText, formMatrices(typeMatrices, true,
                        userAnsweredSurveyBasedOnCourseId.size()));
            } else if (entry.getKey() == DomainConstants.numeric) {
                HashMap<Long, ArrayList<ArrayList<Double>>> typeMatrices =
                        formNumericMatrixForAllStudents(
                                entry.getValue(), userAnsweredSurveyBasedOnCourseId.size(),
                                studentWithQuestionAndAnswer, groupLogic);
                finalMatrices.put((long) DomainConstants.numeric, formMatrices(typeMatrices, true,
                        userAnsweredSurveyBasedOnCourseId.size()));
                additionalMappings = getNumericMappings(entry.getValue(), groupLogic,
                        studentWithQuestionAndAnswer, indexUserIdToIndex);
            } else if (entry.getKey() == DomainConstants.MCQOne) {
                HashMap<Long, ArrayList<ArrayList<Double>>> typeMatrices =
                        formSingleChoiceMatrixForAllStudents(
                                entry.getValue(), userAnsweredSurveyBasedOnCourseId.size(),
                                studentWithQuestionAndAnswer, groupLogic);
                finalMatrices.put((long) DomainConstants.MCQOne, formMatrices(typeMatrices, true,
                        userAnsweredSurveyBasedOnCourseId.size()));
            } else if (entry.getKey() == DomainConstants.MCQMultiple) {
                HashMap<Long, ArrayList<ArrayList<Double>>> typeMatrices =
                        formMultipleChoiceMultipleValueMatrixForAllStudents(
                                entry.getValue(), userAnsweredSurveyBasedOnCourseId.size(), studentWithQuestionAndAnswer,
                                groupLogic);
                finalMatrices.put((long) DomainConstants.MCQMultiple, formMatrices(typeMatrices, true,
                        userAnsweredSurveyBasedOnCourseId.size()));
            }
        }
        ArrayList<ArrayList<Double>> finalTotalMatrices = formMatrices(finalMatrices, false,
                userAnsweredSurveyBasedOnCourseId.size());
        Map.Entry<Long, IGroupFormula> entry = groupLogic.entrySet().iterator().next();
        Integer teamSize = entry.getValue().getGroupSize();
        return groupFormationAlgorithm(finalTotalMatrices, additionalMappings, teamSize, indexUserIndexToUserID);
    }

    @Override
    public void insertUserToGroups(String courseId, HashMap<Integer, ArrayList<Long>> teams) {
        log.info("Inserting into the groups for the course from the repository");
        groupFormationRepository = GroupFormationInjector.instance().getGroupFormationRepository();
        for (Map.Entry<Integer, ArrayList<Long>> team : teams.entrySet()) {
            for (Long student : team.getValue()) {
                groupFormationRepository.insertUserToGroups(team.getKey(), courseId, student);
            }
        }
    }

    @Override
    public void deleteGroups(String courseId) {
        log.info("Deleting the groups for the course from the repository");
        groupFormationRepository = GroupFormationInjector.instance().getGroupFormationRepository();
        groupFormationRepository.deleteGroups(courseId);
    }

    private ArrayList<ArrayList<Double>> formMatrices(HashMap<Long, ArrayList<ArrayList<Double>>> typeMatrices,
                                                      boolean considerMean, int students) {
        IGroupFormationAbstractFactory groupFormationAbstractFactory = GroupFormationInjector.instance()
                .getGroupFormationAbstractFactory();
        ArrayList<ArrayList<Double>> totalMatrix = groupFormationAbstractFactory.getMatrixInstance(students);
        for (int rowIndex = 0; rowIndex < students; rowIndex++) {
            ArrayList<Double> studentCount = groupFormationAbstractFactory.createRowInstance(students);
            for (int columnIndex = 0; columnIndex < students; columnIndex++) {
                studentCount.add(DomainConstants.minimumProbability);
            }
            totalMatrix.add(studentCount);
        }
        for (Map.Entry<Long, ArrayList<ArrayList<Double>>> questionMatrix : typeMatrices.entrySet()) {
            for (int row = 0; row < students; row++) {
                for (int column = 0; column < students; column++) {
                    totalMatrix.get(row).set(column,
                            totalMatrix.get(row).get(column) + questionMatrix.getValue().get(row).get(column));
                }
            }
        }
        if (considerMean) {
            for (int row = 0; row < students; row++)
                for (int column = 0; column < students; column++)
                    totalMatrix.get(row).set(column, totalMatrix.get(row).get(column) / (double) typeMatrices.size());
        }
        return totalMatrix;
    }

    private HashMap<Long, ArrayList<ArrayList<Double>>> formMultipleChoiceMultipleValueMatrixForAllStudents(
            ArrayList<IQuestion> questions, int students, HashMap<Long,
            HashMap<Long, IResponse>> studentWithQuestionAndAnswer,
            HashMap<Long, IGroupFormula> groupLogic) {
        IGroupFormationAbstractFactory groupFormationAbstractFactory = GroupFormationInjector.instance()
                .getGroupFormationAbstractFactory();
        HashMap<Long, ArrayList<ArrayList<Double>>> questionMatrix =
                groupFormationAbstractFactory.finalMatricesInstance();
        for (IQuestion question : questions) {
            ArrayList<ArrayList<Double>> matrix = groupFormationAbstractFactory.getMatrixInstance(students);
            for (Map.Entry<Long, HashMap<Long, IResponse>> student : studentWithQuestionAndAnswer
                    .entrySet()) {
                ArrayList<Double> row = groupFormationAbstractFactory.createRowInstance(students);
                for (Map.Entry<Long, HashMap<Long, IResponse>> secondStudent : studentWithQuestionAndAnswer
                        .entrySet()) {
                    if (student.getKey() == secondStudent.getKey()) {
                        row.add(DomainConstants.minimumDistance);
                    } else {
                        List<String> studentOptions = student.getValue().get(question.getId())
                                .getOptions();
                        List<String> secondStudentOptions = secondStudent.getValue().get(question.getId())
                                .getOptions();
                        List<String> intersectOptions = studentOptions.stream().filter(secondStudentOptions::contains)
                                .collect(Collectors.toList());
                        double probabilityValue = (double) (DomainConstants.factor * intersectOptions.size())
                                / (double) (studentOptions.size() + secondStudentOptions.size());
                        if (groupLogic.get(question.getId()).getSimilarity() == DomainConstants.isSimilar) {
                            row.add(probabilityValue);
                        } else {
                            row.add(DomainConstants.maximumProbability - probabilityValue);
                        }
                    }
                }
                matrix.add(row);
            }
            questionMatrix.put(question.getId(), matrix);
        }
        return questionMatrix;
    }

    private HashMap<String, HashMap<Integer, Integer>> getNumericMappings(
            ArrayList<IQuestion> numericTypeMatrix, HashMap<Long, IGroupFormula> groupLogic,
            HashMap<Long, HashMap<Long, IResponse>> studentWithQuestionAndAnswer,
            HashMap<Long, Integer> indexUserIdToIndex) {
        IGroupFormationAbstractFactory groupFormationAbstractFactory = GroupFormationInjector.instance().
                getGroupFormationAbstractFactory();
        HashMap<String, HashMap<Integer, Integer>> mappings = groupFormationAbstractFactory.additionalMappingsInstance();
        HashMap<Integer, Integer> lessthanXValues = groupFormationAbstractFactory.createXValuesInstance();
        HashMap<Integer, Integer> greaterthanXValues = groupFormationAbstractFactory.createXValuesInstance();
        for (Map.Entry<Long, Integer> user : indexUserIdToIndex.entrySet()) {
            lessthanXValues.put(user.getValue(), DomainConstants.initialXValues);
            greaterthanXValues.put(user.getValue(), DomainConstants.initialXValues);
        }
        for (IQuestion question : numericTypeMatrix) {
            for (Map.Entry<Long, HashMap<Long, IResponse>> student : studentWithQuestionAndAnswer
                    .entrySet()) {
                Integer studentKey = indexUserIdToIndex.get(student.getKey());
                if (Integer.parseInt(
                        student.getValue().get(question.getId()).getAnswerText()) <
                        groupLogic.get(question.getId()).getLesserThan()) {
                    if (lessthanXValues.containsKey(studentKey)) {
                        lessthanXValues.put(studentKey, lessthanXValues.get(studentKey) +
                                (int) DomainConstants.maximumProbability);
                    }
                }
                if (Integer.parseInt(student.getValue().get(question.getId()).getAnswerText()) >
                        groupLogic.get(question.getId()).getGreaterThan()) {
                    if (greaterthanXValues.containsKey(studentKey)) {
                        greaterthanXValues.put(studentKey, greaterthanXValues.get(studentKey) +
                                (int) DomainConstants.maximumProbability);
                    }
                }
            }
        }
        for (Map.Entry<Integer, Integer> lessThanXValue : lessthanXValues.entrySet()) {
            double averageLessThanXValue = lessThanXValue.getValue() * DomainConstants.probabilityFactor / (double) (numericTypeMatrix.size());
            if (averageLessThanXValue > DomainConstants.thresholdProbability) {
                lessThanXValue.setValue((int) DomainConstants.maximumProbability);
            } else {
                lessThanXValue.setValue((int) DomainConstants.minimumProbability);
            }
        }
        mappings.put(DomainConstants.lessThanX, lessthanXValues);
        for (Map.Entry<Integer, Integer> greaterThanXValue : greaterthanXValues.entrySet()) {
            double averageGreaterThanXValue = greaterThanXValue.getValue() * DomainConstants.probabilityFactor / (double) (numericTypeMatrix.size());
            if (averageGreaterThanXValue > DomainConstants.thresholdProbability) {
                greaterThanXValue.setValue((int) DomainConstants.maximumProbability);
            } else {
                greaterThanXValue.setValue((int) DomainConstants.minimumProbability);
            }
        }
        mappings.put(DomainConstants.greaterThanX, greaterthanXValues);
        return mappings;
    }

    private HashMap<Long, ArrayList<ArrayList<Double>>> formNumericMatrixForAllStudents(
            ArrayList<IQuestion> questions, int students, HashMap<Long,
            HashMap<Long, IResponse>> studentWithQuestionAndAnswer, HashMap<Long, IGroupFormula> groupLogic) {
        IGroupFormationAbstractFactory groupFormationAbstractFactory = GroupFormationInjector.instance().
                getGroupFormationAbstractFactory();
        HashMap<Long, ArrayList<ArrayList<Double>>> questionMatrix = groupFormationAbstractFactory.
                finalMatricesInstance();
        for (IQuestion question : questions) {
            ArrayList<ArrayList<Double>> matrix = groupFormationAbstractFactory.getMatrixInstance(students);
            for (Map.Entry<Long, HashMap<Long, IResponse>> student : studentWithQuestionAndAnswer
                    .entrySet()) {
                ArrayList<Double> row = groupFormationAbstractFactory.createRowInstance(students);
                for (Map.Entry<Long, HashMap<Long, IResponse>> secondStudent : studentWithQuestionAndAnswer
                        .entrySet()) {
                    if (student.getKey() == secondStudent.getKey()) {
                        row.add(DomainConstants.minimumDistance);
                    } else if (student.getValue().get(question.getId()).getAnswerText().equals(
                            secondStudent.getValue().get(question.getId()).getAnswerText())) {
                        if (groupLogic.get(question.getId()).getSimilarity() == DomainConstants.isSimilar) {
                            row.add(DomainConstants.maximumProbability);
                        } else {
                            row.add(DomainConstants.minimumProbability);
                        }
                    } else {
                        if (groupLogic.get(question.getId()).getSimilarity() == DomainConstants.isSimilar) {
                            row.add(DomainConstants.maximumProbability);
                        } else {
                            row.add(DomainConstants.minimumProbability);
                        }
                    }
                }
                matrix.add(row);
            }
            questionMatrix.put(question.getId(), matrix);
        }
        return questionMatrix;
    }

    private HashMap<Long, ArrayList<ArrayList<Double>>> formTextMatrixForAllStudents(ArrayList<IQuestion> value
            , int students, HashMap<Long,
            HashMap<Long, IResponse>> studentWithQuestionAndAnswer, HashMap<Long, IGroupFormula> groupLogic) {
        IGroupFormationAbstractFactory groupFormationAbstractFactory = GroupFormationInjector.instance().
                getGroupFormationAbstractFactory();
        HashMap<Long, ArrayList<ArrayList<Double>>> questionMatrix = groupFormationAbstractFactory.
                finalMatricesInstance();
        for (IQuestion question : value) {
            ArrayList<ArrayList<Double>> matrix = groupFormationAbstractFactory.getMatrixInstance(students);
            for (Map.Entry<Long, HashMap<Long, IResponse>> student : studentWithQuestionAndAnswer
                    .entrySet()) {
                ArrayList<Double> row = groupFormationAbstractFactory.createRowInstance(students);
                for (Map.Entry<Long, HashMap<Long, IResponse>> secondStudent : studentWithQuestionAndAnswer
                        .entrySet()) {
                    if (student.getKey() == secondStudent.getKey()) {
                        row.add(DomainConstants.minimumDistance);
                    } else {
                        LevenshteinDetailedDistance distance = groupFormationAbstractFactory.
                                createLevenshteinInstance();
                        String studentText = student.getValue().get(question.getId()).getAnswerText();
                        String secondStudentText = secondStudent.getValue().get(question.getId()).getAnswerText();
                        LevenshteinResults calculatedDistance = distance.apply(studentText, secondStudentText);
                        double probabilityValue = calculatedDistance.getDistance() *
                                DomainConstants.maximumProbability / DomainConstants.distanceFactor;
                        if (groupLogic.get(question.getId()).getMatchWords() > DomainConstants.minimumMatchWords) {
                            row.add(probabilityValue);
                        } else {
                            row.add(DomainConstants.maximumProbability - probabilityValue);
                        }
                    }
                }
                matrix.add(row);
            }
            questionMatrix.put(question.getId(), matrix);
        }
        return questionMatrix;
    }

    private HashMap<Integer, ArrayList<Long>> groupFormationAlgorithm(ArrayList<ArrayList<Double>> finalTotalMatrices,
                                                                      HashMap<String, HashMap<Integer, Integer>>
                                                                              additionalMappings,
                                                                      Integer teamSize,
                                                                      HashMap<Integer, Long> indexUserIndexToUserId) {
        IGroupFormationAbstractFactory groupFormationAbstractFactory = GroupFormationInjector.instance().
                getGroupFormationAbstractFactory();
        HashMap<Integer, ArrayList<Integer>> teams = groupFormationAbstractFactory.getTeamsInstance();
        HashMap<Integer, ArrayList<Long>> teamsWithUserId = groupFormationAbstractFactory.getFormedGroupsInstance();
        int count = 0;
        HashMap<Integer, Integer> studentLessThanX = groupFormationAbstractFactory.createXValuesInstance();
        HashMap<Integer, Integer> studentGreaterthanX = groupFormationAbstractFactory.createXValuesInstance();
        if (additionalMappings.containsKey(DomainConstants.lessThanX)) {
            studentLessThanX = additionalMappings.get(DomainConstants.lessThanX);
        }
        if (additionalMappings.containsKey(DomainConstants.greaterThanX)) {
            studentGreaterthanX = additionalMappings.get(DomainConstants.greaterThanX);
        }
        ArrayList<Integer> selectedStudents = groupFormationAbstractFactory.createStudentListInstance();
        for (int rowIndex = 0; rowIndex < finalTotalMatrices.size(); rowIndex++) {
            if (selectedStudents.contains(rowIndex) == false) {
                ArrayList<Integer> eachStudent = this.formTeams(finalTotalMatrices.get(rowIndex), teamSize,
                        studentLessThanX, studentGreaterthanX, true, true);
                for (Integer studentIndex : eachStudent) {
                    studentLessThanX.remove(studentIndex);
                    for (int row = 0; row < finalTotalMatrices.get(studentIndex).size(); row++) {
                        finalTotalMatrices.get(studentIndex).set(row, DomainConstants.maximumDistance);
                        finalTotalMatrices.get(row).set(studentIndex, DomainConstants.maximumDistance);
                    }
                }
                selectedStudents.addAll(eachStudent);
                teams.put(count, eachStudent);
            }
            count++;
        }
        for (Map.Entry<Integer, ArrayList<Integer>> team : teams.entrySet()) {
            ArrayList<Long> listTeam = groupFormationAbstractFactory.getTeamSize(teamSize);
            for (Integer id : team.getValue()) {
                listTeam.add(indexUserIndexToUserId.get(id));
            }
            teamsWithUserId.put(team.getKey(), listTeam);
        }
        return teamsWithUserId;
    }

    private ArrayList<Integer> formTeams(ArrayList<Double> arrayList, int teamSize,
                                         HashMap<Integer, Integer> studentLessThanX,
                                         HashMap<Integer, Integer> studentGreaterthanX,
                                         boolean useLessthanX, boolean useGreaterthanX) {
        IGroupFormationAbstractFactory groupFormationAbstractFactory = GroupFormationInjector.instance().
                getGroupFormationAbstractFactory();
        HashMap<Integer, Double> map = groupFormationAbstractFactory.getMapForSorting();
        for (int index = 0; index < arrayList.size(); ++index) {
            map.put(index, arrayList.get(index));
        }
        HashMap<Integer, Double> sortedValues = sortByValue(map);
        Set<Integer> indices = sortedValues.keySet();
        ArrayList<Integer> topStudents = groupFormationAbstractFactory.createStudentListInstance();
        boolean notUsedLessthanX = true;
        boolean notUsedGreaterthanX = true;
        for (Integer index : indices) {
            if (useGreaterthanX && notUsedGreaterthanX) {
                if (studentGreaterthanX.containsKey(index)) {
                    if (studentGreaterthanX.get(index) == DomainConstants.initialXValues) {
                        topStudents.add(index);
                        notUsedGreaterthanX = false;
                    }
                }
            }
            if (useLessthanX && notUsedLessthanX) {
                if (studentLessThanX.containsKey(index)) {
                    if (studentLessThanX.get(index) == (int) DomainConstants.maximumProbability) {
                        topStudents.add(index);
                        notUsedLessthanX = false;
                    }
                }
            }
            if (topStudents.size() == DomainConstants.thresholdStudentCount) {
                break;
            } else if ((useLessthanX && useGreaterthanX) == false) {
                if (topStudents.size() == DomainConstants.minimumStudentCount) {
                    break;
                }
            }
        }
        for (Integer index : indices) {
            if (topStudents.size() == teamSize) {
                break;
            } else if (topStudents.contains(index) == false
                    && sortedValues.get(index) != DomainConstants.maximumDistance) {
                topStudents.add(index);
            }
        }
        return topStudents;
    }

    public HashMap<Integer, Double> sortByValue(HashMap<Integer, Double> map) {
        IGroupFormationAbstractFactory groupFormationAbstractFactory = GroupFormationInjector.instance().
                getGroupFormationAbstractFactory();
        List<Map.Entry<Integer, Double>> list = new LinkedList<Map.Entry<Integer, Double>>(map.entrySet());
        Collections.sort(list, Comparator.comparing(Map.Entry::getValue));
        HashMap<Integer, Double> sortedHashMap = groupFormationAbstractFactory.createLinkedListHashMap();
        for (Map.Entry<Integer, Double> item : list) {
            sortedHashMap.put(item.getKey(), item.getValue());
        }
        return sortedHashMap;
    }

    private HashMap<Long, ArrayList<ArrayList<Double>>> formSingleChoiceMatrixForAllStudents(
            ArrayList<IQuestion> questions, int students, HashMap<Long,
            HashMap<Long, IResponse>> studentWithQuestionAndAnswer, HashMap<Long, IGroupFormula> groupLogic) {
        IGroupFormationAbstractFactory groupFormationAbstractFactory = GroupFormationInjector.instance().
                getGroupFormationAbstractFactory();
        HashMap<Long, ArrayList<ArrayList<Double>>> questionMatrix = groupFormationAbstractFactory.
                finalMatricesInstance();
        for (IQuestion question : questions) {
            ArrayList<ArrayList<Double>> matrix = groupFormationAbstractFactory.getMatrixInstance(students);
            for (Map.Entry<Long, HashMap<Long, IResponse>> student : studentWithQuestionAndAnswer
                    .entrySet()) {
                ArrayList<Double> row = groupFormationAbstractFactory.createRowInstance(students);
                for (Map.Entry<Long, HashMap<Long, IResponse>> secondStudent : studentWithQuestionAndAnswer
                        .entrySet()) {
                    if (student.getKey() == secondStudent.getKey()) {
                        row.add(DomainConstants.minimumDistance);
                    } else if (student.getValue().get(question.getId()).getOptions().get(0).equals(
                            secondStudent.getValue().get(question.getId()).getOptions().get(0))) {
                        if (groupLogic.get(question.getId()).getSimilarity() == DomainConstants.isSimilar) {
                            row.add(DomainConstants.minimumProbability);
                        } else {
                            row.add(DomainConstants.maximumProbability);
                        }
                    } else {
                        if (groupLogic.get(question.getId()).getSimilarity() == DomainConstants.isSimilar) {
                            row.add(DomainConstants.maximumProbability);
                        } else {
                            row.add(DomainConstants.minimumProbability);
                        }
                    }
                }
                matrix.add(row);
            }
            questionMatrix.put(question.getId(), matrix);
        }
        return questionMatrix;
    }

}
