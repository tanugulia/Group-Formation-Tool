package CSCI5308.GroupFormationTool.Question;

import CSCI5308.GroupFormationTool.Common.DomainConstants;
import CSCI5308.GroupFormationTool.User.ITestUserAbstractFactory;
import CSCI5308.GroupFormationTool.User.IUser;
import CSCI5308.GroupFormationTool.User.TestUserInjector;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
public class QuestionManagerRepositoryTest {

    private ITestQuestionAbstractFactory questionAbstractFactoryTest = TestQuestionInjector.instance().
            getQuestionAbstractFactory();

    private ITestUserAbstractFactory userAbstractFactoryTest = TestUserInjector.instance().
            getUserAbstractFactory();

    @Test
    void createQuestionTest() {
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
        question.setCreatedDate(questionAbstractFactoryTest.createDateInstance(System.currentTimeMillis()));
        question.setId(1);
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
        assertTrue(question.getChoices() != null);
        assertTrue(question.getId() == 1);
        assertTrue(question.getText().equals("Spring text"));
        assertTrue(question.getTitle().equals("Spring title"));
        assertTrue(question.getInstructor().getEmailId().equals("padmeshdonthu@gmail.com"));
        assertTrue(question.getType() == DomainConstants.MCQOne);
    }


    @Test
    void deleteQuestionTest() {
        IQuestion question = questionAbstractFactoryTest.createQuestionInstance();
        long questionId = 2;
        question.setId(questionId);
        question.setText("Sample text");
        question.setTitle("Sample title");
        question.setType(DomainConstants.numeric);
        question.setChoices(null);
        assertTrue(question.getId() == questionId);
        assertFalse(question.getId() == 3);
        assertTrue(question.getChoices() == null);
    }

}
