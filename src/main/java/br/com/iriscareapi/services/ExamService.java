package br.com.iriscareapi.services;

import br.com.iriscareapi.dto.exam.ExamFindDTO;
import br.com.iriscareapi.dto.exam.ExamUpdateDTO;
import br.com.iriscareapi.entities.Exam;
import br.com.iriscareapi.exception.EntityRegisterException;
import br.com.iriscareapi.exception.ObjectNotFoundException;
import br.com.iriscareapi.repositories.ExamRepository;
import br.com.iriscareapi.utils.DataUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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

    public Page<ExamFindDTO> findAllByChildId(Long childId, Pageable pageable) throws ObjectNotFoundException {
        childHasAnyExam(childId);
        return examRepository.findAllByChildId(childId, pageable).map(ExamFindDTO::new);
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

    public void updateExam(Long examId, ExamUpdateDTO examUpdateDTO) throws Exception {
        Exam exam = findById(examId);
        DataUtils.dataUpdate(exam, examUpdateDTO);
        saveExam(exam);
    }

}
