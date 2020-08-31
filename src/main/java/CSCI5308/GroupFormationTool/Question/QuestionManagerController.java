package CSCI5308.GroupFormationTool.Question;

import CSCI5308.GroupFormationTool.Common.DomainConstants;
import CSCI5308.GroupFormationTool.User.IUser;
import CSCI5308.GroupFormationTool.User.IUserAbstractFactory;
import CSCI5308.GroupFormationTool.User.UserInjector;
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
import java.util.List;

@Controller
public class QuestionManagerController {

    private static final Logger log = LoggerFactory.getLogger(QuestionManagerController.class.getName());

    @GetMapping("/questionManager/createQuestion")
    public String createQuestion(Model model) {
        return "question/createQuestion";
    }

    @PostMapping("/questionManager/createQuestion")
    public String saveQuestion(Model model, @RequestParam("questionTitle") String title,
                               @RequestParam("questionText") String text, @RequestParam("questionType") String type,
                               @RequestParam("optionText") List<String> optionText,
                               @RequestParam("optionValue") List<String> optionValue) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        long outcome;
        IQuestionAbstractFactory questionAbstractFactory = QuestionInjector.instance().getQuestionAbstractFactory();
        IUserAbstractFactory userAbstractFactory = UserInjector.instance().getUserAbstractFactory();
        IQuestion question = questionAbstractFactory.createQuestionInstance();
        IUser instructor = userAbstractFactory.createUserInstance();
        question.setText(text);
        question.setTitle(title);
        question.setType(Integer.parseInt(type));
        instructor.setEmailId(authentication.getPrincipal().toString());
        question.setInstructor(instructor);
        log.info("Saving the question created by the instructor to the database");
        outcome = question.createQuestion(optionText, optionValue);
        if (outcome == DomainConstants.invalidData) {
            log.info("One or more input fields have invalid/empty data");
            model.addAttribute("invalidData",
                    "One or more fields have invalid/empty data! Please enter and try again");
            return "question/createQuestion";
        } else {
            log.info("Question is saved to the database and the instructor views the saved question");
            return "redirect:/questionManager/viewQuestion?questionId=" + outcome;
        }
    }

    @GetMapping("/questionManager/deleteQuestion")
    public String deleteQuestion(@RequestParam("questionId") long questionId, Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        IQuestionAbstractFactory questionAbstractFactory = QuestionInjector.instance().getQuestionAbstractFactory();
        IQuestion question = questionAbstractFactory.createQuestionInstance();
        log.info("Deleting the question created by the instructor from the database");
        boolean status = question.deleteQuestion(questionId);
        if (status) {
            log.info("The Question " + questionId + " is successfully deleted!");
            model.addAttribute("successMessage",
                    "The question " + questionId + " is successfully deleted!");
        } else {
            log.info("The question could not be deleted");
            model.addAttribute("failureMessage", "The question can not not be deleted.");
        }
        String emailId = authentication.getPrincipal().toString();
        log.info("Redirecting to the instructor's question list page");
        ArrayList<IQuestion> questionList = question.getQuestionListForInstructor(emailId);
        model.addAttribute("questionList", questionList);
        return "question/questionManager";
    }

}
