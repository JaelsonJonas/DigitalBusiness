package br.com.iriscareapi.dto.child;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.br.CPF;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ChildUpdateDTO {

    @Length(max = 50)
    private String name;

    @Length(max = 14)
    @CPF
    //@JsonFormat(pattern = "###.###.###-##")
    private String cpf;

    @JsonFormat(pattern = "dd/MM/yyyy")
    private String birthday;

}
