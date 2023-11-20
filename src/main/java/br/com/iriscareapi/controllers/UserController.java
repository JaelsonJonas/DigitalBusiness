package br.com.iriscareapi.controllers;

import br.com.iriscareapi.dto.*;
import br.com.iriscareapi.exception.ObjectNotFoundException;
import br.com.iriscareapi.services.UserService;
import jakarta.validation.Valid;
import jakarta.websocket.server.PathParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping(value = "/{id}")
    public ResponseEntity<UserFindDTO> findUserById(@PathVariable Long id) throws ObjectNotFoundException {
        return ResponseEntity.status(HttpStatus.FOUND).body(new UserFindDTO(userService.findById(id)));
    }

    @PostMapping
    public ResponseEntity<Void> registerNewUser(@RequestBody @Valid UserInsertDTO userInsertDTO) throws Exception {
        userService.registerUser(userInsertDTO);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<Void> updateUser(@PathVariable Long id, @RequestBody @Valid UserUpdateDTO updateDTO) throws Exception {
        userService.updateUser(updateDTO, id);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @PostMapping(value = "/{id}/children")
    public ResponseEntity<Void> registerNewChild(@RequestBody @Valid ChildInsertDTO childInsertDTO, @PathVariable Long id) throws Exception {
        userService.registerNewChild(childInsertDTO, id);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping(value = "/{id}/children/{childId}")
    public ResponseEntity<ChildFindDTO> findUserChildById(@PathVariable Long id, @PathVariable Long childId) throws ObjectNotFoundException {
        return ResponseEntity.status(HttpStatus.FOUND).body(userService.findChildById(childId, id));
    }

    @GetMapping(value = "/{id}/children")
    public ResponseEntity<List<ChildFindDTO>> findAllUserChildren(@PathVariable Long id) throws ObjectNotFoundException {
        return ResponseEntity.status(HttpStatus.FOUND).body(userService.findAllChildrenByUserId(id));
    }

    @GetMapping(value = "/{id}/children/active")
    public ResponseEntity<List<ChildFindDTO>> findAllActiveUserChildren(@PathVariable Long id) throws ObjectNotFoundException {
        return ResponseEntity.status(HttpStatus.FOUND).body(userService.findAllActiveChildrenByUserId(id));
    }

    @GetMapping(value = "/{id}/children/inactive")
    public ResponseEntity<List<ChildFindDTO>> findAllInactiveUserChildren(@PathVariable Long id) throws ObjectNotFoundException {
        return ResponseEntity.status(HttpStatus.FOUND).body(userService.findAllInactiveChildrenByUserId(id));
    }

    @PutMapping(value = "/{id}/children/{childId}")
    public ResponseEntity<Void> updateChild(@PathVariable Long id,
                                            @PathVariable Long childId,
                                            @RequestBody @Valid ChildUpdateDTO childUpdateDTO) throws Exception {
        userService.updateChild(id, childId, childUpdateDTO);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @PutMapping(value = "/{id}/children/{childId}/active")
    public ResponseEntity<Void> updateChild(@PathVariable Long id,
                                            @PathVariable Long childId) throws ObjectNotFoundException {
        userService.changeChildActive(id, childId);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @PutMapping(value = "/{id}/phone")
    public ResponseEntity<Void> updatePhone(@PathVariable Long id,
                                            @RequestBody @Valid PhoneUpdateDTO phoneUpdateDTO) throws Exception {
        userService.updatePhone(id, phoneUpdateDTO);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @PutMapping(value = "/{id}/address")
    public ResponseEntity<Void> updateAddress(@PathVariable Long id,
                                            @RequestBody @Valid AddressUpdateDTO addressUpdateDTO) throws Exception {
        userService.updateAddress(id, addressUpdateDTO);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

}
