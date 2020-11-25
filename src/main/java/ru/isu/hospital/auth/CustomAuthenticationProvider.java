package ru.isu.hospital.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;
import ru.isu.hospital.model.Doctor;
import ru.isu.hospital.repository.DoctorRepository;

//Bean
@Component("customAuthenticationProvider")
public class CustomAuthenticationProvider implements AuthenticationProvider {

    @Autowired
    DoctorRepository repo;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        // введенные дпользователем данные
        UsernamePasswordAuthenticationToken token = (UsernamePasswordAuthenticationToken) authentication;
        // ищем его в БД
        Doctor user = repo.findByUsername(token.getName());
        if (user==null || !user.getPassword().equalsIgnoreCase(token.getCredentials().toString())){
            throw new BadCredentialsException("No user or password");
        }
        return new UsernamePasswordAuthenticationToken(user,user.getPassword(), user.getAuthorities());
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return UsernamePasswordAuthenticationToken.class.equals(aClass);
    }
}
