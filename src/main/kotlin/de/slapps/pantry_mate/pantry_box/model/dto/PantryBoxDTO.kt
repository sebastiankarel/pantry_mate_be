package de.slapps.pantry_mate.pantry_box.model.dto

import de.slapps.pantry_mate.pantry_box.model.QuantityUnit
import java.time.LocalDateTime

data class PantryBoxDTO(
    val id: Int,
    val itemName: String,
    val quantity: Int,
    val quantityUnit: QuantityUnit,
    val createdOn: LocalDateTime,
)
