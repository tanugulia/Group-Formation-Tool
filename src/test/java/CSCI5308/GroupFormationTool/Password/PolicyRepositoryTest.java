package CSCI5308.GroupFormationTool.Password;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
public class PolicyRepositoryTest {

    private ITestPasswordAbstractFactory passwordAbstractFactoryTest = TestPasswordInjector.instance().
            getPasswordAbstractFactory();

    @Test
    public void passwordSPolicyCheckTest() {
        IPolicy policy = passwordAbstractFactoryTest.createPolicyInstance();
        policy.setId(1);
        policy.setSetting("Maximum length");
        policy.setValue("9");
        policy.setEnabled(1);

        assertFalse(policy.getId() < 0);
        assertFalse(policy.getValue().isEmpty());
        assertTrue(policy.getSetting().equals("Maximum length"));
    }

    @Test
    public void getPoliciesTest() {
        ArrayList<IPolicy> policyList = passwordAbstractFactoryTest.createPolicyListInstance();

        IPolicy policy = passwordAbstractFactoryTest.createPolicyInstance();
        policy.setId(1);
        policy.setSetting("Maximum length");
        policy.setValue("9");
        policy.setEnabled(1);
        policyList.add(policy);

        policy = passwordAbstractFactoryTest.createPolicyInstance();
        policy.setId(2);
        policy.setSetting("Minimum length");
        policy.setValue("3");
        policy.setEnabled(1);
        policyList.add(policy);

        assertTrue(policyList.get(0).getId() > 0);
        assertTrue(policyList.get(0).getSetting().equals("Maximum length"));
        assertTrue(policyList.get(0).getValue().length() > 0);

        assertFalse(policyList.get(0).getSetting().isEmpty());
        assertFalse(policyList.get(0).getId() < 0);
        assertFalse(policyList.get(0).getEnabled() == 0);
        assertTrue(policyList.get(0).getId() == 1);

        assertFalse(policyList.isEmpty());
        assertTrue(policyList.size() == 2);
    }
}
