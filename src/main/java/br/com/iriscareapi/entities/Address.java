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
    private Integer number;

    @Column(length = 45, nullable = false)
    private String street;

    @Column(length = 35, nullable = false)
    private String city;

    private Boolean isMain = false;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.DETACH)
    @JsonIgnore
    private User user;

    public Address(String street, Integer number, String zipCode, String city, Boolean isMain) {
        this.street = street;
        this.number = number;
        this.zipCode = zipCode;
        this.city = city;
        this.isMain = isMain;
    }

}