package br.com.iriscareapi.repositories;

import br.com.iriscareapi.entities.Exam;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ExamRepository extends JpaRepository<Exam, Long> {

    Page<Exam> findAllByChildId(Long id, Pageable pageable);

    boolean existsExamByChildId(Long id);
}
