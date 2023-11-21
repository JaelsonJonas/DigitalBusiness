package br.com.iriscareapi.dto.exam;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ExamUpdateDTO {

    @Length(min = 1, max = 40)
    private String name;

    @JsonFormat(pattern = "dd/MM/yyyy HH:mm")
    private String date;

    @Length(max = 250)
    private String description;

    private String image;

}
