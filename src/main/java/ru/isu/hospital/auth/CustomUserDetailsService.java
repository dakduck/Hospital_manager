package ru.isu.hospital.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import ru.isu.hospital.model.Doctor;
import ru.isu.hospital.repository.DoctorRepository;


@Component("customUserDetailsService")
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private DoctorRepository repo;

    // если не хочется париться со своей аутентификацией
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Doctor user = repo.findByUsername(username);
        return user;
    }

}
