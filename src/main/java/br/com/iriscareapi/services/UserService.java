package br.com.iriscareapi.services;

import br.com.iriscareapi.dto.ChildInsertDTO;
import br.com.iriscareapi.dto.UserInsertDTO;
import br.com.iriscareapi.dto.UserUpdateDTO;
import br.com.iriscareapi.entities.Address;
import br.com.iriscareapi.entities.Child;
import br.com.iriscareapi.entities.Phone;
import br.com.iriscareapi.entities.User;
import br.com.iriscareapi.exception.EntityRegisterException;
import br.com.iriscareapi.exception.ObjectNotFoundException;
import br.com.iriscareapi.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

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
        saveUser(user);
    }

    public void updateUser(UserUpdateDTO userUpdateDTO, Long id) throws ObjectNotFoundException {
        User user = findById(id);
        dataUpdate(user, userUpdateDTO);
        saveUser(user);
    }

    public void saveUser(User user) {
        try {
            userRepository.saveAndFlush(user);
        } catch (Exception e) {
            throw new EntityRegisterException("User", e.getMessage());
        }
    }

    public void dataUpdate(User userToAtt, UserUpdateDTO userUpdateDTO) {
        userToAtt.setName(validateUpdatedValue(userToAtt.getName(), userToAtt.getName()));

        userToAtt.setCpf(validateUpdatedValue(userToAtt.getCpf(), userToAtt.getCpf()));

        userToAtt.setBirthday(validateUpdatedValue(userToAtt.getBirthday(), LocalDate.parse(userUpdateDTO.getBirthday())));

        userToAtt.setEmail(validateUpdatedValue(userToAtt.getEmail(), userUpdateDTO.getEmail()));

        userToAtt.setPassword(validateUpdatedValue(userToAtt.getPassword(), userUpdateDTO.getPassword()));

    }

    public void registerNewChild(ChildInsertDTO childInsertDTO, Long userId) throws ObjectNotFoundException {
        User user = findById(userId);
        Child child = new Child(childInsertDTO);
        child.setUser(user);
        childService.saveChild(child);
        user.addChild(child);
        userRepository.save(user);
    }

    public static <T> T validateUpdatedValue(T defaultValue, T newValue) {
        return (newValue != null && !newValue.toString().isEmpty() && !newValue.toString().isBlank()) ? newValue : defaultValue;
    }

}
