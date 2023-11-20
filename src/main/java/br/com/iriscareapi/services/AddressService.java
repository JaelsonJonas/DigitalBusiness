package br.com.iriscareapi.services;

import br.com.iriscareapi.dto.address.AddressUpdateDTO;
import br.com.iriscareapi.entities.Address;
import br.com.iriscareapi.exception.EntityRegisterException;
import br.com.iriscareapi.exception.ObjectNotFoundException;
import br.com.iriscareapi.repositories.AddressRepository;
import br.com.iriscareapi.utils.DataUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AddressService {

    @Autowired
    private AddressRepository addressRepository;

    public Address findById(Long id) throws ObjectNotFoundException {
        return addressRepository
                .findById(id).orElseThrow(() -> new ObjectNotFoundException("Address with id " + id + " not found."));
    }

    public void saveAddress(Address address) {
        try {
            addressRepository.save(address);
        } catch (Exception e) {
            throw new EntityRegisterException("Address", e.getMessage());
        }
    }

    public void updateAddress(Long adsId, AddressUpdateDTO addressUpdateDTO) throws Exception {
        Address address = findById(adsId);

        DataUtils.dataUpdate(address, addressUpdateDTO);

        saveAddress(address);
    }


}
