package br.com.iriscareapi.repositories;

import br.com.iriscareapi.entities.Exam;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ExamRepository extends JpaRepository<Exam, Long> {

    List<Exam> findAllByChildId(Long id);

    boolean existsExamByChildId(Long id);
}
