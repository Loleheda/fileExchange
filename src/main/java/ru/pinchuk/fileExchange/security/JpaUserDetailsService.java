package ru.pinchuk.fileExchange.security;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.pinchuk.fileExchange.entity.User;
import ru.pinchuk.fileExchange.repository.UserRepository;


@Service
public class JpaUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    public JpaUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        System.out.println(username);
        User currentUser = userRepository.findByLogin(username);
        System.out.println(currentUser);
        return new SecurityUser(currentUser);
    }
}