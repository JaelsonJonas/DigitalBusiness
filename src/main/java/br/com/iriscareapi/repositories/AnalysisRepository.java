package br.com.iriscareapi.repositories;

import br.com.iriscareapi.entities.Analysis;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AnalysisRepository extends JpaRepository<Analysis, Long> {

    List<Analysis> findAllByChildId(Long id);

    boolean existsAnalysisByChildId(Long id);

}
