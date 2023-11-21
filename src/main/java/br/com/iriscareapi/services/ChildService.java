package br.com.iriscareapi.services;

import br.com.iriscareapi.dto.analysis.AnalysisFindDTO;
import br.com.iriscareapi.dto.analysis.AnalysisInsertDTO;
import br.com.iriscareapi.dto.child.ChildUpdateDTO;
import br.com.iriscareapi.dto.exam.ExamFindDTO;
import br.com.iriscareapi.dto.exam.ExamInsertDTO;
import br.com.iriscareapi.dto.exam.ExamUpdateDTO;
import br.com.iriscareapi.entities.Analysis;
import br.com.iriscareapi.entities.Child;
import br.com.iriscareapi.entities.Exam;
import br.com.iriscareapi.exception.EntityRegisterException;
import br.com.iriscareapi.exception.ObjectNotFoundException;
import br.com.iriscareapi.repositories.ChildRepository;
import br.com.iriscareapi.utils.DataUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ChildService {

    @Autowired
    private ChildRepository childRepository;

    @Autowired
    private ExamService examService;

    @Autowired
    private AnalysisService analysisService;

    public Child findById(Long id) throws ObjectNotFoundException {
        return childRepository.findById(id).
                                orElseThrow(() -> new ObjectNotFoundException("Child with id " + id + " not found"));
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

    public void updateExam(Long child, Long examId, ExamUpdateDTO examUpdateDTO) throws Exception {
        if(childHasExamWithGivenId(child, examId))
            examService.updateExam(examId, examUpdateDTO);
    }

    public List<Long> findChildIdsByUserId(Long id) throws ObjectNotFoundException {
        userHasAnyChild(id);
        return childRepository.findChildIdsByUserId(id);
    }

    public boolean childHasExamWithGivenId(Long childId, Long examId) throws ObjectNotFoundException {
        if (childRepository.checkIfChildHasExamWithGivenId(childId, examId))
            return true;
        else
            throw new ObjectNotFoundException("User with id " + childId + " doesn't have a Child with id"
                    + examId + " registered");
    }

    public AnalysisFindDTO findAnalysisById(Long userId, Long analysisId) throws ObjectNotFoundException {
        if(childHasAnalysisWithGivenId(userId, analysisId))
            return new AnalysisFindDTO(analysisService.findById(analysisId));
        return null;
    }

    public List<AnalysisFindDTO> finAllAnalysesByChildId(Long id) throws ObjectNotFoundException {
        return analysisService.findAllByChildId(id);
    }

    public void registerNewAnalysis(Long id, AnalysisInsertDTO analysisInsertDTO) throws ObjectNotFoundException {
        Child child = findById(id);
        Analysis analysis = new Analysis(analysisInsertDTO);
        analysis.setChild(child);
        analysisService.saveAnalysis(analysis);
        child.addAnalysis(analysis);
        saveChild(child);
    }

    public boolean childHasAnalysisWithGivenId(Long childId, Long analysisId) throws ObjectNotFoundException {
        if (childRepository.checkIfChildHasAnalysisWithGivenId(childId, analysisId))
            return true;
        else
            throw new ObjectNotFoundException("User with id " + childId + " doesn't have a Child with id"
                    + analysisId + " registered");
    }

    /* public void dataUpdate(Child childToAtt, ChildUpdateDTO childUpdateDTO) throws Exception {
        childToAtt.setName(DataUtils.validateUpdatedValue(childToAtt.getName(),
                                                    childUpdateDTO.getName()));

        childToAtt.setCpf(DataUtils.validateUpdatedValue(childToAtt.getCpf(),
                                                    childUpdateDTO.getCpf()));

        childToAtt.setBirthday(DataUtils.validateUpdatedValue(childToAtt.getBirthday(),
                                    DateUtils.parseString(childUpdateDTO.getBirthday())));
    } */

}
