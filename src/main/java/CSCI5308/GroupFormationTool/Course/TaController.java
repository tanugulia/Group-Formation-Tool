package CSCI5308.GroupFormationTool.Course;

import CSCI5308.GroupFormationTool.Survey.ISurvey;
import CSCI5308.GroupFormationTool.Survey.ISurveyAbstractFactory;
import CSCI5308.GroupFormationTool.Survey.SurveyInjector;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class TaController {

    private static final Logger log = LoggerFactory.getLogger(TaController.class.getName());

    @GetMapping(value = "/taCourseDetails")
    public ModelAndView courseDetails(@RequestParam(value = "courseName") String courseName,
                                      @RequestParam(value = "courseId") String courseId, Model model) {
        ISurveyAbstractFactory surveyAbstractFactory = SurveyInjector.instance().getSurveyAbstractFactory();
        ISurvey survey = surveyAbstractFactory.createSurveyInstance();
        ModelAndView modelAndView;
        boolean published = false;
        log.info("Checking if instructor has created a survey for the course " + courseId);
        int created = survey.getSurveyIdByCourseId(courseId);
        modelAndView = new ModelAndView("course/taCourseDetails");
        if (created != -1) {
            log.info("Checking if instructor has published the survey for the course " + courseId);
            published = survey.checkIfSurveyPublished(courseId);
        }
        modelAndView.addObject("published", published);
        modelAndView.addObject("courseId", courseId);
        modelAndView.addObject("courseName", courseName);
        modelAndView.addObject("created", created);
        return modelAndView;
    }
}
