package br.com.iriscareapi.dto.user;

import br.com.iriscareapi.dto.address.AddressDTO;
import br.com.iriscareapi.dto.child.ChildFindDTO;
import br.com.iriscareapi.dto.phone.PhoneDTO;
import br.com.iriscareapi.entities.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

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
                        .stream().map(ChildFindDTO::new).toList();
    }

}
