package br.com.iriscareapi.controllers;

import br.com.iriscareapi.dto.ExamFindDTO;
import br.com.iriscareapi.exception.ObjectNotFoundException;
import br.com.iriscareapi.services.ChildService;
import br.com.iriscareapi.services.ExamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/children")
public class ChildController {

    @Autowired
    private ChildService childService;

    @GetMapping(value = "/{id}/exam/{examId}")
    public ResponseEntity<ExamFindDTO> findExamById(@PathVariable Long id, @PathVariable Long examId) throws ObjectNotFoundException {
        return ResponseEntity.status(HttpStatus.FOUND).body(childService.findExamById(id, examId));
    }

}
