package br.com.iriscareapi.services;

import br.com.iriscareapi.dto.ExamFindDTO;
import br.com.iriscareapi.dto.ExamInsertDTO;
import br.com.iriscareapi.entities.Child;
import br.com.iriscareapi.entities.Exam;
import br.com.iriscareapi.entities.Phone;
import br.com.iriscareapi.exception.EntityRegisterException;
import br.com.iriscareapi.exception.ObjectNotFoundException;
import br.com.iriscareapi.repositories.ExamRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ExamService {

    @Autowired
    private ExamRepository examRepository;

    public Exam findById(Long id) throws ObjectNotFoundException {
        return examRepository.findById(id)
                                .orElseThrow(() -> new ObjectNotFoundException("Exam with id " + id + " not found"));
    }

    public List<ExamFindDTO> findAllByChildId(Long childId) throws ObjectNotFoundException {
        childHasAnyExam(childId);
        return examRepository.findAllByChildId(childId).stream().map(ExamFindDTO::new).collect(Collectors.toList());
    }

    public void childHasAnyExam(Long id) throws ObjectNotFoundException {
        if(!examRepository.existsExamByChildId(id))
            throw new ObjectNotFoundException("Child with id " + id + " doesn't have any exam registered");
    }

    public void saveExam(Exam exam) {
        try {
            examRepository.saveAndFlush(exam);
        } catch (Exception e) {
            throw new EntityRegisterException("Exam", e.getMessage());
        }
    }

}
