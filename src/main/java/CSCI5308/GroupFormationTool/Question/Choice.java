package CSCI5308.GroupFormationTool.Question;

public class Choice implements IChoice {

    private String text;

    private int value;

    public Choice() {
        text = null;
        value = -1;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

}
