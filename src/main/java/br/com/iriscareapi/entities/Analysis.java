package br.com.iriscareapi.entities;

import br.com.iriscareapi.dto.analysis.AnalysisInsertDTO;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDate;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Table(name = "tb_ic_analysis")
public class Analysis {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @CreationTimestamp
    @JsonFormat(pattern = "dd/MM/yyyy HH:mm")
    private LocalDate date;

    @Column(name = "analysis_img", nullable = false)
    @Lob
    private String image;

    private Boolean leucocoria;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.DETACH)
    private Child child;

    public Analysis(AnalysisInsertDTO analysisInsertDTO) {
        this.image = analysisInsertDTO.getImage();
        this.leucocoria = analysisInsertDTO.getLeucocoria();
    }

}
