package br.com.iriscareapi.services;

import br.com.iriscareapi.dto.*;
import br.com.iriscareapi.entities.Address;
import br.com.iriscareapi.entities.Child;
import br.com.iriscareapi.entities.Phone;
import br.com.iriscareapi.entities.User;
import br.com.iriscareapi.exception.EntityRegisterException;
import br.com.iriscareapi.exception.ObjectNotFoundException;
import br.com.iriscareapi.repositories.UserRepository;
import br.com.iriscareapi.utils.DataUtils;
import br.com.iriscareapi.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

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

    public void registerUser(UserInsertDTO userInsertDTO) throws Exception {
        User user = new User(userInsertDTO);
        Address address = new Address(userInsertDTO.getAddress());
        Phone phone = new Phone(userInsertDTO.getPhone());

        saveUser(user);

        address.setUser(user);
        addressService.saveAddress(address);

        phone.setUser(user);
        phoneService.savePhone(phone);

        user.setAddress(address);
        user.setPhone(phone);
        saveUser(user);
    }

    public void updateUser(UserUpdateDTO userUpdateDTO, Long id) throws Exception {
        User user = findById(id);
        DataUtils.dataUpdate(user, userUpdateDTO);
        saveUser(user);
    }

    public void saveUser(User user) {
        try {
            userRepository.saveAndFlush(user);
        } catch (Exception e) {
            throw new EntityRegisterException("User", e.getMessage());
        }
    }

    public void dataUpdate(User userToAtt, UserUpdateDTO userUpdateDTO) throws Exception {
        userToAtt.setName(DataUtils.validateUpdatedValue(userToAtt.getName(), userToAtt.getName()));

        userToAtt.setCpf(DataUtils.validateUpdatedValue(userToAtt.getCpf(), userToAtt.getCpf()));

        userToAtt.setBirthday(DataUtils.validateUpdatedValue(userToAtt.getBirthday(), DateUtils.parseString(userUpdateDTO.getBirthday())));

        userToAtt.setEmail(DataUtils.validateUpdatedValue(userToAtt.getEmail(), userUpdateDTO.getEmail()));

        userToAtt.setPassword(DataUtils.validateUpdatedValue(userToAtt.getPassword(), userUpdateDTO.getPassword()));

    }

    public void registerNewChild(ChildInsertDTO childInsertDTO, Long userId) throws Exception {
        User user = findById(userId);
        Child child = new Child(childInsertDTO);
        child.setUser(user);
        childService.saveChild(child);
        user.addChild(child);
        saveUser(user);
    }

    public ChildFindDTO findChildById(Long childId, Long userId) throws ObjectNotFoundException {
        if (userHasChildWithGivenId(userId, childId))
            return new ChildFindDTO(childService.findById(childId));

        return null;
    }

    public List<ChildFindDTO> findAllChildrenByUserId(Long userId) throws ObjectNotFoundException {
        return childService.findAllByUserId(userId).stream()
                            .map(ChildFindDTO::new).collect(Collectors.toList());
    }

    public List<ChildFindDTO> findAllActiveChildrenByUserId(Long userId) throws ObjectNotFoundException {
        return childService.findAllActiveChildrenByUserId(userId).stream()
                .map(ChildFindDTO::new).collect(Collectors.toList());
    }

    public List<ChildFindDTO> findAllInactiveChildrenByUserId(Long userId) throws ObjectNotFoundException {
        return childService.findAllInactiveChildrenByUserId(userId).stream()
                .map(ChildFindDTO::new).collect(Collectors.toList());
    }

    public void updateChild(Long userId, Long childId, ChildUpdateDTO childUpdateDTO) throws Exception {
        if (userHasChildWithGivenId(userId, childId))
            childService.updateChild(childId, childUpdateDTO);
    }

    public void changeChildActive(Long userId, Long childId) throws ObjectNotFoundException {
        if (userHasChildWithGivenId(userId, childId))
            childService.changeChildActive(childId);
    }

    public void changeAllChildActive(Long userId, List<Long> childrenId) throws ObjectNotFoundException {
        for(Long id : childrenId) {
            if (userRepository.checkIfUserHasChildWithGivenId(userId, id))
                childService.changeChildActive(id);
        }
    }

    public void updatePhone(Long userId, PhoneUpdateDTO phoneUpdateDTO) throws Exception {
        Long phoneId = findById(userId).getPhone().getId();
        phoneService.updatePhone(phoneId, phoneUpdateDTO);
    }

    public void updateAddress(Long userId, AddressUpdateDTO addressUpdateDTO) throws Exception {
        Long addressId = findById(userId).getAddress().getId();
        addressService.updateAddress(addressId, addressUpdateDTO);
    }

    public boolean userHasChildWithGivenId(Long userId, Long childId) throws ObjectNotFoundException {
        if (userRepository.checkIfUserHasChildWithGivenId(userId, childId))
            return true;
        else
            throw new ObjectNotFoundException("User with id " + userId + " doesn't have a Child with id"
                    + childId + " registered");
    }
}
