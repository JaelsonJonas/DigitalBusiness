package br.com.iriscareapi.entities;

import br.com.iriscareapi.dto.exam.ExamInsertDTO;
import br.com.iriscareapi.utils.DateUtils;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.Objects;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
@Table(name = "tb_ic_exam")
@SequenceGenerator(sequenceName = "seq_exam", allocationSize = 1, name = "exam")
public class Exam {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "exam")
    private Long id;

    @Column(name = "exam_name", nullable = false, length = 40)
    private String name;

    @Column(name = "exam_date")
    @JsonFormat(pattern = "dd/MM/yyyy HH:mm")
    private LocalDate date;

    @Column(name = "exam_desc", length = 250)
    private String description;

    @Column(name = "exam_img")
    private String image;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.DETACH)
    private Child child;

    public Exam(ExamInsertDTO examInsertDTO) throws Exception {
        this.name = examInsertDTO.getName();
        this.date = DateUtils.parseString(examInsertDTO.getDate());
        if (Objects.nonNull(examInsertDTO.getDescription())) {
            this.description = examInsertDTO.getDescription();
        }
    }

}
