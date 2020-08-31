package CSCI5308.GroupFormationTool.Question;

import java.util.ArrayList;
import java.util.Set;

public interface IQuestionAbstractFactory {

    IQuestion createQuestionInstance();

    ArrayList<IQuestion> createQuestionListInstance();

    IChoice createChoiceInstance();

    ArrayList<IChoice> createChoiceListInstance();

    IQuestionManagerRepository createQuestionManagerRepository();

    IQuestionAdminRepository createQuestionAdminRepository();

    Set<String> createSetInstance();
}
