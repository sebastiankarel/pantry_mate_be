package de.slapps.pantry_mate.pantry.model

import de.slapps.pantry_mate.pantry.model.dto.PantryDTO

fun Pantry.toDTO() = PantryDTO(
    id = this.id ?: throw IllegalStateException("Pantry id must not be null."),
    name = this.name,
    createdOn = this.createdOn,
)