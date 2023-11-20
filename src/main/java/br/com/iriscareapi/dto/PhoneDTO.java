package br.com.iriscareapi.dto;

import br.com.iriscareapi.entities.Phone;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class PhoneDTO {

    @NotNull @NotNull @Length(min = 1, max = 3)
    private String ddd;

    @NotNull @NotNull @Length(max = 9)
    private String number;

    public PhoneDTO(Phone phone) {
        this.ddd = phone.getDdd();
        this.number = phone.getNumber();
    }

}
