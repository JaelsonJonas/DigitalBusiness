package br.com.iriscareapi.entities;

import br.com.iriscareapi.dto.phone.PhoneDTO;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@Entity
@Table(name = "tb_ic_phone")
public class Phone {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "phone_ddd", nullable = false, length = 3)
    private String ddd;

    @Column(name = "phone_nmb", nullable = false, length = 9)
    private String number;

    @OneToOne(cascade = CascadeType.DETACH, fetch = FetchType.LAZY)
    @JsonIgnore
    @JsonIgnoreProperties("phone")
    private User user;

    public Phone(PhoneDTO phoneDTO) {
        this.ddd = phoneDTO.getDdd();
        this.number = phoneDTO.getNumber();
    }
}
