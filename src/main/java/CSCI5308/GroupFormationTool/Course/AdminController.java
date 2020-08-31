package CSCI5308.GroupFormationTool.Course;

import CSCI5308.GroupFormationTool.Common.DomainConstants;
import CSCI5308.GroupFormationTool.Question.QuestionAdminController;
import CSCI5308.GroupFormationTool.User.IUser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;

@Controller
public class AdminController {

    private static final Logger Log = LoggerFactory.getLogger(QuestionAdminController.class.getName());

    @GetMapping("/admin/allCourses")
    public String adminCourses(Model model) {
        ICourseAbstractFactory courseAbstractFactory = CourseInjector.instance().getCourseAbstractFactory();
        ICourse course = courseAbstractFactory.createCourseInstance();
        Log.info("Fetching all course details from the Database");
        List<ICourse> allCourses = course.getAllCourses();
        model.addAttribute("courses", allCourses);
        return "course/allCourses";
    }

    @GetMapping("/admin/assignInstructor")
    public String assignInstructor(Model model, @RequestParam(name = "courseId") String courseId) {
        ICourseAbstractFactory courseAbstractFactory = CourseInjector.instance().getCourseAbstractFactory();
        IUserCourses userCourses = courseAbstractFactory.createUserCoursesInstance();
        ICourse course = courseAbstractFactory.createCourseInstance();
        Log.info("Fetching course details using courseId " + courseId + " from the Database");
        ICourse courseById = course.getCourseById(courseId);
        ArrayList<IUser> allUsersCurrentlyNotInstructors = userCourses
                .usersCurrentlyNotInstructorsForCourse(courseId);
        Log.info("Fetching instructors list for a course using courseId " + courseId);
        ArrayList<IUser> instructorList = userCourses.getInstructorsForCourse(courseId);
        model.addAttribute("instructorList", instructorList);
        model.addAttribute("course", courseById);
        model.addAttribute("users", allUsersCurrentlyNotInstructors);
        return "course/assignInstructor";
    }

    @PostMapping("/admin/assignInstructor")
    public String assignInstructorToCourse(@RequestParam(name = "instructor") Long instructor,
                                           @RequestParam(name = "id") String courseId, Model model) {
        ICourseAbstractFactory courseAbstractFactory = CourseInjector.instance().getCourseAbstractFactory();
        IUserCourses userCourses = courseAbstractFactory.createUserCoursesInstance();
        ICourse course = courseAbstractFactory.createCourseInstance();
        Log.info("Fetching course details by using course Id " + courseId);
        ICourse courseById = course.getCourseById(courseId);
        Log.info("Assign/Add instructor to a course " + courseId);
        boolean success = userCourses.addInstructorsToCourse(instructor, courseId);
        Log.info("Fetch instructor for a course by id " + courseId);
        ArrayList<IUser> instructorList = userCourses.getInstructorsForCourse(courseId);
        model.addAttribute("instructorList", instructorList);
        model.addAttribute("course", courseById);
        if (success) {
            model.addAttribute("success", DomainConstants.instructorAddSuccess);
        } else {
            model.addAttribute("failure", DomainConstants.instructorAddFailure);
        }
        return "redirect:/assignInstructor?courseId=" + courseId;

    }
}
