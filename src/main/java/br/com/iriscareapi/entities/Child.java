package br.com.iriscareapi.entities;

import br.com.iriscareapi.dto.ChildInsertDTO;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.br.CPF;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Entity
@Table(name = "tb_ic_child")
@SequenceGenerator(name = "child", sequenceName = "SQ_TB_CHILD", allocationSize = 1)
public class Child {

    @Id
    @GeneratedValue(generator = "child", strategy = GenerationType.SEQUENCE)
    @Column(name = "id_child")
    private Long id;

    @Column(name = "child_name", length = 50, nullable = false)
    private String name;

    @Column(name = "child_cpf", nullable = false, length = 14)
    //@JsonFormat(pattern = "###.###.###-##")
    @CPF
    private String cpf;

    @Column(name = "child_birthday")
    @JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDate birthday;

    private Boolean active;

    @ManyToOne
    @JsonIgnore
    private User user;

    public Child(ChildInsertDTO dto) {
        this.name = dto.getName();
        this.cpf = dto.getCpf();
        this.birthday = LocalDate.parse(dto.getBirthday());
        this.active = true;
    }

}
