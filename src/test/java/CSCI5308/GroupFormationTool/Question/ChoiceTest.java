package CSCI5308.GroupFormationTool.Question;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class ChoiceTest {

    private ITestQuestionAbstractFactory questionAbstractFactoryTest = TestQuestionInjector.instance().
            getQuestionAbstractFactory();

    public IChoice createDefaultChoice() {
        ChoiceDBMock choiceDBMock = questionAbstractFactoryTest.createChoiceDBMock();
        IChoice choice = loadChoice(choiceDBMock);
        return choice;
    }

    public IChoice loadChoice(ChoiceDBMock choiceDBMock) {
        IChoice choice = questionAbstractFactoryTest.createChoiceInstance();
        choice = choiceDBMock.loadChoice(choice);
        return choice;
    }

    @Test
    public void getText() {
        IChoice choice = createDefaultChoice();
        assertEquals("Amateur", choice.getText());
    }

    @Test
    public void setText() {
        IChoice choice = questionAbstractFactoryTest.createChoiceInstance();
        choice.setText("Beginner");
        assertEquals("Beginner", choice.getText());
    }

    @Test
    public void getValue() {
        IChoice choice = createDefaultChoice();
        assertEquals(1, choice.getValue());
    }

    @Test
    public void setValue() {
        IChoice choice = questionAbstractFactoryTest.createChoiceInstance();
        choice.setValue(2);
        assertEquals(2, choice.getValue());
    }
}
