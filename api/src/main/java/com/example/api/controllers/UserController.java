package com.example.api.controllers;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.api.dtos.GetUsersDTO;
import com.example.api.models.User;
import com.example.api.repositories.UserRepository;
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
  public ResponseEntity<User> getUserById(@PathVariable String id) {
    return userService.getUserById(id)
        .map(ResponseEntity::ok)
        .orElseGet(() -> ResponseEntity.status(org.springframework.http.HttpStatus.NOT_FOUND).build());
  }
}
