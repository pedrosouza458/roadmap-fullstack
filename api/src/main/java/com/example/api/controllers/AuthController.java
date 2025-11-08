package com.example.api.controllers;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.api.dtos.LoginUserDTO;
import com.example.api.dtos.RegisterUserDTO;
import com.example.api.models.User;
import com.example.api.services.AuthService;
import com.example.api.services.JwtService;

@RequestMapping("/auth")
@RestController
public class AuthController {
  private final JwtService jwtService;
  private final AuthService authService;

  public AuthController(JwtService jwtService, AuthService authService) {
    this.jwtService = jwtService;
    this.authService = authService;
  }

  @PostMapping("/sign-up")
  public ResponseEntity<Map<String, String>> createUser(@RequestBody RegisterUserDTO payload) {
    authService.signup(payload);
    Map<String, String> response = new HashMap<>();
    response.put("Message", "User created Succesfully");
    return ResponseEntity.status(HttpStatus.CREATED).body(response);
  }

  @PostMapping("/sign-in")
  public ResponseEntity<Map<String, Object>> login(@RequestBody LoginUserDTO dto) {
    User authenticatedUser = authService.authenticate(dto);

    String jwtToken = jwtService.generateToken(authenticatedUser);

    Map<String, Object> response = new HashMap<>();
    response.put("token", jwtToken);
    response.put("expires", jwtService.getExpirationTime());

    return ResponseEntity.ok(response);
  }
}
