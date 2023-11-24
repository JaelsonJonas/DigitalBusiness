package br.com.iriscareapi.dto.user;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.Email;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.br.CPF;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserUpdateDTO {

    @Length(max = 50)
    private String name;

    @CPF
    private String cpf;

    @JsonFormat(pattern = "dd/MM/yyyy")
    private String birthday;

    @Email
    private String email;

    @Length(max = 100)
    private String password;
}
