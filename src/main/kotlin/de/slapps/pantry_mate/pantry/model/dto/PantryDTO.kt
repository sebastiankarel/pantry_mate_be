package de.slapps.pantry_mate.pantry.model.dto

data class PantryDTO(
    val id: Int,
    val name: String,
    val boxes: List<PantryBoxDTO>,
)
