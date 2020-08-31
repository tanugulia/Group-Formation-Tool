package CSCI5308.GroupFormationTool.Database;

import CSCI5308.GroupFormationTool.Common.DomainConstants;
import org.springframework.stereotype.Service;

@Service
public class DBConfiguration implements IDBConfiguration {

    @Override
    public String getDBUserName() {
        return DomainConstants.USER;
    }

    @Override
    public String getDBPassword() {
        return DomainConstants.PASSWORD;
    }

    @Override
    public String getDBURL() {
        return DomainConstants.URL;
    }
}
