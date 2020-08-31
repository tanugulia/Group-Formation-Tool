package CSCI5308.GroupFormationTool.Survey;

import CSCI5308.GroupFormationTool.Common.DomainConstants;
import CSCI5308.GroupFormationTool.Course.CourseInjector;
import CSCI5308.GroupFormationTool.Course.ICourse;
import CSCI5308.GroupFormationTool.Course.IUserCoursesRepository;
import CSCI5308.GroupFormationTool.Question.IQuestion;
import CSCI5308.GroupFormationTool.User.IUser;
import CSCI5308.GroupFormationTool.User.IUserAbstractFactory;
import CSCI5308.GroupFormationTool.User.UserInjector;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.HashMap;

public class Survey implements ISurvey {

    private static final Logger log = LoggerFactory.getLogger(Survey.class.getName());

    private long id;

    private ICourse course;

    private String surveyId;

    private String description;

    private String courseId;

    private ISurveyRepository surveyRepository;

    public Survey() {
        this.surveyId = null;
        this.description = null;
        this.courseId = null;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public ICourse getCourse() {
        return course;
    }

    public void setCourse(ICourse course) {
        this.course = course;
    }

    public String getSurveyId() {
        return surveyId;
    }

    public void setSurveyId(String surveyId) {
        this.surveyId = surveyId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String courseId() {
        return courseId;
    }

    public void setCourseId(String courseId) {
        this.courseId = courseId;
    }

    @Override
    public boolean checkIfSurveyCreated(String courseId) {
        log.info("Checking if the survey is created for the course " + courseId + " from the database");
        ISurveyRepository surveyRepository = SurveyInjector.instance().getSurveyRepository();
        return surveyRepository.checkIfSurveyCreated(courseId);
    }

    @Override
    public boolean checkIfSurveyPublished(String courseId) {
        log.info("Checking if the survey is published for the course " + courseId + " from the database");
        ISurveyRepository surveyRepository = SurveyInjector.instance().getSurveyRepository();
        return surveyRepository.checkIfSurveyPublished(courseId);
    }

    @Override
    public boolean checkIfSurveyHasFormula(String courseId) {
        log.info("Checking if the survey has an algorithm for group formation for the course " +
                courseId + " from the database");
        ISurveyRepository surveyRepository = SurveyInjector.instance().getSurveyRepository();
        return surveyRepository.checkIfSurveyHasFormula(courseId);
    }

    @Override
    public int checkIfGroupsCanBeFormedForSurvey(String courseId) {
        if (checkIfSurveyCreated(courseId) == false) {
            return DomainConstants.surveyNotCreated;
        }
        if (checkIfSurveyPublished(courseId) == false) {
            return DomainConstants.surveyNotPublished;
        }
        if (checkIfSurveyHasFormula(courseId) == false) {
            return DomainConstants.surveyNotHavingAlgorithm;
        }
        return DomainConstants.surveyGroupFormationPossible;
    }

    @Override
    public boolean publishSurvey(String courseId) {
        log.info("Publishing the survey for the course " + courseId);
        ISurveyRepository surveyRepository = SurveyInjector.instance().getSurveyRepository();
        return surveyRepository.publishSurvey(courseId);
    }

    @Override
    public int getSurveyIdByCourseId(String courseId) {
        log.info("Getting survey id based on course id " + courseId);
        ISurveyRepository surveyRepository = SurveyInjector.instance().getSurveyRepository();
        return surveyRepository.getSurveyIdByCourseId(courseId);
    }

    public String getSurveyId(String courseId) {
        log.info("Getting the survey Id for a course " + courseId);
        surveyRepository = SurveyInjector.instance().getSurveyRepository();
        return surveyRepository.getSurveyId(courseId);
    }

    public ArrayList<IQuestion> getSurveyQuestions(String surveyId) {
        log.info("Getting the survey questions for a course by survey id " + surveyId);
        surveyRepository = SurveyInjector.instance().getSurveyRepository();
        return surveyRepository.getSurveyQuestions(surveyId);
    }

    @Override
    public boolean isSurveyPublished(String courseId) {
        log.info("Checks if the survey is published for a course " + courseId);
        surveyRepository = SurveyInjector.instance().getSurveyRepository();
        String surveyId = surveyRepository.getSurveyId(courseId);
        return surveyRepository.isSurveyPublished(surveyId);
    }

    @Override
    public boolean isSurveyCompleted(String courseId, String userId) {
        surveyRepository = SurveyInjector.instance().getSurveyRepository();
        String surveyId = surveyRepository.getSurveyId(courseId);
        log.info("Checking if the survey" + surveyId + "is completed for a course "
                + courseId + " by the user " + userId);
        return surveyRepository.isSurveyCompleted(surveyId, userId);
    }

    public int createSurvey(String courseId) {
        log.info("Creating the survey for a course " + courseId);
        surveyRepository = SurveyInjector.instance().getSurveyRepository();
        return surveyRepository.createSurvey(courseId);
    }

    public boolean addQuestionToSurvey(long questionId, long surveyId) {
        log.info("Adding question " + questionId + " to a survey " + surveyId);
        surveyRepository = SurveyInjector.instance().getSurveyRepository();
        return surveyRepository.addQuestionToSurvey(questionId, surveyId);
    }

    public ArrayList<IQuestion> getQuestionsForSurvey(String courseId) {
        log.info("Getting the survey questions for a course " + courseId);
        surveyRepository = SurveyInjector.instance().getSurveyRepository();
        return surveyRepository.getQuestionsForSurvey(courseId);
    }

    public boolean deleteQuestionFromSurvey(long questionId, long surveyId) {
        log.info("Deleting the survey question " + questionId + " from a survey " + surveyId);
        surveyRepository = SurveyInjector.instance().getSurveyRepository();
        return surveyRepository.deleteQuestionFromSurvey(questionId, surveyId);
    }

    @Override
    public ArrayList<IQuestion> getSearchedQuestionListForSurvey(String emailId, int surveyId, String courseId,
                                                                 String questionTitle) {
        log.info("Getting the survey questions for a course based on the searched value: " + questionTitle);
        IUserCoursesRepository userCoursesRepository;
        IUserAbstractFactory userAbstractFactory = UserInjector.instance().getUserAbstractFactory();
        userCoursesRepository = CourseInjector.instance().getUserCoursesRepository();
        String userType = userCoursesRepository.getUserRoleByEmailId(emailId);
        surveyRepository = SurveyInjector.instance().getSurveyRepository();
        ArrayList<IQuestion> questionList = null;
        if (userType.equals(DomainConstants.instructorRole)) {
            questionList = surveyRepository.getSurveyQuestionListForInstructor(emailId, surveyId, questionTitle);
        } else if (userType.equals(DomainConstants.tARole)) {
            ArrayList<IUser> instructorsList = userCoursesRepository.getInstructorsForCourse(courseId);
            ArrayList<Long> instructorIds = userAbstractFactory.createUserIdList();
            for (IUser user : instructorsList) {
                instructorIds.add(user.getId());
            }
            questionList = surveyRepository.getSurveyQuestionListForTA(instructorIds, surveyId, questionTitle);
        }
        return questionList;
    }

    @Override
    public ArrayList<Long> getUsersWhoTookSurvey(String courseId) {
        log.info("Getting the users who took the questions for a course survey by course id " + courseId);
        surveyRepository = SurveyInjector.instance().getSurveyRepository();
        return surveyRepository.getUsersWhoTookSurvey(courseId);
    }

    @Override
    public HashMap<Long, HashMap<Long, IResponse>> getAllStudentResponses(String courseId) {
        log.info("Getting the responses of all users who took the course survey for course id " + courseId);
        surveyRepository = SurveyInjector.instance().getSurveyRepository();
        return surveyRepository.getAllStudentResponses(courseId);
    }
}
