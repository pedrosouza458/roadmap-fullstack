package com.example.api.services;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.api.dtos.CreateUserDTO;
import com.example.api.dtos.GetUsersDTO;

import com.example.api.models.User;
import com.example.api.repositories.UserRepository;

import jakarta.transaction.Transactional;

@Service
public class UserService {

  private final UserRepository userRepository;
  private final BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();

  public UserService(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  public Page<GetUsersDTO> getUsers(Pageable pageable) {
    return userRepository.findAll(pageable).map(u -> new GetUsersDTO(
        u.getId(),
        u.getName(),
        u.getEmail()));
  }

  public GetUsersDTO getUserById(UUID id) {
    User user = userRepository.findById(id)
        .orElseThrow(() -> new RuntimeException("User not found with this id."));
    return new GetUsersDTO(user.getId(), user.getName(), user.getEmail());
  }

  @Transactional
  public GetUsersDTO createUser(CreateUserDTO dto) {
    User user = new User();
    user.setName(dto.name().toString());
    user.setEmail(dto.email());
    String hashedPassword = bCryptPasswordEncoder.encode(dto.password());
    user.setPassword(hashedPassword);
    User saved = userRepository.saveAndFlush(user);
    return new GetUsersDTO(saved.getId(), saved.getName(), saved.getEmail());
  }
}
