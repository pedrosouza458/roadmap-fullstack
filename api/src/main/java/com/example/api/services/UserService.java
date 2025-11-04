package com.example.api.services;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.example.api.dtos.GetUsersDTO;
import com.example.api.models.User;
import com.example.api.repositories.UserRepository;

@Service
public class UserService {

  private final UserRepository userRepository;

  public UserService(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  public Page<GetUsersDTO> getUsers(Pageable pageable) {
    return userRepository.findAll(pageable).map(u -> new GetUsersDTO(
        u.getId(),
        u.getName(),
        u.getName()));
  }

  public Optional<User> getUserById(String id) {
    return userRepository.findById(id);
  }
}
