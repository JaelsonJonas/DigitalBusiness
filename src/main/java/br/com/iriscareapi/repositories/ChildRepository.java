package br.com.iriscareapi.repositories;

import br.com.iriscareapi.entities.Child;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ChildRepository extends JpaRepository<Child, Long> {

    List<Child> findAllByUserId(Long id);

    List<Child> findAllByUserIdAndActiveIsTrue(Long id);

    List<Child> findAllByUserIdAndActiveIsFalse(Long id);

    boolean existsChildByUserId(Long id);

    @Query("SELECT CASE WHEN COUNT(c) > 0 THEN true ELSE false END FROM Child c " +
            "JOIN c.exams exms WHERE c.id = :childId AND exms.id = :examId")
    boolean checkIfChildHasExamWithGivenId(@Param("childId") Long childId, @Param("examId") Long examId);

    @Query("SELECT c.id FROM User u JOIN u.children c WHERE u.id = :userId")
    List<Long> findChildIdsByUserId(@Param("userId") Long userId);

}
