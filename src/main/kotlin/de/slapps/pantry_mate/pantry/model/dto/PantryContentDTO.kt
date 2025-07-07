package de.slapps.pantry_mate.pantry.model.dto

data class PantryContentDTO(
    val userId: Int,
    val pantryBoxes: List<PantryBoxDTO>,
)
