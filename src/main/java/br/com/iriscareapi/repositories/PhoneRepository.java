package br.com.iriscareapi.repositories;

import br.com.iriscareapi.entities.Phone;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PhoneRepository extends JpaRepository<Phone, Long> {
}
