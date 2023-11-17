package br.com.iriscareapi.entities;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
@Table(name = "tb_ic_exam")
public class Exam {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "exam_name", nullable = false, length = 40)
    private String name;

    @Column(name = "exam_date")
    @JsonFormat(pattern = "dd/MM/yyyy HH:mm")
    private LocalDate date;

    @Column(name = "exam_desc", length = 250)
    private String description;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.DETACH)
    private Child child;

}
