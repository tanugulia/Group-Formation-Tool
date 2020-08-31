package CSCI5308.GroupFormationTool.Password;

import java.util.ArrayList;

public interface IPolicy {

    int getId();

    void setId(int id);

    String getSetting();

    void setSetting(String setting);

    String getValue();

    void setValue(String value);

    int getEnabled();

    void setEnabled(int enabled);

    ArrayList<IPolicy> getPolicies();

    String passwordSPolicyCheck(String password);

}
