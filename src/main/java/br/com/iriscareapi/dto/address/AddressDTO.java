package br.com.iriscareapi.dto.address;

import br.com.iriscareapi.entities.Address;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class AddressDTO {

    @Length(max = 9) @NotNull @NotBlank
    @JsonFormat(pattern = "nnnnn-nnn")
    private String zipCode;

    @NotNull @NotBlank
    private String number;

    @Length(max = 50) @NotNull @NotBlank
    private String street;

    @Length(max = 50) @NotNull @NotBlank
    private String neighborhood;

    @Length(max = 50) @NotNull @NotBlank
    private String city;

    @Length(max = 50) @NotNull @NotBlank
    private String state;

    public AddressDTO(Address address) {
        this.zipCode = address.getZipCode();
        this.number = address.getNumber();
        this.street = address.getStreet();
        this.neighborhood = address.getNeighborhood();
        this.city = address.getCity();
        this.state = address.getState();
    }

}
