package br.com.iriscareapi.controllers;

import br.com.iriscareapi.entities.User;
import br.com.iriscareapi.exception.ResourceNotFoundException;
import br.com.iriscareapi.repositories.UserRepository;
import br.com.iriscareapi.security.CurrentUser;
import br.com.iriscareapi.security.UserPrincipal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthController {

    @Autowired
    private UserRepository userRepository;


    @GetMapping("/user/me")
    @PreAuthorize("hasRole('USER')")
    public User getCurrentUser(@CurrentUser UserPrincipal userPrincipal) {
        return userRepository.findById(userPrincipal.getId()).orElseThrow(() -> new ResourceNotFoundException("User", "id", userPrincipal.getId()));
    }


}
