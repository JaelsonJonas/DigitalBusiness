package br.com.iriscareapi.services;

import br.com.iriscareapi.dto.ChildUpdateDTO;
import br.com.iriscareapi.entities.Child;
import br.com.iriscareapi.exception.ObjectNotFoundException;
import br.com.iriscareapi.repositories.ChildRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class ChildService {

    @Autowired
    private ChildRepository repository;

    public Child findById(Long id) throws ObjectNotFoundException {
        return repository.findById(id).orElseThrow(() -> new ObjectNotFoundException("Child with id " + id + " not found"));
    }

    public List<Child> findAllByUserId(Long id) throws ObjectNotFoundException {
        userHasAnyChild(id);
        return repository.findAllByUserId(id);
    }

    public List<Child> findAllActiveChildrenByUserId(Long id) throws ObjectNotFoundException {
        userHasAnyChild(id);
        return repository.findAllByUserIdAndActiveIsTrue(id);
    }

    public List<Child> findAllInactiveChildrenByUserId(Long id) throws ObjectNotFoundException {
        userHasAnyChild(id);
        return repository.findAllByUserIdAndActiveIsFalse(id);
    }

    public void insertChild(Child child) {
        repository.save(child);
    }

    public void updateChild(ChildUpdateDTO childUpdateDTO, Long childId) throws ObjectNotFoundException {
        var child = findById(childId);
        dataUpdate(child, childUpdateDTO);
        repository.save(child);
    }

    public void changeChildActive(Long id) throws ObjectNotFoundException {
        var child = findById(id);
        child.setActive(!child.getActive());
        repository.save(child);
    }

    public void changeAllChildActive(List<Long> childrenId) throws ObjectNotFoundException {
        for(Long id : childrenId) {
            changeChildActive(id);
        }
    }

    public void userHasAnyChild(Long id) throws ObjectNotFoundException {
        if(!repository.existsChildByUserId(id))
            throw new ObjectNotFoundException("User with id " + id + " doesn't have any children registered");
    }

    public void dataUpdate(Child childToAtt, ChildUpdateDTO childUpdateDTO) {
        String name = (childUpdateDTO.getName() != null &&
                childUpdateDTO.getName().isEmpty() &&
                childUpdateDTO.getName().isBlank()) ? childUpdateDTO.getName()
                : childToAtt.getName();
        childToAtt.setName(name);

        String cpf = (childUpdateDTO.getCpf() != null &&
                childUpdateDTO.getCpf().isEmpty() &&
                childUpdateDTO.getCpf().isBlank()) ? childUpdateDTO.getCpf() : childToAtt.getCpf();
        childToAtt.setCpf(cpf);

        LocalDate birthday = (childUpdateDTO.getBirthday() != null && childUpdateDTO.getBirthday().isEmpty() &&
                childUpdateDTO.getBirthday().isBlank()) ? LocalDate.parse(childUpdateDTO.getBirthday()) : childToAtt.getBirthday();
        childToAtt.setBirthday(birthday);
    }

}
