package de.slapps.pantry_mate.pantry.model.dto

import java.time.LocalDateTime

data class PantryDTO(
    val id: Int,
    val name: String,
    val createdOn: LocalDateTime,
)
