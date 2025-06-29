package de.slapps.pantry_mate.inventory.model

import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Table

@Table("inventory")
data class Inventory(
    @Id val id: Int? = null,
    val items: List<Item>,
)