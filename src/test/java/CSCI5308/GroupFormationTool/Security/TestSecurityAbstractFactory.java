package CSCI5308.GroupFormationTool.Security;

import static org.mockito.Mockito.mock;

public class TestSecurityAbstractFactory implements ITestSecurityAbstractFactory {

    @Override
    public BCryptEncryption createBCryptEncryptionMock() {
        return mock(BCryptEncryption.class);
    }
}
