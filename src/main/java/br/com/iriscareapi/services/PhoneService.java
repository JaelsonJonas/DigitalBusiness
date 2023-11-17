package br.com.iriscareapi.services;

import br.com.iriscareapi.dto.PhoneUpdateDTO;
import br.com.iriscareapi.entities.Phone;
import br.com.iriscareapi.exception.EntityRegisterException;
import br.com.iriscareapi.exception.ObjectNotFoundException;
import br.com.iriscareapi.repositories.PhoneRepository;
import br.com.iriscareapi.utils.DataUtils;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class PhoneService {

    @Autowired
    private PhoneRepository phoneRepository;

    public Phone findById(Long id) throws ObjectNotFoundException {
        return phoneRepository
                .findById(id).orElseThrow(() -> new ObjectNotFoundException("Phone with id " + id + " not found."));
    }

    public void savePhone(Phone phone) {
        try {
            phoneRepository.saveAndFlush(phone);
        } catch (Exception e) {
            throw new EntityRegisterException("Phone", e.getMessage());
        }
    }

    public void updatePhone(Long phoneId, PhoneUpdateDTO phoneUpdateDTO) throws ObjectNotFoundException {
        Phone phone = findById(phoneId);

        HashMap<Object, Object> fields = new HashMap<>();
        fields.put(phone.getDDD(),phoneUpdateDTO.getDDD());
        fields.put(phone.getNumber(), phoneUpdateDTO.getNumber());

        DataUtils.dataUpdate(phone, fields);
        savePhone(phone);
    }

}
