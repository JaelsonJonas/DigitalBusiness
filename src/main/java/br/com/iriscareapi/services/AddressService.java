package br.com.iriscareapi.services;

import br.com.iriscareapi.entities.Address;
import br.com.iriscareapi.repositories.AddressRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AddressService {

    @Autowired
    private AddressRepository addressRepository;

    public void insertAddress(Address address) {
        addressRepository.saveAndFlush(address);
    }
}
