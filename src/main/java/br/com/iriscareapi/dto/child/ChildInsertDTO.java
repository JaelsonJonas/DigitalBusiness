package br.com.iriscareapi.dto.child;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.br.CPF;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class ChildInsertDTO {

    @Length(max = 50) @NotNull @NotBlank
    private String name;

    @NotBlank @NotNull
    @CPF
    //@JsonFormat(pattern = "###.###.###-##")
    private String cpf;

    @NotBlank @NotNull
    @JsonFormat(pattern = "dd/MM/yyyy")
    private String birthday;

}
