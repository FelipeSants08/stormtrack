package com.fiap.stormtrack.controller;

import com.fiap.stormtrack.model.User;
import com.fiap.stormtrack.model.UserRole;
import com.fiap.stormtrack.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("users")
public class UserController {

    @Autowired
    private UserRepository repository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @DeleteMapping("{id}")
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    public void destroy(@PathVariable Long id){
        repository.deleteById(id);
    }

    @PostMapping
    public User create(@RequestBody User user){
        user.setRole(UserRole.ADMIN);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return repository.save(user);
    }
}
