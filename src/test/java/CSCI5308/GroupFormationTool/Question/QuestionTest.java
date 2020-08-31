package CSCI5308.GroupFormationTool.Question;

import CSCI5308.GroupFormationTool.Common.DomainConstants;
import CSCI5308.GroupFormationTool.User.ITestUserAbstractFactory;
import CSCI5308.GroupFormationTool.User.IUser;
import CSCI5308.GroupFormationTool.User.TestUserInjector;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.sql.Date;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@SpringBootTest
public class QuestionTest {

    public QuestionAdminRepository questionAdminRepository;

    public QuestionManagerRepository questionManagerRepository;

    private ITestQuestionAbstractFactory questionAbstractFactoryTest = TestQuestionInjector.instance().
            getQuestionAbstractFactory();

    private ITestUserAbstractFactory userAbstractFactoryTest = TestUserInjector.instance().
            getUserAbstractFactory();

    @BeforeEach
    public void init() {
        questionManagerRepository = questionAbstractFactoryTest.createQuestionManagerRepositoryMock();
        QuestionInjector.instance().setQuestionManagerRepository(questionManagerRepository);
        questionAdminRepository = questionAbstractFactoryTest.createQuestionAdminRepositoryMock();
        QuestionInjector.instance().setQuestionAdminRepository(questionAdminRepository);
    }

    public IQuestion createDefaultQuestion() {
        QuestionDBMock questionDBMock = questionAbstractFactoryTest.createQuestionDBMock();
        IQuestion question = loadQuestion(questionDBMock);
        return question;
    }

    public IQuestion loadQuestion(QuestionDBMock questionDBMock) {
        IQuestion question = questionAbstractFactoryTest.createQuestionInstance();
        question = questionDBMock.loadQuestion(question);
        return question;
    }

    @Test
    public void getIdTest() {
        IQuestion question = createDefaultQuestion();
        assertEquals(1, question.getId());
    }

    @Test
    public void setIdTest() {
        IQuestion question = questionAbstractFactoryTest.createQuestionInstance();
        question.setId(2);
        assertEquals(2, question.getId());
    }

    @Test
    public void getInstructorTest() {
        IQuestion question = createDefaultQuestion();
        assertEquals("Test", question.getInstructor().getFirstName());
    }

    @Test
    public void setInstructorTest() {
        IQuestion question = questionAbstractFactoryTest.createQuestionInstance();
        question.setInstructor(userAbstractFactoryTest.createUserInstance());
        assertEquals(-1, question.getInstructor().getId());
    }

    @Test
    public void getTitleTest() {
        IQuestion question = createDefaultQuestion();
        assertEquals("Sample", question.getTitle());
    }

    @Test
    public void setTitleTest() {
        IQuestion question = questionAbstractFactoryTest.createQuestionInstance();
        question.setTitle("New Title");
        assertEquals("New Title", question.getTitle());
    }

    @Test
    public void getTextTest() {
        IQuestion question = createDefaultQuestion();
        assertEquals("Sample question", question.getText());
    }

    @Test
    public void setTextTest() {
        IQuestion question = questionAbstractFactoryTest.createQuestionInstance();
        question.setText("New Text");
        assertEquals("New Text", question.getText());
    }

    @Test
    public void getTypeTest() {
        IQuestion question = createDefaultQuestion();
        assertEquals(1, question.getType());
    }

    @Test
    public void setTypeTest() {
        IQuestion question = questionAbstractFactoryTest.createQuestionInstance();
        question.setType(2);
        assertEquals(2, question.getType());
    }

    @Test
    public void getCreatedDateTest() {
        IQuestion question = createDefaultQuestion();
        assertEquals("1969-12-31", question.getCreatedDate().toString());
    }

    @Test
    public void setCreatedDateTest() {
        IQuestion question = questionAbstractFactoryTest.createQuestionInstance();
        Date date = questionAbstractFactoryTest.createDateInstance(0);
        question.setCreatedDate(date);
        assertEquals("1969-12-31", question.getCreatedDate().toString());
    }

    @Test
    public void getChoicesTest() {
        IQuestion question = createDefaultQuestion();
        assertEquals(1, question.getChoices().size());
    }

    @Test
    public void setChoicesTest() {
        IQuestion question = questionAbstractFactoryTest.createQuestionInstance();
        ArrayList<IChoice> choices = questionAbstractFactoryTest.createChoiceListInstance();
        IChoice choice = questionAbstractFactoryTest.createChoiceInstance();
        choice.setText("sample");
        choice.setValue(1);
        choices.add(choice);
        question.setChoices(choices);
        assertEquals(1, question.getChoices().size());
    }

