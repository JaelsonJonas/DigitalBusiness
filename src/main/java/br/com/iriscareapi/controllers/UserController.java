package br.com.iriscareapi.controllers;

import br.com.iriscareapi.dto.address.AddressUpdateDTO;
import br.com.iriscareapi.dto.auth.AuthResponse;
import br.com.iriscareapi.dto.auth.LoginRequest;
import br.com.iriscareapi.dto.child.ChildFindDTO;
import br.com.iriscareapi.dto.child.ChildInsertDTO;
import br.com.iriscareapi.dto.child.ChildUpdateDTO;
import br.com.iriscareapi.dto.phone.PhoneUpdateDTO;
import br.com.iriscareapi.dto.user.UserFindDTO;
import br.com.iriscareapi.dto.user.UserInsertDTO;
import br.com.iriscareapi.dto.user.UserUpdateDTO;
import br.com.iriscareapi.entities.User;
import br.com.iriscareapi.exception.ObjectNotFoundException;
import br.com.iriscareapi.services.UserService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {

        AuthResponse authResponse = userService.authenticateUser(loginRequest);

        return ResponseEntity.ok(authResponse);

    }

    @PostMapping("/signup")
    public ResponseEntity<Void> registerUser(@Valid @RequestBody UserInsertDTO userInsertDTO) throws Exception {

        User user = userService.registerUser(userInsertDTO);

        URI location = ServletUriComponentsBuilder
                .fromCurrentContextPath().path("/user/me")
                .buildAndExpand(user.getId()).toUri();


        return ResponseEntity.created(location).build();
    }


    @GetMapping(value = "/{id}")
    @SecurityRequirement(name = "bearer-key")
    public ResponseEntity<UserFindDTO> findUserById(@PathVariable Long id) throws ObjectNotFoundException {
        return ResponseEntity.status(HttpStatus.FOUND).body(new UserFindDTO(userService.findById(id)));
    }

    @PutMapping(value = "/{id}")
    @SecurityRequirement(name = "bearer-key")
    public ResponseEntity<Void> updateUser(@PathVariable Long id, @RequestBody @Valid UserUpdateDTO updateDTO) throws Exception {
        userService.updateUser(updateDTO, id);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @DeleteMapping(value = "/{id}")
    @SecurityRequirement(name = "bearer-key")
    public ResponseEntity<Void> deactivateUser(@PathVariable Long id) throws Exception {
        userService.changeUserActive(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @PostMapping(value = "/{id}/children")
    @SecurityRequirement(name = "bearer-key")
    public ResponseEntity<Void> registerNewChild(@RequestBody @Valid ChildInsertDTO childInsertDTO, @PathVariable Long id) throws Exception {
        userService.registerNewChild(childInsertDTO, id);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping(value = "/{id}/children/{childId}")
    @SecurityRequirement(name = "bearer-key")
    public ResponseEntity<ChildFindDTO> findUserChildById(@PathVariable Long id, @PathVariable Long childId) throws ObjectNotFoundException {
        return ResponseEntity.status(HttpStatus.FOUND).body(userService.findChildById(childId, id));
    }

    @GetMapping(value = "/{id}/children")
    @SecurityRequirement(name = "bearer-key")
    public ResponseEntity<List<ChildFindDTO>> findAllUserChildren(@PathVariable Long id) throws ObjectNotFoundException {
        return ResponseEntity.status(HttpStatus.FOUND).body(userService.findAllChildrenByUserId(id));
    }

    @GetMapping(value = "/{id}/children/active")
    @SecurityRequirement(name = "bearer-key")
    public ResponseEntity<List<ChildFindDTO>> findAllActiveUserChildren(@PathVariable Long id) throws ObjectNotFoundException {
        return ResponseEntity.status(HttpStatus.FOUND).body(userService.findAllActiveChildrenByUserId(id));
    }

    @GetMapping(value = "/{id}/children/inactive")
    @SecurityRequirement(name = "bearer-key")
    public ResponseEntity<List<ChildFindDTO>> findAllInactiveUserChildren(@PathVariable Long id) throws ObjectNotFoundException {
        return ResponseEntity.status(HttpStatus.FOUND).body(userService.findAllInactiveChildrenByUserId(id));
    }

    @PutMapping(value = "/{id}/children/{childId}")
    @SecurityRequirement(name = "bearer-key")
    public ResponseEntity<Void> updateChild(@PathVariable Long id,
                                            @PathVariable Long childId,
                                            @RequestBody @Valid ChildUpdateDTO childUpdateDTO) throws Exception {
        userService.updateChild(id, childId, childUpdateDTO);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @PutMapping(value = "/{id}/children/{childId}/active")
    @SecurityRequirement(name = "bearer-key")
    public ResponseEntity<Void> updateChild(@PathVariable Long id,
                                            @PathVariable Long childId) throws ObjectNotFoundException {
        userService.changeChildActive(id, childId);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @PutMapping(value = "/{id}/phone")
    @SecurityRequirement(name = "bearer-key")
    public ResponseEntity<Void> updatePhone(@PathVariable Long id,
                                            @RequestBody @Valid PhoneUpdateDTO phoneUpdateDTO) throws Exception {
        userService.updatePhone(id, phoneUpdateDTO);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @PutMapping(value = "/{id}/address")
    @SecurityRequirement(name = "bearer-key")
    public ResponseEntity<Void> updateAddress(@PathVariable Long id,
                                              @RequestBody @Valid AddressUpdateDTO addressUpdateDTO) throws Exception {
        userService.updateAddress(id, addressUpdateDTO);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

}
