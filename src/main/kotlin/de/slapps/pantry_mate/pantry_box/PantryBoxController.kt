package de.slapps.pantry_mate.pantry_box

import de.slapps.pantry_mate.pantry_box.model.dto.CreatePantryBoxDTO
import de.slapps.pantry_mate.pantry_box.model.dto.PantryBoxDTO
import de.slapps.pantry_mate.pantry_box.model.dto.PantryBoxListDTO
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/pantry_box")
class PantryBoxController(
    private val pantryBoxService: PantryBoxService,
) {

    @GetMapping(
        path = ["/{pantryId}"],
        produces = [MediaType.APPLICATION_JSON_VALUE],
    )
    suspend fun getPantryBoxesForPantry(pantryId: Int): ResponseEntity<PantryBoxListDTO> {
        return ResponseEntity.ok(pantryBoxService.getPantryBoxesForPantryId(pantryId))
    }

    @PostMapping(
        path = ["/create"],
        consumes = [MediaType.APPLICATION_JSON_VALUE],
        produces = [MediaType.APPLICATION_JSON_VALUE],
    )
    suspend fun createPantryBox(
        @RequestParam pantryId: Int,
        @RequestBody body: CreatePantryBoxDTO,
    ): ResponseEntity<PantryBoxDTO> {
        return ResponseEntity.ok(pantryBoxService.createNewPantryBox(pantryId, body))
    }

    @DeleteMapping
    suspend fun deletePantryBox(pantryBoxId: Int): ResponseEntity<Unit> {
        return ResponseEntity.ok(pantryBoxService.deletePantryBox(pantryBoxId))
    }

    @PutMapping(
        path = ["/update/{pantryBoxId}"],
    )
    suspend fun updatePantryBox(
        @PathVariable pantryBoxId: Int,
        @RequestParam quantity: Int,
    ): ResponseEntity<PantryBoxDTO> {
        return ResponseEntity.ok(pantryBoxService.updatePantryBoxQuantity(pantryBoxId, quantity))
    }
}