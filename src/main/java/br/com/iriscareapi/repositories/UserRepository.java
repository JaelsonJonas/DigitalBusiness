package br.com.iriscareapi.repositories;

import br.com.iriscareapi.entities.User;
import br.com.iriscareapi.exception.EntityRegisterException;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

}
