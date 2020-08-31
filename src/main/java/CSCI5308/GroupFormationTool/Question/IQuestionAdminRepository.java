package CSCI5308.GroupFormationTool.Question;

import java.util.ArrayList;

public interface IQuestionAdminRepository {

    ArrayList<IQuestion> getQuestionListForInstructor(String emailId);

    ArrayList<IQuestion> getSortedQuestionListForInstructor(String emailId, String sortField);

    IQuestion getQuestionById(long questionId);

    ArrayList<IChoice> getOptionsForTheQuestion(long questionId);

}
