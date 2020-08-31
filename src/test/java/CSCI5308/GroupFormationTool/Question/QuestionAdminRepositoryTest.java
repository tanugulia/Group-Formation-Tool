package CSCI5308.GroupFormationTool.Question;

import CSCI5308.GroupFormationTool.Common.DomainConstants;
import CSCI5308.GroupFormationTool.User.ITestUserAbstractFactory;
import CSCI5308.GroupFormationTool.User.IUser;
import CSCI5308.GroupFormationTool.User.TestUserInjector;
import CSCI5308.GroupFormationTool.User.User;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.sql.Date;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
public class QuestionAdminRepositoryTest {

    private ITestQuestionAbstractFactory questionAbstractFactoryTest = TestQuestionInjector.instance().
            getQuestionAbstractFactory();

    private ITestUserAbstractFactory userAbstractFactoryTest = TestUserInjector.instance().
            getUserAbstractFactory();

    @Test
    void getOptionsForTheQuestionTest() {
        ArrayList<IChoice> choices = questionAbstractFactoryTest.createChoiceListInstance();
        IChoice choice = questionAbstractFactoryTest.createChoiceInstance();
        choice.setText("Amateur");
        choice.setValue(1);
        choices.add(choice);
        choice = questionAbstractFactoryTest.createChoiceInstance();
        choice.setText("Beginner");
        choice.setValue(2);
        choices.add(choice);
        assertTrue(choices.get(0).getText().length() < 100);
        assertTrue(choices.get(0).getValue() < 100);
        assertFalse(choices.get(0).getValue() == 0);
        assertFalse(choices.get(0).getText().isEmpty());
        assertTrue(choices.get(0).getText().equals("Amateur"));
        assertTrue(choices.get(0).getValue() == 1);
        assertTrue(choices.get(1).getText().length() < 100);
        assertTrue(choices.get(1).getValue() < 100);
        assertFalse(choices.get(1).getValue() == 0);
        assertFalse(choices.get(1).getText().isEmpty());
        assertTrue(choices.get(1).getText().equals("Beginner"));
        assertTrue(choices.get(1).getValue() == 2);
        assertFalse(choices.isEmpty());
        assertTrue(choices.size() == 2);
        choices = questionAbstractFactoryTest.createChoiceListInstance();
        assertTrue(choices.isEmpty());
        assertFalse(choices.size() > 2);
    }

    @Test
    void getQuestionByIdTest() {
        long questionId = 1;
        IQuestion question = questionAbstractFactoryTest.createQuestionInstance();
        ArrayList<IChoice> choices = questionAbstractFactoryTest.createChoiceListInstance();
        IChoice choice = questionAbstractFactoryTest.createChoiceInstance();
        choice.setText("Amateur");
        choice.setValue(1);
        choices.add(choice);
        choice = questionAbstractFactoryTest.createChoiceInstance();
        choice.setText("Beginner");
        choice.setValue(2);
        choices.add(choice);
        IUser user = userAbstractFactoryTest.createUserInstance();
        user.setEmailId("padmeshdonthu@gmail.com");
        question.setCreatedDate(new Date(System.currentTimeMillis()));
        question.setId(questionId);
        question.setInstructor(user);
        question.setText("Spring text");
        question.setTitle("Spring title");
        question.setType(DomainConstants.MCQOne);
        question.setChoices(choices);
        assertTrue(question.getText().length() < 200);
        assertTrue(question.getTitle().length() < 100);
        assertTrue(question.getId() < 10);
        assertTrue(question.getInstructor().getEmailId().length() < 100);
        assertTrue(question.getChoices().size() < 100);
        assertTrue(question.getType() < 10);
        assertFalse(question.getCreatedDate() == null);
        assertFalse(question.getId() == 0);
        assertFalse(question.getTitle().isEmpty());
        assertFalse(question.getText().isEmpty());
        assertFalse(question.getChoices().isEmpty());
        assertFalse(question.getInstructor() == null);
        assertFalse(question.getType() == DomainConstants.MCQMultiple);
        assertTrue(question.getChoices().size() == 2);
        assertTrue(question.getId() == questionId);
        assertTrue(question.getText().equals("Spring text"));
        assertTrue(question.getTitle().equals("Spring title"));
        assertTrue(question.getInstructor().getEmailId().equals("padmeshdonthu@gmail.com"));
        assertTrue(question.getType() == DomainConstants.MCQOne);
    }

