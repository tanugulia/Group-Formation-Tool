package CSCI5308.GroupFormationTool.Course;

import CSCI5308.GroupFormationTool.Question.QuestionManagerRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.ArrayList;

@Controller
public class GuestController {

    private static final Logger Log = LoggerFactory.getLogger(QuestionManagerRepository.class.getName());

    @GetMapping("/guest/guestCourses")
    public String guestCourses(Model model) {
        ICourseAbstractFactory courseAbstractFactory = CourseInjector.instance().getCourseAbstractFactory();
        ICourse course = courseAbstractFactory.createCourseInstance();
        ArrayList<ICourse> courseList = null;
        Log.info("Function call to fetch all course details for a Guest user");
        courseList = course.getAllCourses();
        model.addAttribute("courses", courseList);
        return "course/guestCourses";
    }
}
