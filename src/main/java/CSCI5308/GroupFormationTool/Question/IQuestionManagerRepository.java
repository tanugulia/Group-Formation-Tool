package CSCI5308.GroupFormationTool.Question;

public interface IQuestionManagerRepository {

    long createQuestion(IQuestion question);

    boolean deleteQuestion(long questionId);
}