    @Test
    void createQuestionTest() {
        IQuestion question = questionAbstractFactoryTest.createQuestionInstance();
        IUser user = userAbstractFactoryTest.createUserInstance();
        user.setEmailId("padmeshdonthu@gmail.com");
        question.setCreatedDate(questionAbstractFactoryTest.createDateInstance(System.currentTimeMillis()));
        question.setId(1);
        question.setInstructor(user);
        question.setText("Spring text");
        question.setTitle("Spring title");
        question.setType(DomainConstants.MCQOne);
        ArrayList<String> optionText = questionAbstractFactoryTest.createListInstance();
        optionText.add("Test");
        optionText.add("Sample");
        ArrayList<String> optionValue = questionAbstractFactoryTest.createListInstance();
        optionValue.add("1");
        optionValue.add("2");
        when(questionManagerRepository.createQuestion(question)).thenReturn((long) 1);
        assertFalse(question.createQuestion(optionText, optionValue) == 0);
        assertTrue(question.createQuestion(optionText, optionValue) == 1);
        optionText.add("Sample");
        optionValue.add("3");
        assertFalse(question.createQuestion(optionText, optionValue) == 0);
        assertTrue(question.createQuestion(optionText, optionValue) == 1);
        when(questionManagerRepository.createQuestion(question)).thenReturn(DomainConstants.invalidData);
        assertFalse(question.createQuestion(questionAbstractFactoryTest.createListInstance()
                , optionValue) == 1);
        assertTrue(question.createQuestion(optionText, optionValue)
                == DomainConstants.invalidData);
        question.setType(DomainConstants.numeric);
        when(questionManagerRepository.createQuestion(question)).thenReturn((long) 1);
        assertFalse(question.createQuestion
                (questionAbstractFactoryTest.createListInstance(), questionAbstractFactoryTest.createListInstance()) == 0);
        assertTrue(question.createQuestion(questionAbstractFactoryTest.createListInstance()
                , questionAbstractFactoryTest.createListInstance()) == 1);
        question.setText("");
        when(questionManagerRepository.createQuestion(question)).thenReturn(DomainConstants.invalidData);
        assertFalse(question.createQuestion(questionAbstractFactoryTest.createListInstance(),
                questionAbstractFactoryTest.createListInstance()) == 1);
        assertTrue(question.createQuestion(questionAbstractFactoryTest.createListInstance(),
                questionAbstractFactoryTest.createListInstance()) == DomainConstants.invalidData);
    }

    @Test
    void deleteQuestionTest() {
        long questionId = 1;
        IQuestion question = questionAbstractFactoryTest.createQuestionInstance();
        when(questionManagerRepository.deleteQuestion(questionId)).thenReturn(true);
        assertTrue(question.deleteQuestion(questionId));
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
        question.setCreatedDate(questionAbstractFactoryTest.createDateInstance(System.currentTimeMillis()));
        question.setId(1);
        question.setInstructor(user);
        question.setText("Spring text");
        question.setTitle("Spring title");
        question.setType(DomainConstants.MCQOne);
        question.setChoices(choices);
        questions.add(question);
        question = questionAbstractFactoryTest.createQuestionInstance();
        question.setCreatedDate(questionAbstractFactoryTest.createDateInstance(System.currentTimeMillis()));
        question.setId(2);
        question.setInstructor(user);
        question.setText("Sample text");
        question.setTitle("Sample title");
        question.setType(DomainConstants.numeric);
        question.setChoices(null);
        questions.add(question);
        when(questionAdminRepository.getQuestionListForInstructor(user.getEmailId())).thenReturn(questions);
        assertTrue(question.getQuestionListForInstructor(user.getEmailId()) != null);
        assertTrue(question.getQuestionListForInstructor(user.getEmailId()).size() == 2);
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
        question.setCreatedDate(questionAbstractFactoryTest.createDateInstance(System.currentTimeMillis()));
        question.setId(questionId);
        question.setInstructor(user);
        question.setText("Spring text");
        question.setTitle("Spring title");
        question.setType(DomainConstants.MCQOne);
        when(questionAdminRepository.getQuestionById(questionId)).thenReturn(question);
        when(questionAdminRepository.getOptionsForTheQuestion(questionId)).thenReturn(choices);
        assertFalse(questionAdminRepository.getQuestionById(questionId) == null);
        assertTrue(question.getQuestionById(questionId).getText().equals("Spring text"));
    }

    @Test
    void getSortedQuestionListForInstructorTest() {
        String sortByField = "title";
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
        question.setCreatedDate(questionAbstractFactoryTest.createDateInstance(System.currentTimeMillis()));
        question.setId(2);
        question.setInstructor(user);
        question.setText("Sample text");
        question.setTitle("Sample title");
        question.setType(DomainConstants.numeric);
        question.setChoices(null);
        questions.add(question);
        question.setCreatedDate(questionAbstractFactoryTest.createDateInstance(System.currentTimeMillis()));
        question.setId(1);
        question.setInstructor(user);
        question.setText("Spring text");
        question.setTitle("Spring title");
        question.setType(DomainConstants.MCQOne);
        question.setChoices(choices);
        questions.add(question);
        when(questionAdminRepository.getSortedQuestionListForInstructor(sortByField, user.getEmailId())).
                thenReturn(questions);
        assertFalse(question.getSortedQuestionListForInstructor(sortByField, user.getEmailId()).
                isEmpty());
        assertTrue(question.getSortedQuestionListForInstructor(sortByField, user.getEmailId()).
                size() == 2);
    }
}
