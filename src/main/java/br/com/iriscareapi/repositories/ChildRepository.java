package br.com.iriscareapi.repositories;

import br.com.iriscareapi.entities.Child;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChildRepository extends JpaRepository<Child, Long> {

}
