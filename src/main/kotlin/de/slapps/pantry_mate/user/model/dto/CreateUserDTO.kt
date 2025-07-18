package de.slapps.pantry_mate.user.model.dto

data class CreateUserDTO(
    val username: String,
    val email: String,
    val password: String,
)