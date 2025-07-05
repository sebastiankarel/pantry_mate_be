package de.slapps.pantry_mate.pantry

import de.slapps.pantry_mate.pantry.model.dto.PantryDTO
import de.slapps.pantry_mate.pantry.model.dto.PantryListDTO
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/pantry")
class PantryController(
    private val pantryService: PantryService,
) {

    @GetMapping("/{userId}")
    suspend fun getPantriesForUser(@PathVariable userId: Int): ResponseEntity<PantryListDTO> {
        return ResponseEntity.ok(pantryService.getPantriesForUser(userId))
    }
}