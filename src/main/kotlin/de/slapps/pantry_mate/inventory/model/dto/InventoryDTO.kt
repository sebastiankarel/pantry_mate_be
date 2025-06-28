package de.slapps.pantry_mate.inventory.model.dto

data class InventoryDTO(
    val id: Int,
    val items: List<ItemDTO>,
)