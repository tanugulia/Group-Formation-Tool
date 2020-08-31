package CSCI5308.GroupFormationTool.Question;

import java.sql.Date;
import java.util.ArrayList;

public interface ITestQuestionAbstractFactory {

    IQuestion createQuestionInstance();

    ArrayList<IQuestion> createQuestionListInstance();

    IChoice createChoiceInstance();

    ArrayList<IChoice> createChoiceListInstance();

    ChoiceDBMock createChoiceDBMock();

    QuestionDBMock createQuestionDBMock();

    QuestionAdminRepository createQuestionAdminRepositoryMock();

    QuestionManagerRepository createQuestionManagerRepositoryMock();

    Date createDateInstance(long date);

    ArrayList<String> createListInstance();
}
