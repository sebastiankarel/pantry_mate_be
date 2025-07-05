package de.slapps.pantry_mate.pantry.model.dto

data class PantryListDTO(
    val userId: Int,
    val pantries: List<PantryDTO>,
)
