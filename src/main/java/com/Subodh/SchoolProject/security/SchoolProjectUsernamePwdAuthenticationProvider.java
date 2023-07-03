package com.Subodh.SchoolProject.security;

import com.Subodh.SchoolProject.model.Person;
import com.Subodh.SchoolProject.model.Roles;
import com.Subodh.SchoolProject.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class SchoolProjectUsernamePwdAuthenticationProvider implements AuthenticationProvider {

    @Autowired
    private PersonRepository personRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {

        String email = authentication.getName();
        String pwd = authentication.getCredentials().toString();

        Person person = personRepository.readByEmail(email); // realByEmail is same as findByEmail(email)

        if (null != person && person.getPersonId() > 0 && passwordEncoder.matches(pwd,person.getPwd())) {
            return new UsernamePasswordAuthenticationToken(
                    email, null, getGrantedAuthorities(person.getRoles()));
        } else {
            throw new BadCredentialsException("Invalid Credentials");
        }
    }

    private List<GrantedAuthority> getGrantedAuthorities(Roles roles) {
        List<GrantedAuthority> grantedAuthorities = new ArrayList<>();
        grantedAuthorities.add(new SimpleGrantedAuthority("ROLE_" + roles.getRoleName()));
        return grantedAuthorities;
    }

    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }

}
