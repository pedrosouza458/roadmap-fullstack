package com.example.api.services;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.api.repositories.UserRepository;

import jakarta.transaction.Transactional;

import com.example.api.dtos.GetUsersDTO;
import com.example.api.dtos.LoginUserDTO;
import com.example.api.dtos.RegisterUserDTO;
import com.example.api.models.User;

@Service
public class AuthService {
  private final UserRepository userRepository;

  private final PasswordEncoder passwordEncoder;

  private final AuthenticationManager authenticationManager;

  public AuthService(
      UserRepository userRepository,
      AuthenticationManager authenticationManager,
      PasswordEncoder passwordEncoder) {
    this.authenticationManager = authenticationManager;
    this.userRepository = userRepository;
    this.passwordEncoder = passwordEncoder;
  }

  @Transactional
  public GetUsersDTO signup(RegisterUserDTO input) {
    User user = new User();
    user.setName(input.name().toString());
    user.setEmail(input.email());
    user.setPassword(passwordEncoder.encode(input.password()));
    User saved = userRepository.saveAndFlush(user);
    return new GetUsersDTO(saved.getId(), saved.getName(), saved.getEmail());
  }

  public User authenticate(LoginUserDTO input) {
    authenticationManager.authenticate(
        new UsernamePasswordAuthenticationToken(
            input.email(),
            input.password()));

    return userRepository.findByEmail(input.email())
        .orElseThrow();
  }
}
