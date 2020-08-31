package CSCI5308.GroupFormationTool.Question;

import CSCI5308.GroupFormationTool.User.IUser;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

public interface IQuestion {

    long getId();

    void setId(long id);

    IUser getInstructor();

    void setInstructor(IUser instructor);

    String getTitle();

    void setTitle(String title);

    String getText();

    void setText(String text);

    int getType();

    void setType(int type);

    Date getCreatedDate();

    void setCreatedDate(Date createdDate);

    ArrayList<IChoice> getChoices();

    void setChoices(ArrayList<IChoice> choices);

    long createQuestion(List<String> optionText, List<String> optionValue);

    boolean deleteQuestion(long questionId);

    ArrayList<IQuestion> getQuestionListForInstructor(String emailId);

    IQuestion getQuestionById(long questionId);

    ArrayList<IQuestion> getSortedQuestionListForInstructor(String emailId, String sortBy);

}
