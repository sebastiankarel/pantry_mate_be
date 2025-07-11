package de.slapps.pantry_mate.pantry.model.dto

import java.time.LocalDateTime

data class CreatePantryBoxDTO(
    val itemName: String,
    val quantity: Int,
    val createdOn: LocalDateTime,
)