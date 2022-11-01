package com.example.ankets.security;

import com.example.ankets.model.Role;
import com.example.ankets.model.User;
import com.example.ankets.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByLogin(username);
    }

    public boolean addUser(User user) {
        User usFromDb = userRepository.findByLogin(user.getLogin());
        if (usFromDb != null) {
            return false;
        }
        user.setActive(true);
        user.setRole(Role.USER);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
        return true;
    }

    public boolean createAdmin() {
        User usFromDb = userRepository.findByLogin("admin");
        if (usFromDb != null) {
            return false;
        }
        User admin = new User();
        admin.setLogin("admin");
        admin.setActive(true);
        admin.setRole(Role.ADMIN);
        admin.setName("admin");
        admin.setPassword(passwordEncoder.encode("admin"));
        userRepository.save(admin);
        return true;
    }

    public boolean isUserAdmin(User user) {
        return user.getRole() == Role.ADMIN;
    }

}
