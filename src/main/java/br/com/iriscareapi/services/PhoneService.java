package br.com.iriscareapi.services;

import br.com.iriscareapi.entities.Phone;
import br.com.iriscareapi.repositories.PhoneRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PhoneService {

    @Autowired
    private PhoneRepository phoneRepository;

    public void insertPhone(Phone phone) {
        phoneRepository.saveAndFlush(phone);
    }

}
