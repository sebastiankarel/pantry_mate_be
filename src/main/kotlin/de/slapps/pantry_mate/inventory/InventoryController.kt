package de.slapps.pantry_mate.inventory

import de.slapps.pantry_mate.inventory.model.dto.InventoryDTO
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/inventory")
class InventoryController(
    private val inventoryService: InventoryService,
) {

    @GetMapping("/{placeId}")
    suspend fun getInventory(@PathVariable placeId: Int): ResponseEntity<InventoryDTO> {
        return ResponseEntity.ok(inventoryService.getInventory(placeId))
    }
}