package br.com.iriscareapi.dto.exam;

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
public class ExamInsertDTO {

    @NotNull @NotBlank
    @Length(min = 1, max = 40)
    private String name;

    @NotNull @NotBlank
    @JsonFormat(pattern = "dd/MM/yyyy HH:mm")
    private String date;

    @Length(max = 50)
    private String description;

}
