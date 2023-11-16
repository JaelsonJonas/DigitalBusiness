package br.com.iriscareapi.services;

import br.com.iriscareapi.dto.ChildInsertDTO;
import br.com.iriscareapi.dto.UserInsertDTO;
import br.com.iriscareapi.entities.Address;
import br.com.iriscareapi.entities.Child;
import br.com.iriscareapi.entities.Phone;
import br.com.iriscareapi.entities.User;
import br.com.iriscareapi.exception.ObjectNotFoundException;
import br.com.iriscareapi.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ChildService childService;

    @Autowired
    private AddressService addressService;

    @Autowired
    private PhoneService phoneService;

    public User findById(Long id) throws ObjectNotFoundException {
        return userRepository
                .findById(id).orElseThrow(() -> new ObjectNotFoundException("User with id " + id + " not found."));
    }

    public void registerUser(UserInsertDTO userInsertDTO) {
        User user = new User(userInsertDTO);
        Address address = new Address(userInsertDTO.getAddressDTO());
        Phone phone = new Phone(userInsertDTO.getPhoneDTO());

        address.setUser(user);
        addressService.insertAddress(address);

        phone.setUser(user);
        phoneService.insertPhone(phone);

        user.setAddress(address);
        user.setPhone(phone);
        userRepository.saveAndFlush(user);
    }

    public void insertChild(ChildInsertDTO childInsertDTO, Long userId) throws ObjectNotFoundException {
        User user = findById(userId);
        Child child = new Child(childInsertDTO);
        child.setUser(user);
        childService.insertChild(child);
        user.addChild(child);
        userRepository.save(user);
    }

}