    @Test
    void getQuestionListForInstructorTest() {
        IQuestion question = questionAbstractFactoryTest.createQuestionInstance();
        ArrayList<IChoice> choices = questionAbstractFactoryTest.createChoiceListInstance();
        ArrayList<IQuestion> questions = questionAbstractFactoryTest.createQuestionListInstance();
        IChoice choice = questionAbstractFactoryTest.createChoiceInstance();
        choice.setText("Amateur");
        choice.setValue(1);
        choices.add(choice);
        choice = questionAbstractFactoryTest.createChoiceInstance();
        choice.setText("Beginner");
        choice.setValue(2);
        choices.add(choice);
        IUser user = userAbstractFactoryTest.createUserInstance();
        user.setEmailId("padmeshdonthu@gmail.com");
        question.setCreatedDate(new Date(System.currentTimeMillis()));
        question.setId(1);
        question.setInstructor(user);
        question.setText("Spring text");
        question.setTitle("Spring title");
        question.setType(DomainConstants.MCQOne);
        question.setChoices(choices);
        questions.add(question);
        question = questionAbstractFactoryTest.createQuestionInstance();
        question.setCreatedDate(new Date(System.currentTimeMillis()));
        question.setId(2);
        question.setInstructor(user);
        question.setText("Sample text");
        question.setTitle("Sample title");
        question.setType(DomainConstants.numeric);
        question.setChoices(null);
        questions.add(question);
        assertTrue(questions.get(0).getText().length() < 200);
        assertTrue(questions.get(0).getTitle().length() < 100);
        assertTrue(questions.get(0).getId() < 10);
        assertTrue(questions.get(0).getInstructor().getEmailId().length() < 100);
        assertTrue(questions.get(0).getChoices().size() < 100);
        assertTrue(questions.get(0).getType() < 10);
        assertFalse(questions.get(0).getCreatedDate() == null);
        assertFalse(questions.get(0).getId() == 0);
        assertFalse(questions.get(0).getTitle().isEmpty());
        assertFalse(questions.get(0).getText().isEmpty());
        assertFalse(questions.get(0).getChoices().isEmpty());
        assertFalse(questions.get(0).getInstructor() == null);
        assertFalse(questions.get(0).getType() == DomainConstants.MCQMultiple);
        assertTrue(questions.get(0).getChoices() instanceof ArrayList);
        assertTrue(questions.get(0).getId() == 1);
        assertTrue(questions.get(0).getText() instanceof String);
        assertTrue(questions.get(0).getTitle() instanceof String);
        assertTrue(questions.get(0).getInstructor() instanceof User);
        assertTrue(questions.get(0).getType() == DomainConstants.MCQOne);
        assertTrue(questions.get(0).getCreatedDate() instanceof Date);
        assertTrue(questions.get(1).getText().length() < 200);
        assertTrue(questions.get(1).getTitle().length() < 100);
        assertTrue(questions.get(1).getId() < 10);
        assertTrue(questions.get(1).getInstructor().getEmailId().length() < 100);
        assertTrue(questions.get(1).getChoices() == null);
        assertTrue(questions.get(1).getType() < 10);
        assertFalse(questions.get(1).getCreatedDate() == null);
        assertFalse(questions.get(1).getId() == 0);
        assertFalse(questions.get(1).getTitle().isEmpty());
        assertFalse(questions.get(1).getText().isEmpty());
        assertFalse(questions.get(1).getChoices() instanceof ArrayList);
        assertFalse(questions.get(1).getInstructor() == null);
        assertFalse(questions.get(1).getType() == DomainConstants.MCQOne);
        assertTrue(questions.get(1).getId() == 2);
        assertTrue(questions.get(1).getText() instanceof String);
        assertTrue(questions.get(1).getTitle() instanceof String);
        assertTrue(questions.get(1).getInstructor() instanceof User);
        assertTrue(questions.get(1).getType() == DomainConstants.numeric);
        assertTrue(questions.get(1).getCreatedDate() instanceof Date);
        assertFalse(questions.isEmpty());
        assertTrue(questions instanceof ArrayList);
    }

