package br.com.iriscareapi.repositories;

import br.com.iriscareapi.entities.Address;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AddressRepository extends JpaRepository<Address, Long> {
}
