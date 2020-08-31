package CSCI5308.GroupFormationTool.Survey;

import CSCI5308.GroupFormationTool.Course.InstructorCourseController;
import CSCI5308.GroupFormationTool.Question.IQuestion;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;

@Controller
public class SurveyController {

    private static final Logger log = LoggerFactory.getLogger(InstructorCourseController.class.getName());

    @GetMapping(value = "/survey/createSurvey")
    public String createSurvey(@RequestParam(value = "courseName") String courseName,
                               @RequestParam(value = "courseId") String courseId, Model model) {
        ISurveyAbstractFactory surveyAbstractFactory = SurveyInjector.instance().getSurveyAbstractFactory();
        ISurvey survey = surveyAbstractFactory.createSurveyInstance();
        log.info("Creating survey for course " + courseId);
        int surveyId = survey.createSurvey(courseId);
        log.info("Survey created with id " + surveyId);
        ArrayList<IQuestion> surveyQuestionList = survey.getQuestionsForSurvey(courseId);
        log.info("There are" + surveyQuestionList.size() + " questions in the database for the survey " + surveyId);
        model.addAttribute("questionList", null);
        model.addAttribute("surveyQuestionList", surveyQuestionList);
        model.addAttribute("surveyId", surveyId);
        model.addAttribute("courseId", courseId);
        model.addAttribute("courseName", courseName);
        return "survey/createSurvey";
    }

    @GetMapping(value = "/survey/createSurveyFormula")
    public String createSurveyFormula(@RequestParam(value = "courseName") String courseName,
                                      @RequestParam(value = "courseId") String courseId, Model model) {
        int surveyId = 0;
        ISurveyAbstractFactory surveyAbstractFactory = SurveyInjector.instance().getSurveyAbstractFactory();
        ISurveyFormula surveyFormula = surveyAbstractFactory.createSurveyFormulaInstance();
        ArrayList<SurveyFormula> surveyFormulaList = surveyAbstractFactory.createSurveyFormulaArrayList();
        SurveyFormulaList formulaList = surveyAbstractFactory.createSurveyFormulaListInstance();
        log.info("Getting survey Details for the formula setup");
        surveyFormulaList = surveyFormula.getSurveyDetailsToSetAlgorithm(courseId);
        formulaList.setSurveyRules(surveyFormulaList);
        for (SurveyFormula s : surveyFormulaList) {
            surveyId = s.getSurveyId();
            break;
        }
        log.info("Survey id for the course" + courseName + "is " + surveyId);
        model.addAttribute("courseId", courseId);
        model.addAttribute("courseName", courseName);
        model.addAttribute("surveyId", surveyId);
        model.addAttribute("surveyFormulaList", formulaList);
        return "survey/createSurveyFormula";
    }

    @PostMapping(value = "/survey/publishSurvey")
    public ModelAndView publishSurvey(@RequestParam(value = "courseName") String courseName,
                                      @RequestParam(value = "courseId") String courseId, Model model) {
        ISurveyAbstractFactory surveyAbstractFactory = SurveyInjector.instance().getSurveyAbstractFactory();
        ISurvey survey = surveyAbstractFactory.createSurveyInstance();
        ModelAndView modelAndView;
        log.info("Getting survey id for the course");
        int surveyId = survey.getSurveyIdByCourseId(courseId);
        log.info("Survey id for the course" + courseName + "is " + surveyId);
        boolean outcome = survey.publishSurvey(courseId);
        log.info("Check condition for survey published or not result value " + outcome);
        modelAndView = new ModelAndView("course/instructorCourseDetails");
        if (outcome) {
            modelAndView.addObject("published", true);
        } else {
            modelAndView.addObject("published", false);
            modelAndView.addObject("questionError", "Add atleast one question to the " +
                    "survey to publish the survey!!");
        }
        modelAndView.addObject("courseId", courseId);
        modelAndView.addObject("courseName", courseName);
        modelAndView.addObject("created", surveyId);
        return modelAndView;
    }

