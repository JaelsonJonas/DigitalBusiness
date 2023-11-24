package br.com.iriscareapi.validation;


import br.com.iriscareapi.dto.auth.LoginRequest;
import br.com.iriscareapi.entities.User;
import br.com.iriscareapi.exception.BadRequestException;
import br.com.iriscareapi.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class LoginIsActiveValidation implements LoginValidation {

    @Autowired
    private UserRepository userRepository;

    public void validate(LoginRequest loginRequest) {

        Optional<User> user = userRepository.findByEmail(loginRequest.email());

        if (user.isPresent() && !user.get().getActive())
            throw new BadRequestException("Login is inactive");

    }
}
