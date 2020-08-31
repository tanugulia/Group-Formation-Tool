package CSCI5308.GroupFormationTool.Survey;

import CSCI5308.GroupFormationTool.Common.DomainConstants;
import CSCI5308.GroupFormationTool.Course.CourseInjector;
import CSCI5308.GroupFormationTool.Course.ICourseAbstractFactory;
import CSCI5308.GroupFormationTool.Course.IUserCourses;
import CSCI5308.GroupFormationTool.Question.IQuestion;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@Controller
public class ResponseController {

    private static final Logger Log = LoggerFactory.getLogger(ResponseController.class.getName());

    private static final Logger log = LoggerFactory.getLogger(ResponseController.class.getName());

    private ISurvey surveyInstance;

    private IResponse responseInstance;

    @GetMapping("/courseSurveyResponse")
    public String takeSurvey(@RequestParam(value = "courseName") String courseName,
                             @RequestParam(value = "courseId") String courseId, Model model) {
        ICourseAbstractFactory courseAbstractFactory = CourseInjector.instance().getCourseAbstractFactory();
        ISurveyAbstractFactory surveyAbstractFactory = SurveyInjector.instance().getSurveyAbstractFactory();
        surveyInstance = surveyAbstractFactory.createSurveyInstance();
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        IUserCourses userCourses = courseAbstractFactory.createUserCoursesInstance();
        String userRole = null;
        String emailId = authentication.getPrincipal().toString();
        userRole = userCourses.getUserRoleByEmailId(emailId);
        if (userRole.equals(DomainConstants.studentRole)) {
            String surveyId = surveyInstance.getSurveyId(courseId);
            ArrayList<IQuestion> surveyQuestions = surveyInstance.getSurveyQuestions(surveyId);
            model.addAttribute("surveyQuestions", surveyQuestions);
            model.addAttribute("courseName", courseName);
            model.addAttribute("courseId", courseId);
            return "course/courseSurveyResponse";
        } else {
            Log.warn("Other type of user tried directly accessing response page");
            return "redirect:login";
        }
    }

    @PostMapping("/courseSurveyResponse")
    public String submitSurvey(@RequestParam Map<String, String> searchParams, Model model) {
        ISurveyAbstractFactory surveyAbstractFactory = SurveyInjector.instance().getSurveyAbstractFactory();
        responseInstance = surveyAbstractFactory.createResponseInstance();
        boolean success = false;
        ArrayList<IResponse> responseList = responseInstance.createResponseList(searchParams);
        if (responseList != null) {
            success = responseInstance.storeResponses(responseList);
        }
        if (success) {
            model.addAttribute("Success", DomainConstants.surveySuccess);
        } else {
            model.addAttribute("Error", DomainConstants.dbError);
        }
        return "course/courseSurveySubmitted";
    }

    @GetMapping("/viewResponse")
    public String viewUserResponse(@RequestParam("userId") Long userId,
                                   @RequestParam("courseId") String courseId,
                                   @RequestParam("courseName") String courseName,
                                   @RequestParam("surveyId") Long surveyId,
                                   Model model) {
        ISurveyAbstractFactory surveyAbstractFactory = SurveyInjector.instance().getSurveyAbstractFactory();
        IResponse response = surveyAbstractFactory.createResponseInstance();
        log.info("Getting responses of the student for the course survey");
        HashMap<Long, IResponse> questionAndAnswers = response.getUserResponses(userId, surveyId, courseId);
        model.addAttribute("courseName", courseName);
        model.addAttribute("courseId", courseId);
        model.addAttribute("responses", questionAndAnswers);
        return "survey/userResponse";
    }
}
