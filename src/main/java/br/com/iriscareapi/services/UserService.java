package br.com.iriscareapi.services;

import br.com.iriscareapi.dto.address.AddressUpdateDTO;
import br.com.iriscareapi.dto.auth.AuthResponse;
import br.com.iriscareapi.dto.auth.LoginRequest;
import br.com.iriscareapi.dto.child.ChildFindDTO;
import br.com.iriscareapi.dto.child.ChildInsertDTO;
import br.com.iriscareapi.dto.child.ChildUpdateDTO;
import br.com.iriscareapi.dto.phone.PhoneUpdateDTO;
import br.com.iriscareapi.dto.user.UserInsertDTO;
import br.com.iriscareapi.dto.user.UserUpdateDTO;
import br.com.iriscareapi.entities.*;
import br.com.iriscareapi.exception.BadRequestException;
import br.com.iriscareapi.exception.EntityRegisterException;
import br.com.iriscareapi.exception.ObjectNotFoundException;
import br.com.iriscareapi.repositories.UserRepository;
import br.com.iriscareapi.security.TokenProvider;
import br.com.iriscareapi.utils.DataUtils;
import br.com.iriscareapi.utils.DateUtils;
import br.com.iriscareapi.validation.LoginValidation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {

    @Autowired
    List<LoginValidation> validations;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ChildService childService;
    @Autowired
    private AddressService addressService;
    @Autowired
    private PhoneService phoneService;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private TokenProvider tokenProvider;

    public AuthResponse authenticateUser(LoginRequest loginRequest) {

        validations.forEach(v -> v.validate(loginRequest));

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.email(), loginRequest.password())
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);

        String token = tokenProvider.createToken(authentication);

        Long id = tokenProvider.getUserIdFromToken(token);

        return new AuthResponse(token, id);
    }

    public User registerUser(UserInsertDTO userInsertDTO) throws Exception {

        if (userRepository.existsByEmail(userInsertDTO.getEmail())) {
            throw new BadRequestException("Email address already in use.");
        }

        User user = new User(userInsertDTO);
        Address address = new Address(userInsertDTO.getAddress());
        Phone phone = new Phone(userInsertDTO.getPhone());

        user.setProvider(AuthProvider.LOCAL);

        user.setPassword(passwordEncoder.encode(user.getPassword()));

        saveUser(user);

        address.setUser(user);

        addressService.saveAddress(address);

        phone.setUser(user);

        phoneService.savePhone(phone);

        user.setAddress(address);

        user.setPhone(phone);

        saveUser(user);

        return user;
    }


    public User findById(Long id) throws ObjectNotFoundException {
        return userRepository
                .findById(id).orElseThrow(() -> new ObjectNotFoundException("User with id " + id + " not found."));
    }

    public void updateUser(UserUpdateDTO userUpdateDTO, Long id) throws Exception {
        User user = findById(id);
        userUpdateDTO.setPassword(passwordEncoder.encode(userUpdateDTO.getPassword()));
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

    public void changeUserActive(Long id) throws ObjectNotFoundException {
        var user = findById(id);
        user.setActive(!user.getActive());
        saveUser(user);

        if (childService.findChildIdsByUserId(id) != null && childService.findChildIdsByUserId(id).isEmpty())
            changeAllChildActive(id, childService.findChildIdsByUserId(id));
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
        for (Long id : childrenId) {
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
