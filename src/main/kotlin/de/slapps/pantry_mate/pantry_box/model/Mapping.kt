package de.slapps.pantry_mate.pantry_box.model

import de.slapps.pantry_mate.pantry_box.model.dto.PantryBoxDTO


fun PantryBox.toDTO() = PantryBoxDTO(
    id = this.id ?: throw IllegalStateException("PantryBox id must not be null."),
    itemName = this.name,
    quantity = this.quantity,
    quantityUnit = this.quantityUnit,
    createdOn = this.createdOn,
)
