package com.gamernous.dto;

import com.gamernous.entity.User;

import lombok.Data;

@Data
public class UserDTO {
    private Long id;
    private String name;
    private String email;
    private String login;
    private String password;

    public UserDTO() {}

    public UserDTO(User user) {
        this.id = user.getId();
        this.name = user.getName();
        this.email = user.getEmail();
        this.login = user.getLogin();
    }

    public User toUser() {
        User user = new User();
        user.setId(id);
        user.setEmail(email);
        user.setName(name);
        user.setLogin(login);
        user.setPassword(password);
        return user;
    }
}
