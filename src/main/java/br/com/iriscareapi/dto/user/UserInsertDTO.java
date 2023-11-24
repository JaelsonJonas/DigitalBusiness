package br.com.iriscareapi.dto.user;

import br.com.iriscareapi.dto.address.AddressDTO;
import br.com.iriscareapi.dto.phone.PhoneDTO;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.br.CPF;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class UserInsertDTO {

    @NotNull @NotBlank @Length(max = 50)
    private String name;

    @NotNull @NotBlank
    @CPF
    private String cpf;

    @NotNull @NotBlank
    @JsonFormat(pattern = "dd/MM/yyyy")
    private String birthday;

    @NotNull @NotBlank
    @Email
    private String email;

    @NotNull @NotBlank @Length(max = 100)
    private String password;

    @NotNull
    private AddressDTO address;

    @NotNull
    private PhoneDTO phone;


}