    @PostMapping(value = "/survey/saveSurveyFormula")
    public String saveSurveyFormula(@RequestParam(value = "courseName") String courseName,
                                    @RequestParam(value = "courseId") String courseId,
                                    @RequestParam(value = "surveyId") int surveyId,
                                    @RequestParam int groupSize,
                                    Model model,
                                    @ModelAttribute("surveyFormulaList") SurveyFormulaList surveyFormulaList) {
        ISurveyAbstractFactory surveyAbstractFactory = SurveyInjector.instance().getSurveyAbstractFactory();
        ISurveyFormula surveyFormula = surveyAbstractFactory.createSurveyFormulaInstance();
        log.info("Passing details to business layer for creating rule for survey " + surveyId);
        boolean status = surveyFormula.createSurveyFormula(courseId, surveyId, groupSize, surveyFormulaList);
        if (status) {
            log.info("Survey formula for survey:" + surveyId + " added successfully");
            model.addAttribute("SuccessMsg", "Survey Formula added successfully!");
        } else {
            log.error("Creation of survey formula for survey:" + surveyId + " failed");
            model.addAttribute("ErrorMsg", "Creation of survey formula failed!");
        }
        return "redirect:/instructorCourseDetails?courseId=" + courseId + "&courseName=" + courseName;
    }

    @GetMapping("/survey/addQuestionToSurvey")
    public String addQuestionToSurvey(@RequestParam("questionId") long questionId,
                                      @RequestParam("surveyId") long surveyId,
                                      @RequestParam("courseId") String courseId,
                                      @RequestParam("courseName") String courseName, Model model) {
        ISurveyAbstractFactory surveyAbstractFactory = SurveyInjector.instance().getSurveyAbstractFactory();
        ISurvey survey = surveyAbstractFactory.createSurveyInstance();
        log.info("Adding question " + questionId + "to the survey" + surveyId);
        survey.addQuestionToSurvey(questionId, surveyId);
        log.info("Get question list for the survey based on the course Id" + courseId);
        ArrayList<IQuestion> surveyQuestionList = survey.getQuestionsForSurvey(courseId);
        model.addAttribute("surveyId", surveyId);
        model.addAttribute("courseId", courseId);
        model.addAttribute("surveyQuestionList", surveyQuestionList);
        model.addAttribute("courseName", courseName);
        return "survey/createSurvey";
    }

    @GetMapping("/survey/deleteQuestionFromSurvey")
    public String deleteQuestionFromSurvey(@RequestParam("questionId") long questionId,
                                           @RequestParam("surveyId") long surveyId,
                                           @RequestParam("courseId") String courseId,
                                           @RequestParam("courseName") String courseName, Model model) {
        ISurveyAbstractFactory surveyAbstractFactory = SurveyInjector.instance().getSurveyAbstractFactory();
        ISurvey survey = surveyAbstractFactory.createSurveyInstance();
        log.info("Delete question " + questionId + "from the survey " + surveyId);
        survey.deleteQuestionFromSurvey(questionId, surveyId);
        log.info("Get question list for the survey based on the course Id" + courseId);
        ArrayList<IQuestion> surveyQuestionList = survey.getQuestionsForSurvey(courseId);
        model.addAttribute("surveyId", surveyId);
        model.addAttribute("courseId", courseId);
        model.addAttribute("courseName", courseName);
        model.addAttribute("surveyQuestionList", surveyQuestionList);
        return "survey/createSurvey";
    }

    @PostMapping("/questionManager/searchQuestionForSurvey")
    public String searchQuestionForSurvey(@RequestParam("questionTitle") String questionTitle,
                                          @RequestParam("surveyId") int surveyId,
                                          @RequestParam("courseId") String courseId,
                                          @RequestParam("courseName") String courseName, Model model) {
        ISurveyAbstractFactory surveyAbstractFactory = SurveyInjector.instance().getSurveyAbstractFactory();
        ISurvey survey = surveyAbstractFactory.createSurveyInstance();
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String emailId = authentication.getPrincipal().toString();
        ArrayList<IQuestion> questionList = null;
        log.info("Get question list for the course " + courseId + "with survey " + surveyId +
                "and question title " + questionTitle);
        questionList = survey.getSearchedQuestionListForSurvey(emailId, surveyId, courseId, questionTitle);
        log.info("Get question list for the survey based on the course Id" + courseId);
        ArrayList<IQuestion> surveyQuestionList = survey.getQuestionsForSurvey(courseId);
        model.addAttribute("surveyQuestionList", surveyQuestionList);
        model.addAttribute("surveyId", surveyId);
        model.addAttribute("courseId", courseId);
        model.addAttribute("courseName", courseName);
        model.addAttribute("questionList", questionList);
        return "survey/createSurvey";
    }

}
