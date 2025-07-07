package de.slapps.pantry_mate.pantry.model.dto

import java.time.LocalDateTime

data class PantryBoxDTO(
    val name: String,
    val quantity: Int,
    val createdOn: LocalDateTime,
)
