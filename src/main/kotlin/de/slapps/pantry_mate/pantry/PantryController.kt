package de.slapps.pantry_mate.pantry

import de.slapps.pantry_mate.pantry.model.dto.PantryDTO
import de.slapps.pantry_mate.pantry.model.dto.PantryListDTO
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/pantry")
class PantryController(
    private val pantryService: PantryService,
) {

    @GetMapping(
        path = ["/list/{userId}"],
        produces = [MediaType.APPLICATION_JSON_VALUE],
    )
    suspend fun getPantryListForUser(@PathVariable userId: Int): ResponseEntity<PantryListDTO> {
        return ResponseEntity.ok(pantryService.getPantriesForUser(userId))
    }

    @PostMapping(
        path = ["/create"],
        produces = [MediaType.APPLICATION_JSON_VALUE],
    )
    suspend fun createPantry(
        @RequestParam userId: Int,
        @RequestParam pantryName: String,
    ): ResponseEntity<PantryDTO> {
        return ResponseEntity(
            pantryService.createNewPantry(userId, pantryName),
            HttpStatus.CREATED,
        )
    }

    @DeleteMapping("/delete")
    suspend fun deletePantry(
        @RequestParam pantryId: Int,
    ): ResponseEntity<Unit> {
        return ResponseEntity.ok(pantryService.deletePantry(pantryId))
    }
}