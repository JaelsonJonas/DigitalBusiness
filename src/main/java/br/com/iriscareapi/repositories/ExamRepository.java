package br.com.iriscareapi.repositories;

import br.com.iriscareapi.entities.Exam;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ExamRepository extends JpaRepository<Exam, Long> {
}
