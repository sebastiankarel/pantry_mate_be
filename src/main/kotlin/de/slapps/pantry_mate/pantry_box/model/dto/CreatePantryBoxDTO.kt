package de.slapps.pantry_mate.pantry_box.model.dto

import de.slapps.pantry_mate.pantry_box.model.QuantityUnit

data class CreatePantryBoxDTO(
    val name: String,
    val quantity: Int,
    val quantityUnit: QuantityUnit,
)
