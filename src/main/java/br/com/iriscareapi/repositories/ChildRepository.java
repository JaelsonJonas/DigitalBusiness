package br.com.iriscareapi.repositories;

import br.com.iriscareapi.entities.Child;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ChildRepository extends JpaRepository<Child, Long> {

    List<Child> findAllByUserId(Long id);

    List<Child> findAllByUserIdAndActiveIsTrue(Long id);

    List<Child> findAllByUserIdAndActiveIsFalse(Long id);

    boolean existsChildByUserId(Long id);

}
