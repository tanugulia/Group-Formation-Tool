package CSCI5308.GroupFormationTool.Security;

import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.ArrayList;
import java.util.List;

public interface ISecurityAbstractFactory {

    CustomAuthenticationManager createCustomAuthenticationManager();

    BCryptPasswordEncoder createBCryptPasswordEncoder();

    SimpleGrantedAuthority createSimpleGrantedAuthority(String role);

    ArrayList<GrantedAuthority> createGrantedAuthorityListInstance();

    UsernamePasswordAuthenticationToken createUsernamePasswordAuthenticationToken(Object principal,
                                                                                  Object credentials,
                                                                                  List<GrantedAuthority> grantedAuthorities);

    BadCredentialsException createBadCredentialsExceptionInstance(String value);

    AuthenticationServiceException createAuthenticationServiceExceptionInstance(String value);

    BCryptEncryption createBCryptEncryptionInstance();
}
