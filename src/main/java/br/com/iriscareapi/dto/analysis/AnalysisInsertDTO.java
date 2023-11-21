package br.com.iriscareapi.dto.analysis;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class AnalysisInsertDTO {

    @NotNull @NotBlank
    private String image;

    private Boolean leucocoria;

}
