package br.com.iriscareapi.services;

import br.com.iriscareapi.dto.analysis.AnalysisFindDTO;
import br.com.iriscareapi.entities.Analysis;
import br.com.iriscareapi.exception.EntityRegisterException;
import br.com.iriscareapi.exception.ObjectNotFoundException;
import br.com.iriscareapi.repositories.AnalysisRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AnalysisService {

    @Autowired
    private AnalysisRepository analysisRepository;

    public Analysis findById(Long id) throws ObjectNotFoundException {
        return analysisRepository.findById(id).
                orElseThrow(() -> new ObjectNotFoundException("Analysis with id " + id + " not found"));
    }

    public List<AnalysisFindDTO> findAllByChildId(Long id) throws ObjectNotFoundException {
        childHasAnyAnalysis(id);
        return analysisRepository.findAllByChildId(id).stream().map(AnalysisFindDTO::new).collect(Collectors.toList());
    }

    public void saveAnalysis(Analysis analysis) {
        try {
            analysisRepository.saveAndFlush(analysis);
        } catch (Exception e) {
            throw new EntityRegisterException("Analysis", e.getMessage());
        }
    }

    public void childHasAnyAnalysis(Long id) throws ObjectNotFoundException {
        if(!analysisRepository.existsAnalysisByChildId(id))
            throw new ObjectNotFoundException("Child with id " + id + " doesn't have any analysis registered");
    }

}
