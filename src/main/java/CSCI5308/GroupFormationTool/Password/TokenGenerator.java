package CSCI5308.GroupFormationTool.Password;

import java.util.UUID;

public class TokenGenerator implements ITokenGenerator {

    @Override
    public String generator() {
        return UUID.randomUUID().toString();
    }
}
