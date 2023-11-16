package br.com.iriscareapi.entities;

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
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 9, nullable = false)
    @JsonFormat(pattern = "nnnnn-nnn")
    private String zipCode;

    @Column(nullable = false)
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

    public Address(String zipCode, String street, String number, String city, String state) {
        this.zipCode = zipCode;
        this.street = street;
        this.number = number;
        this.city = city;
    }

}