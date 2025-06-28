package de.slapps.pantry_mate.inventory

import de.slapps.pantry_mate.inventory.model.dto.InventoryDTO
import org.springframework.stereotype.Component

@Component
class InventoryService {

    suspend fun getInventory(placeId: Int): InventoryDTO {
        return InventoryDTO(0, listOf())
    }
}