    @Test
    void getSortedQuestionListForInstructorTest() {
        String sortBy = "title";
        IQuestion question;
        ArrayList<IChoice> choices = questionAbstractFactoryTest.createChoiceListInstance();
        ArrayList<IQuestion> questions = questionAbstractFactoryTest.createQuestionListInstance();
        IChoice choice = questionAbstractFactoryTest.createChoiceInstance();
        choice.setText("Amateur");
        choice.setValue(1);
        choices.add(choice);
        choice = questionAbstractFactoryTest.createChoiceInstance();
        choice.setText("Beginner");
        choice.setValue(2);
        choices.add(choice);
        IUser user = userAbstractFactoryTest.createUserInstance();
        user.setEmailId("padmeshdonthu@gmail.com");
        question = questionAbstractFactoryTest.createQuestionInstance();
        question.setCreatedDate(new Date(System.currentTimeMillis()));
        question.setId(2);
        question.setInstructor(user);
        question.setText("Sample text");
        question.setTitle("Sample title");
        question.setType(DomainConstants.numeric);
        question.setChoices(null);
        questions.add(0, question);
        question = questionAbstractFactoryTest.createQuestionInstance();
        question.setCreatedDate(new Date(System.currentTimeMillis()));
        question.setId(1);
        question.setInstructor(user);
        question.setText("Spring text");
        question.setTitle("Spring title");
        question.setType(DomainConstants.MCQOne);
        question.setChoices(choices);
        questions.add(1, question);
        assertTrue(questions.get(1).getText().length() < 200);
        assertTrue(questions.get(1).getTitle().length() < 100);
        assertTrue(questions.get(1).getId() < 10);
        assertTrue(questions.get(1).getInstructor().getEmailId().length() < 100);
        assertTrue(questions.get(1).getChoices().size() < 100);
        assertTrue(questions.get(1).getType() < 10);
        assertFalse(questions.get(1).getCreatedDate() == null);
        assertFalse(questions.get(1).getId() == 0);
        assertFalse(questions.get(1).getTitle().isEmpty());
        assertFalse(questions.get(1).getText().isEmpty());
        assertFalse(questions.get(1).getChoices().isEmpty());
        assertFalse(questions.get(1).getInstructor() == null);
        assertFalse(questions.get(1).getType() == DomainConstants.MCQMultiple);
        assertTrue(questions.get(1).getId() == 1);
        assertTrue(questions.get(1).getType() == DomainConstants.MCQOne);
        assertTrue(questions.get(0).getText().length() < 200);
        assertTrue(questions.get(0).getTitle().length() < 100);
        assertTrue(questions.get(0).getId() < 10);
        assertTrue(questions.get(0).getInstructor().getEmailId().length() < 100);
        assertTrue(questions.get(0).getType() < 10);
        assertTrue(questions.get(0).getChoices() == null);
        assertFalse(questions.get(0).getCreatedDate() == null);
        assertFalse(questions.get(0).getId() == 0);
        assertFalse(questions.get(0).getTitle().isEmpty());
        assertFalse(questions.get(0).getText().isEmpty());
        assertFalse(questions.get(0).getInstructor() == null);
        assertFalse(questions.get(0).getType() == DomainConstants.MCQOne);
        assertTrue(questions.get(0).getId() == 2);
        assertTrue(questions.get(0).getInstructor() instanceof User);
        assertTrue(questions.get(0).getType() == DomainConstants.numeric);
        assertFalse(questions.isEmpty());
    }
}
