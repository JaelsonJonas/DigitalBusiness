package br.com.iriscareapi.validation;

import br.com.iriscareapi.dto.auth.LoginRequest;
import br.com.iriscareapi.exception.BadRequestException;
import br.com.iriscareapi.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Component
public class LoginExistsValidation implements LoginValidation {

    @Autowired
    private UserRepository userRepository;

    public void validate(LoginRequest loginRequest) {

        if (!userRepository.existsByEmail(loginRequest.email()))
            throw new BadRequestException("Login not found");

    }
}
