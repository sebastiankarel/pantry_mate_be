package de.slapps.pantry_mate.inventory

import de.slapps.pantry_mate.inventory.model.dto.InventoryDTO
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

@Component
class InventoryService {

    @Autowired
    private lateinit var inventoryRepository: InventoryRepository

    suspend fun getInventory(placeId: Int): InventoryDTO? {
        return inventoryRepository.findById(placeId)
            ?.takeIf { it.id != null }
            ?.let { inventory ->
                InventoryDTO(
                    inventory.id!!,
                    listOf(),
                )
            }
    }
}