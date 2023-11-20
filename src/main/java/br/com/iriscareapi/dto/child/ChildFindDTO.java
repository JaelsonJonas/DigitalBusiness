package br.com.iriscareapi.dto.child;

import br.com.iriscareapi.dto.exam.ExamFindDTO;
import br.com.iriscareapi.entities.Child;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ChildFindDTO {

    String name;

    String cpf;

    String birthday;

    Boolean active;

    List<ExamFindDTO> exams;

    public ChildFindDTO(Child child) {
        this.name = child.getName();
        this.cpf = child.getCpf();
        this.birthday = child.getBirthday().toString();
        this.active = child.getActive();
        this.exams = child.getExams().stream().map(ExamFindDTO::new).collect(Collectors.toList());
    }

}
