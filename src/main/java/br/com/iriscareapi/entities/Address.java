package br.com.iriscareapi.entities;

import br.com.iriscareapi.dto.address.AddressDTO;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "tb_ic_address")
@SequenceGenerator(sequenceName = "seq_address", allocationSize = 1, name = "address")
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "address")
    private Long id;

    @Column(length = 15, nullable = false)
    @JsonFormat(pattern = "#####-###")
    private String zipCode;

    @Column(nullable = false, name = "house_number")
    private String number;

    @Column(length = 50, nullable = false)
    private String street;

    @Column(length = 50, nullable = false)
    private String neighborhood;

    @Column(length = 50, nullable = false)
    private String city;

    @Column(length = 50, nullable = false)
    private String state;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.DETACH)
    @JsonIgnore
    private User user;

    public Address(AddressDTO addressDTO) {
        this.zipCode = addressDTO.getZipCode();
        this.number = addressDTO.getNumber();
        this.street = addressDTO.getStreet();
        this.neighborhood = addressDTO.getNeighborhood();
        this.city = addressDTO.getCity();
        this.state = addressDTO.getState();
    }

}