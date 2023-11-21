package br.com.iriscareapi.controllers;

import br.com.iriscareapi.dto.exam.ExamFindDTO;
import br.com.iriscareapi.dto.exam.ExamInsertDTO;
import br.com.iriscareapi.dto.exam.ExamUpdateDTO;
import br.com.iriscareapi.exception.ObjectNotFoundException;
import br.com.iriscareapi.services.ChildService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api/children")
public class ChildController {

    @Autowired
    private ChildService childService;

    @GetMapping(value = "/{id}/exam/{examId}")
    public ResponseEntity<ExamFindDTO> findExamById(@PathVariable Long id, @PathVariable Long examId) throws ObjectNotFoundException {
        return ResponseEntity.status(HttpStatus.FOUND).body(childService.findExamById(id, examId));
    }

    @GetMapping(value = "/{id}/exam")
    public ResponseEntity<List<ExamFindDTO>> findAllExamByChildId(@PathVariable Long id) throws ObjectNotFoundException {
        return ResponseEntity.status(HttpStatus.FOUND).body(childService.findAllExamsByChildId(id));
    }

    @PostMapping(value = "/{id}/exam")
    public ResponseEntity<Void> registerNewExam(@PathVariable Long id, @RequestBody @Valid ExamInsertDTO examInsertDTO) throws Exception {
        childService.registerNewExam(id, examInsertDTO);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PutMapping(value = "/{id}/exam/{examId}")
    public ResponseEntity<Void> updateExam(@PathVariable Long id,
                                           @PathVariable Long examId,
                                           @RequestBody @Valid ExamUpdateDTO examUpdateDTO) throws Exception {
        childService.updateExam(id, examId, examUpdateDTO);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

}
