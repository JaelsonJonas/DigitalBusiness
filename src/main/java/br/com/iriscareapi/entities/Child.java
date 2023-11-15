package br.com.iriscareapi.entities;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Entity
@Table(name = "tb_ic_child")
@SequenceGenerator(name = "child", sequenceName = "SQ_TB_CHILD", allocationSize = 1)
public class Child {

    @Id
    private Long id;

    @Column(name = "user_name", length = 50, nullable = false)
    private String name;

    @Column(name = "user_cpf", nullable = false, length = 14)
    @JsonFormat(pattern = "###.###.###-##")
    private String cpf;

    private Boolean active;

    @ManyToOne
    private User user;

}
