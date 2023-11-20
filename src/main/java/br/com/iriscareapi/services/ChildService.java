package br.com.iriscareapi.services;

import br.com.iriscareapi.dto.ChildFindDTO;
import br.com.iriscareapi.dto.ChildUpdateDTO;
import br.com.iriscareapi.dto.ExamFindDTO;
import br.com.iriscareapi.dto.ExamInsertDTO;
import br.com.iriscareapi.entities.Child;
import br.com.iriscareapi.entities.Exam;
import br.com.iriscareapi.exception.EntityRegisterException;
import br.com.iriscareapi.exception.ObjectNotFoundException;
import br.com.iriscareapi.repositories.ChildRepository;
import br.com.iriscareapi.utils.DataUtils;
import br.com.iriscareapi.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class ChildService {

    @Autowired
    private ChildRepository childRepository;

    @Autowired
    private ExamService examService;

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

    public void updateChild(Long childId, ChildUpdateDTO childUpdateDTO) throws Exception {
        Child child = findById(childId);
        DataUtils.dataUpdate(child, childUpdateDTO);
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

    public ExamFindDTO findExamById(Long childId, Long examId) throws ObjectNotFoundException {
        if(childHasExamWithGivenId(childId, examId))
            return new ExamFindDTO(examService.findById(examId));

        return null;
    }

    public List<ExamFindDTO> findAllExamsByChildId(Long childId) throws ObjectNotFoundException {
        return examService.findAllByChildId(childId);
    }

    public void registerNewExam(Long childId, ExamInsertDTO examInsertDTO) throws Exception {
        Child child = findById(childId);
        Exam exam = new Exam(examInsertDTO);
        exam.setChild(child);
        examService.saveExam(exam);
        child.addExam(exam);
        saveChild(child);
    }

    public void dataUpdate(Child childToAtt, ChildUpdateDTO childUpdateDTO) throws Exception {
        childToAtt.setName(DataUtils.validateUpdatedValue(childToAtt.getName(),
                                                    childUpdateDTO.getName()));

        childToAtt.setCpf(DataUtils.validateUpdatedValue(childToAtt.getCpf(),
                                                    childUpdateDTO.getCpf()));

        childToAtt.setBirthday(DataUtils.validateUpdatedValue(childToAtt.getBirthday(),
                                    DateUtils.parseString(childUpdateDTO.getBirthday())));
    }

    public boolean childHasExamWithGivenId(Long childId, Long examId) throws ObjectNotFoundException {
        if (childRepository.checkIfChildHasExamWithGivenId(childId, examId))
            return true;
        else
            throw new ObjectNotFoundException("User with id " + childId + " doesn't have a Child with id"
                    + examId + " registered");
    }

}
