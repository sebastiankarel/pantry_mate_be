package de.slapps.pantry_mate.user.model

import de.slapps.pantry_mate.user.model.dto.UserCreatedDTO

fun User.toUserCreatedDTO() = UserCreatedDTO(
    userId = this.id ?: throw IllegalStateException("User id must not be null."),
    userName = this.username,
    email = this.email,
)