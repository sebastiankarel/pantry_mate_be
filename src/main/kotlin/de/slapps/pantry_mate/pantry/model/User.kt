package de.slapps.pantry_mate.pantry.model

import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Table

@Table("user", schema = "pantry_mate")
data class User(
    @Id val id: Int? = null,
    val username: String,
    val email: String,
    val password: String,
)
