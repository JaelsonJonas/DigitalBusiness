package br.com.iriscareapi.entities;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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
    private Long id;

    @Column(name = "user_name", length = 50, nullable = false)
    private String name;

    @Column(name = "user_cpf", nullable = false, length = 14)
    @JsonFormat(pattern = "###.###.###-##")
    private String cpf;

    private Boolean active;

    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private Phone phone;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Address address;

    @OneToMany
    private List<Child> children;

}
