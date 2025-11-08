package com.example.api.controllers;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.api.dtos.GetUsersDTO;
import com.example.api.dtos.UpdateUserDTO;
import com.example.api.services.UserService;

@RestController
@RequestMapping("/users")
public class UserController {

  private final UserService userService;

  public UserController(UserService userService) {
    this.userService = userService;
  }

  @GetMapping()
  public ResponseEntity<Page<GetUsersDTO>> getUsers(Pageable pageable) {
    Page<GetUsersDTO> page = userService.getUsers(pageable);
    return ResponseEntity.ok(page);
  }

  @GetMapping("/{id}")
  public ResponseEntity<GetUsersDTO> getUserById(@PathVariable UUID id) {
    GetUsersDTO user = userService.getUserById(id);
    return ResponseEntity.ok(user);
  }

  @PutMapping("/{id}")
  public ResponseEntity<Map<String, String>> updateUser(@RequestParam UUID id, @RequestBody UpdateUserDTO payload) {
    userService.updateUser(payload);
    Map<String, String> response = new HashMap<>();
    response.put("Message", "User updated Succesfully");
    return ResponseEntity.status(HttpStatus.CREATED).body(response);
  }
}
