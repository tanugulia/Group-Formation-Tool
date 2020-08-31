package CSCI5308.GroupFormationTool.Question;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class QuestionAbstractFactory implements IQuestionAbstractFactory {

    @Override
    public IQuestion createQuestionInstance() {
        return new Question();
    }

    @Override
    public ArrayList<IQuestion> createQuestionListInstance() {
        return new ArrayList<IQuestion>();
    }

    @Override
    public IChoice createChoiceInstance() {
        return new Choice();
    }

    @Override
    public ArrayList<IChoice> createChoiceListInstance() {
        return new ArrayList<IChoice>();
    }

    @Override
    public IQuestionManagerRepository createQuestionManagerRepository() {
        return new QuestionManagerRepository();
    }

    @Override
    public IQuestionAdminRepository createQuestionAdminRepository() {
        return new QuestionAdminRepository();
    }

    @Override
    public Set<String> createSetInstance() {
        return new HashSet<String>();
    }
}
