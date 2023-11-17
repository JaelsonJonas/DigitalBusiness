package br.com.iriscareapi.services;

import br.com.iriscareapi.dto.ChildFindDTO;
import br.com.iriscareapi.dto.ChildUpdateDTO;
import br.com.iriscareapi.entities.Child;
import br.com.iriscareapi.exception.EntityRegisterException;
import br.com.iriscareapi.exception.ObjectNotFoundException;
import br.com.iriscareapi.repositories.ChildRepository;
import br.com.iriscareapi.utils.DataUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class ChildService {

    @Autowired
    private ChildRepository childRepository;

    public Child findById(Long id) throws ObjectNotFoundException {
        return childRepository.findById(id).orElseThrow(() -> new ObjectNotFoundException("Child with id " + id + " not found"));
    }

    public List<Child> findAllByUserId(Long id) throws ObjectNotFoundException {
        userHasAnyChild(id);
        return childRepository.findAllByUserId(id);
    }

    public List<Child> findAllActiveChildrenByUserId(Long id) throws ObjectNotFoundException {
        userHasAnyChild(id);
        return childRepository.findAllByUserIdAndActiveIsTrue(id);
    }

    public List<Child> findAllInactiveChildrenByUserId(Long id) throws ObjectNotFoundException {
        userHasAnyChild(id);
        return childRepository.findAllByUserIdAndActiveIsFalse(id);
    }

    public void saveChild(Child child) {
        try {
            childRepository.saveAndFlush(child);
        } catch (Exception e) {
            throw new EntityRegisterException("Child", e.getMessage());
        }
    }

    public void updateChild(Long childId, ChildUpdateDTO childUpdateDTO) throws ObjectNotFoundException {
        Child child = findById(childId);
        dataUpdate(child, childUpdateDTO);
        saveChild(child);
    }

    public void changeChildActive(Long id) throws ObjectNotFoundException {
        var child = findById(id);
        child.setActive(!child.getActive());
        saveChild(child);
    }

    public void userHasAnyChild(Long id) throws ObjectNotFoundException {
        if(!childRepository.existsChildByUserId(id))
            throw new ObjectNotFoundException("User with id " + id + " doesn't have any children registered");
    }

    public void dataUpdate(Child childToAtt, ChildUpdateDTO childUpdateDTO) {
        childToAtt.setName(DataUtils.validateUpdatedValue(childToAtt.getName(),
                                                    childUpdateDTO.getName()));

        childToAtt.setCpf(DataUtils.validateUpdatedValue(childToAtt.getCpf(),
                                                    childUpdateDTO.getCpf()));

        childToAtt.setBirthday(DataUtils.validateUpdatedValue(childToAtt.getBirthday(),
                                                    LocalDate.parse(childUpdateDTO.getBirthday())));
    }

    public ChildFindDTO parseChildToChildFindDTO(Child child) {
        return new ChildFindDTO(child.getName(), child.getCpf(), child.getBirthday().toString(), child.getActive());
    }
}
