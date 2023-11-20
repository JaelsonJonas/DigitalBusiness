package br.com.iriscareapi.config;

import br.com.iriscareapi.dto.address.AddressDTO;
import br.com.iriscareapi.dto.child.ChildInsertDTO;
import br.com.iriscareapi.dto.phone.PhoneDTO;
import br.com.iriscareapi.dto.user.UserInsertDTO;
import br.com.iriscareapi.services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DataBaseSeeder implements CommandLineRunner {

    @Autowired
    UserService userService;

    @Autowired
    ChildService childService;

    @Autowired
    ExamService examService;

    @Override
    public void run(String... args) throws Exception {

        UserInsertDTO user = UserInsertDTO.builder()
                .name("Name 1")
                .cpf("750.239.930-51")
                .email("nerys4358@uorak.com")
                .password("123")
                .birthday("12/04/2000")
                .phone(new PhoneDTO("55", "999999999"))
                .address(new AddressDTO("55555-555",
                                           "55",
                                           "Rua",
                                           "Bairro",
                                           "cidade",
                                           "estado"))
                .build();

        userService.registerUser(user);

        ChildInsertDTO child = ChildInsertDTO.builder()
                .name("Zezé")
                .cpf("752.106.910-20")
                .birthday("08/05/2005")
                .build();

        userService.registerNewChild(child, 1L);

    }
}
