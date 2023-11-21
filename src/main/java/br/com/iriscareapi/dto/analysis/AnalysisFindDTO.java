package br.com.iriscareapi.dto.analysis;

import br.com.iriscareapi.entities.Analysis;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class AnalysisFindDTO {

    private String date;

    private String image;

    private Boolean leucocoria;

    public AnalysisFindDTO(Analysis analysis) {
        this.date = analysis.getDate().toString();
        this.image = analysis.getImage();
        this.leucocoria = analysis.getLeucocoria();
    }

}
