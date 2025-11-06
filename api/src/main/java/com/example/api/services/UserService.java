package com.example.api.services;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.api.dtos.CreateUserDTO;
import com.example.api.dtos.GetUsersDTO;
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

  public Optional<User> getUserById(String id) {
    return userRepository.findById(id);
  }

  public GetUsersDTO createUser(CreateUserDTO dto) {
    User user = new User();
    user.setName(dto.name());
    user.setEmail(dto.email());
    String hashedPassword = bCryptPasswordEncoder.encode(dto.password());
    user.setPassword(hashedPassword);
    User saved = userRepository.save(user);
    return new GetUsersDTO(saved.getId(), saved.getName(), saved.getEmail());
  }
}
