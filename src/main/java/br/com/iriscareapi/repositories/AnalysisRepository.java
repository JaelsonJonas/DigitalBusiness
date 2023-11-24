package br.com.iriscareapi.repositories;

import br.com.iriscareapi.entities.Analysis;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AnalysisRepository extends JpaRepository<Analysis, Long> {

    Page<Analysis> findAllByChildId(Long id, Pageable pageable);

    boolean existsAnalysisByChildId(Long id);

}
