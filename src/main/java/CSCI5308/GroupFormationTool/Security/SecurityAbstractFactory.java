package CSCI5308.GroupFormationTool.Security;

import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.ArrayList;
import java.util.List;

public class SecurityAbstractFactory implements ISecurityAbstractFactory {

    @Override
    public CustomAuthenticationManager createCustomAuthenticationManager() {
        return new CustomAuthenticationManager();
    }

    @Override
    public BCryptPasswordEncoder createBCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    public SimpleGrantedAuthority createSimpleGrantedAuthority(String role) {
        return new SimpleGrantedAuthority(role);
    }

    @Override
    public ArrayList<GrantedAuthority> createGrantedAuthorityListInstance() {
        return new ArrayList<GrantedAuthority>();
    }

    @Override
    public UsernamePasswordAuthenticationToken createUsernamePasswordAuthenticationToken(
            Object principal, Object credentials, List<GrantedAuthority> grantedAuthorities) {
        return new UsernamePasswordAuthenticationToken(principal, credentials, grantedAuthorities);
    }

    @Override
    public BadCredentialsException createBadCredentialsExceptionInstance(String value) {
        return new BadCredentialsException(value);
    }

    @Override
    public AuthenticationServiceException createAuthenticationServiceExceptionInstance(String value) {
        return new AuthenticationServiceException(value);
    }

    @Override
    public BCryptEncryption createBCryptEncryptionInstance() {
        return new BCryptEncryption();
    }
}
