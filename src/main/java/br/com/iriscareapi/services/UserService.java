package br.com.iriscareapi.services;

import br.com.iriscareapi.dto.ChildInsertDTO;
import br.com.iriscareapi.entities.Child;
import br.com.iriscareapi.entities.User;
import br.com.iriscareapi.exception.ObjectNotFoundException;
import br.com.iriscareapi.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository repository;

    @Autowired
    private ChildService childService;

    public User findById(Long id) throws ObjectNotFoundException {
        return repository
                .findById(id).orElseThrow(() -> new ObjectNotFoundException("User with id " + id + " not found."));
    }

    public void insertChild(ChildInsertDTO childInsertDTO, Long userId) throws ObjectNotFoundException {
        User user = findById(userId);
        Child child = new Child(childInsertDTO);
        child.setUser(user);
        childService.insertChild(child);
        user.addChild(child);
        repository.save(user);
    }

}
