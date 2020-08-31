package CSCI5308.GroupFormationTool.Password;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@SpringBootTest
public class PolicyTest {

    private IPolicy policyInstance;

    private PolicyRepository policyRepository;

    private ITestPasswordAbstractFactory passwordAbstractFactoryTest = TestPasswordInjector.instance().
            getPasswordAbstractFactory();

    @Test
    public void getIdTest() {
        IPolicy policy = passwordAbstractFactoryTest.createPolicyInstance();
        policy.setId(0);
        assertEquals(0, policy.getId());
    }

    @Test
    public void setIdTest() {
        IPolicy policy = passwordAbstractFactoryTest.createPolicyInstance();
        policy.setId(1);
        assertEquals(1, policy.getId());
    }

    @Test
    public void getSettingTest() {
        IPolicy policy = passwordAbstractFactoryTest.createPolicyInstance();
        assertNull(policy.getSetting());
        policy.setSetting("Minimum length");
        assertEquals("Minimum length", policy.getSetting());
    }

    @Test
    public void setSettingTest() {
        IPolicy policy = passwordAbstractFactoryTest.createPolicyInstance();
        policy.setSetting("Max length");
        assertEquals("Max length", policy.getSetting());
    }

    @Test
    public void getValueTest() {
        IPolicy policy = passwordAbstractFactoryTest.createPolicyInstance();
        assertNull(policy.getValue());
        policy.setValue("3");
        assertEquals("3", policy.getValue());
    }

    @Test
    public void setValueTest() {
        IPolicy policy = passwordAbstractFactoryTest.createPolicyInstance();
        policy.setValue("4");
        assertEquals("4", policy.getValue());
    }

    @Test
    public void getEnabledTest() {
        IPolicy policy = passwordAbstractFactoryTest.createPolicyInstance();
        policy.setEnabled(0);
        assertEquals(0, policy.getEnabled());
    }

    @Test
    public void setEnabledTest() {
        IPolicy policy = passwordAbstractFactoryTest.createPolicyInstance();
        policy.setEnabled(1);
        assertEquals(1, policy.getEnabled());
    }

    @BeforeEach
    public void init() {
        policyRepository = passwordAbstractFactoryTest.createPolicyRepositoryMock();
        PasswordInjector.instance().setPolicyRepository(policyRepository);
    }

    @Test
    public void passwordPolicyCheckTest() {
        policyInstance = passwordAbstractFactoryTest.createPolicyInstance();
        String password = "Padmes$1";
        ArrayList<IPolicy> policies = passwordAbstractFactoryTest.createPolicyListInstance();
        IPolicy policy = passwordAbstractFactoryTest.createPolicyInstance();
        policy.setId(0);
        policy.setEnabled(1);
        policy.setSetting("Minimum length");
        policy.setValue("3");
        policies.add(policy);
        policy = passwordAbstractFactoryTest.createPolicyInstance();
        policy.setId(1);
        policy.setEnabled(1);
        policy.setSetting("Maximum length");
        policy.setValue("9");
        policies.add(policy);
        policy = passwordAbstractFactoryTest.createPolicyInstance();
        policy.setId(2);
        policy.setEnabled(1);
        policy.setSetting("Minimum number of uppercase characters");
        policy.setValue("1");
        policies.add(policy);
        policy = passwordAbstractFactoryTest.createPolicyInstance();
        policy.setId(3);
        policy.setEnabled(1);
        policy.setSetting("Minimum number of lowercase characters");
        policy.setValue("1");
        policies.add(policy);
        policy = passwordAbstractFactoryTest.createPolicyInstance();
        policy.setId(4);
        policy.setEnabled(1);
        policy.setSetting("Minimum number of special characters or symbols");
        policy.setValue("1");
        policies.add(policy);
        policy = passwordAbstractFactoryTest.createPolicyInstance();
        policy.setId(5);
        policy.setEnabled(1);
        policy.setSetting("A set of characters that are not allowed");
        policy.setValue("#");
        policies.add(policy);
        when(policyRepository.passwordSPolicyCheck(password)).thenReturn(policies);
        assertTrue(policyInstance.passwordSPolicyCheck(password) == null);
        String errorMessage = "Minimum length of password should be 3";
        password = "pa";
        when(policyRepository.passwordSPolicyCheck(password)).thenReturn(policies);
        assertTrue(policyInstance.passwordSPolicyCheck(password).equals(errorMessage));
        errorMessage = "Maximum length of password should be 9";
        password = "padmeshA12333444";
        when(policyRepository.passwordSPolicyCheck(password)).thenReturn(policies);
        assertTrue(policyInstance.passwordSPolicyCheck(password).equals(errorMessage));
        errorMessage = "Minimum number of uppercase characters in password should be 1";
        password = "padmesh1";
        when(policyRepository.passwordSPolicyCheck(password)).thenReturn(policies);
        assertTrue(policyInstance.passwordSPolicyCheck(password).equals(errorMessage));
        errorMessage = "Minimum number of lowercase characters in password should be 1";
        password = "PADMESH1";
        when(policyRepository.passwordSPolicyCheck(password)).thenReturn(policies);
        assertTrue(policyInstance.passwordSPolicyCheck(password).equals(errorMessage));
        errorMessage = "Minimum number of symbols or special characters in password should be 1";
        password = "PaDMESH1";
        when(policyRepository.passwordSPolicyCheck(password)).thenReturn(policies);
        assertTrue(policyInstance.passwordSPolicyCheck(password).equals(errorMessage));
        errorMessage = "# not allowed in password ";
        password = "PaDMESH1#";
        when(policyRepository.passwordSPolicyCheck(password)).thenReturn(policies);
        assertTrue(policyInstance.passwordSPolicyCheck(password).equals(errorMessage));
    }

    @Test
    public void getPoliciesTest() {
        policyInstance = passwordAbstractFactoryTest.createPolicyInstance();
        ArrayList<IPolicy> policies = passwordAbstractFactoryTest.createPolicyListInstance();
        IPolicy policy = passwordAbstractFactoryTest.createPolicyInstance();
        policy.setId(0);
        policy.setEnabled(1);
        policy.setSetting("Min length");
        policy.setValue("3");
        policies.add(policy);
        when(policyRepository.getPolicies()).thenReturn(policies);
        assertTrue(policyInstance.getPolicies().size() == 1);
        when(policyRepository.getPolicies()).thenReturn(null);
        assertFalse(policyInstance.getPolicies() instanceof ArrayList);
    }

}
