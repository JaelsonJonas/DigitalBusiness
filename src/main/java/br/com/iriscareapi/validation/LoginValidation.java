package br.com.iriscareapi.validation;

import br.com.iriscareapi.dto.auth.LoginRequest;

public interface LoginValidation {

    void validate(LoginRequest loginRequest);


}
