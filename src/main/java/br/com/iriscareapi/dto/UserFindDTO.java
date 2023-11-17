package br.com.iriscareapi.dto;

import br.com.iriscareapi.entities.Address;
import br.com.iriscareapi.entities.Child;
import br.com.iriscareapi.entities.Phone;
import br.com.iriscareapi.entities.User;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.br.CPF;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserFindDTO {

    private String name;

    private String cpf;

    private String birthday;

    private String email;

    private String password;

    private Boolean active;

    private PhoneDTO phone;

    private AddressDTO address;

    private List<ChildFindDTO> children;

    public UserFindDTO(User user) {
        this.name = user.getName();
        this.cpf = user.getCpf();
        this.birthday = user.getBirthday().toString();
        this.email = user.getEmail();
        this.password = user.getPassword();
        this.active = user.getActive();
        this.phone = new PhoneDTO(user.getPhone());
        this.address = new AddressDTO(user.getAddress());
        this.children = user.getChildren()
                        .stream().map(ch -> new ChildFindDTO(ch.getName(),
                                                            ch.getCpf(),
                                                            ch.getBirthday().toString(),
                                                            ch.getActive())).collect(Collectors.toList());
    }

}
