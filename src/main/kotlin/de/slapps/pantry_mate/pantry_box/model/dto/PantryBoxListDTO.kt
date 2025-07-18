package de.slapps.pantry_mate.pantry_box.model.dto

data class PantryBoxListDTO(
    val pantryId: Int,
    val pantryBoxes: List<PantryBoxDTO>,
)
