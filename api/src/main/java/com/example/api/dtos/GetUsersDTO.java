package com.example.api.dtos;

import java.util.UUID;

public record GetUsersDTO(
        UUID id,
        String name,
        String email) {
}
