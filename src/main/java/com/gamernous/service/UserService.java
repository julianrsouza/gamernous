package com.gamernous.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.gamernous.dto.UserDTO;
import com.gamernous.entity.User;
import com.gamernous.repository.UserRepository;

@Service
public class UserService {

    @Autowired
    private UserRepository repository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    public void save(User user) {
        if (!user.getPassword().isEmpty()) {
            String userPassword = user.getPassword();
            user.setPassword(passwordEncoder.encode(userPassword));
        }
        repository.save(user);
    }

    public Optional<UserDTO> getById(Long id) {
        return repository.findById(id).map(user -> new UserDTO(user));
    }

    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        User exampleUser = new User();
        exampleUser.setLogin(login);

        ExampleMatcher matcher = ExampleMatcher.matching()
            .withIgnorePaths("id", "name", "email", "password")
            .withMatcher("login", ExampleMatcher.GenericPropertyMatchers.exact());

        Example<User> example = Example.of(exampleUser, matcher);

        User user = repository.findOne(example).orElseThrow(() -> new UsernameNotFoundException("User not found"));

        return org.springframework.security.core.userdetails.User.builder()
            .username(user.getLogin())
            .password(user.getPassword())
            .roles("USER")
            .build();
    }
}
