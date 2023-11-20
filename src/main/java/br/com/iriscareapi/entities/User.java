package br.com.iriscareapi.entities;

import br.com.iriscareapi.dto.UserInsertDTO;
import br.com.iriscareapi.utils.DateUtils;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.br.CPF;

import java.time.LocalDate;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Entity
@Table(name = "tb_ic_user")
@SequenceGenerator(name = "user", sequenceName = "SQ_TB_USER", allocationSize = 1)
public class User {

    @Id
    @GeneratedValue(generator = "user", strategy = GenerationType.SEQUENCE)
    @Column(name = "id_user")
    private Long id;

    @Column(name = "user_name", length = 50, nullable = false)
    private String name;

    @Column(name = "user_cpf", nullable = false, length = 14)
    @CPF
    private String cpf;

    @Column(name = "user_birthday")
    @JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDate birthday;

    @Column(name = "user_email", nullable = false)
    @Email
    private String email;

    @Column(name = "user_password", nullable = false, length = 100)
    private String password;

    private Boolean active;

    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JsonIgnoreProperties("user")
    private Phone phone;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Address address;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "user", fetch = FetchType.EAGER)
    private List<Child> children;

    public void addChild(Child child) {
        this.children.add(child);
    }

    public User(UserInsertDTO userInsertDTO) throws Exception {
        this.name = userInsertDTO.getName();
        this.cpf = userInsertDTO.getCpf();
        this.birthday = DateUtils.parseString(userInsertDTO.getBirthday());
        this.email = userInsertDTO.getEmail();
        this.password = userInsertDTO.getPassword();
        this.active = true;
    }

}
