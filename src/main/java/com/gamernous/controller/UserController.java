package com.gamernous.controller;

import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.gamernous.dto.UserDTO;
import com.gamernous.entity.User;
import com.gamernous.service.UserService;

@RestController
@RequestMapping("/api/user")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<Void> save(@RequestBody UserDTO userDTO) {
        User user = userDTO.toUser();
        userService.save(user);
        return ResponseEntity.ok().build();
    }

    @GetMapping
    public ResponseEntity<UserDTO> getById(@RequestParam Long id) {
        Optional<UserDTO> dtoOptional = userService.getById(id);
        if (dtoOptional.isPresent()) {
            return ResponseEntity.ok(dtoOptional.get());
        }
        return ResponseEntity.notFound().build();
    }

}
