package CSCI5308.GroupFormationTool.Question;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;

@Controller
public class QuestionAdminController {

    private static final Logger log = LoggerFactory.getLogger(QuestionAdminController.class.getName());

    ArrayList<IQuestion> questionList = null;

    @GetMapping("/questionManager/questionManager")
    public String questionList(Model model) {
        IQuestionAbstractFactory questionAbstractFactory = QuestionInjector.instance().getQuestionAbstractFactory();
        IQuestion question = questionAbstractFactory.createQuestionInstance();
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String emailId = authentication.getPrincipal().toString();
        log.info("Fetching the question bank for the logged in instructor with email id " + emailId);
        ArrayList<IQuestion> questionList = question.getQuestionListForInstructor(emailId);
        model.addAttribute("questionList", questionList);
        return "question/questionManager";
    }

    @GetMapping("/questionManager/viewQuestion")
    public String viewQuestion(@RequestParam("questionId") long questionId, Model model) {
        IQuestionAbstractFactory questionAbstractFactory = QuestionInjector.instance().getQuestionAbstractFactory();
        IQuestion question = questionAbstractFactory.createQuestionInstance();
        log.info("Fetching the question by its id" + questionId + "for the current instructor to view");
        question = question.getQuestionById(questionId);
        model.addAttribute("question", question);
        return "question/viewQuestion";
    }

    @GetMapping("/questionManager/sortQuestion")
    public String sortQuestion(@RequestParam("sortby") String sortField, Model model) {
        IQuestionAbstractFactory questionAbstractFactory = QuestionInjector.instance().getQuestionAbstractFactory();
        IQuestion question = questionAbstractFactory.createQuestionInstance();
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String emailId = authentication.getPrincipal().toString();
        log.info("Fetching the question bank sorted based on " + sortField + " for the logged in instructor");
        ArrayList<IQuestion> questionList = question.getSortedQuestionListForInstructor(emailId, sortField);
        model.addAttribute("questionList", questionList);
        return "question/questionManager";
    }

}
