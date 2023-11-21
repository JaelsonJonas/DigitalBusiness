package br.com.iriscareapi.repositories;

import br.com.iriscareapi.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface UserRepository extends JpaRepository<User, Long> {

    @Query("SELECT CASE WHEN COUNT(c) > 0 THEN true ELSE false END FROM User u " +
            "JOIN u.children c WHERE u.id = :userId AND c.id = :childId")
    boolean checkIfUserHasChildWithGivenId(@Param("userId") Long userId, @Param("childId") Long childId);

}
