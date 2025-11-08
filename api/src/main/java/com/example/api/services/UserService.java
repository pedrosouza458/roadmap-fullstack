package com.example.api.services;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.api.dtos.GetUsersDTO;
import com.example.api.dtos.UpdateUserDTO;
import com.example.api.models.User;
import com.example.api.repositories.UserRepository;

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

  public GetUsersDTO updateUser(UpdateUserDTO dto) {

    // get id by logged user
    UUID id = UUID.randomUUID();
    User userToUpdate = userRepository.findById(id)
        .orElseThrow(() -> new RuntimeException("User not found with id: " + id));

    if (dto.name() != null) {
      userToUpdate.setName(dto.name());
    }

    if (dto.email() != null) {
      userToUpdate.setEmail(dto.email());
    }

    if (dto.password() != null) {
      String hashedPassword = bCryptPasswordEncoder.encode(dto.password());
      userToUpdate.setPassword(hashedPassword);
    }

    User updatedUser = userRepository.save(userToUpdate);

    return new GetUsersDTO(updatedUser.getId(), updatedUser.getName(), updatedUser.getEmail());
  }
}
