package br.com.iriscareapi.dto.exam;

import br.com.iriscareapi.entities.Exam;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ExamFindDTO {

    private String name;

    private String date;

    private String description;

    public ExamFindDTO(Exam exam) {
        this.name = exam.getName();
        this.date = exam.getDate().toString();
        this.description = exam.getDescription();
    }

}
