package br.com.iriscareapi.services;

import br.com.iriscareapi.dto.AddressUpdateDTO;
import br.com.iriscareapi.dto.PhoneUpdateDTO;
import br.com.iriscareapi.entities.Address;
import br.com.iriscareapi.entities.Phone;
import br.com.iriscareapi.exception.EntityRegisterException;
import br.com.iriscareapi.exception.ObjectNotFoundException;
import br.com.iriscareapi.repositories.AddressRepository;
import br.com.iriscareapi.utils.DataUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;

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
            addressRepository.saveAndFlush(address);
        } catch (Exception e) {
            throw new EntityRegisterException("Address", e.getMessage());
        }
    }

    public void updateAddress(Long adsId, AddressUpdateDTO addressUpdateDTO) throws ObjectNotFoundException {
        Address address = findById(adsId);

        HashMap<Object, Object> fields = new HashMap<>();
        fields.put(address.getZipCode(), addressUpdateDTO.getZipCode());
        fields.put(address.getNumber(), addressUpdateDTO.getNumber());
        fields.put(address.getStreet(), addressUpdateDTO.getStreet());
        fields.put(address.getNeighborhood(), addressUpdateDTO.getNeighborhood());
        fields.put(address.getCity(), addressUpdateDTO.getCity());
        fields.put(address.getState(), addressUpdateDTO.getState());

        DataUtils.dataUpdate(address, fields);
        saveAddress(address);
    }


}
