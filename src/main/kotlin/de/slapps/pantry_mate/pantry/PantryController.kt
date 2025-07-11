package de.slapps.pantry_mate.pantry

import de.slapps.pantry_mate.pantry.model.dto.PantryBoxDTO
import de.slapps.pantry_mate.pantry.model.dto.PantryContentDTO
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/pantry")
class PantryController(
    private val pantryService: PantryService,
) {

    @GetMapping(
        path = ["/{userId}"],
        produces = [MediaType.APPLICATION_JSON_VALUE],
    )
    suspend fun getPantryContentForUser(@PathVariable userId: Int): ResponseEntity<PantryContentDTO> {
        return ResponseEntity.ok(pantryService.getPantryBoxesForUser(userId))
    }

    @PostMapping(
        path = ["/{userId}"],
        consumes = [MediaType.APPLICATION_JSON_VALUE],
        produces = [MediaType.APPLICATION_JSON_VALUE],
    )
    suspend fun postPantryBox(
        @PathVariable userId: Int,
        @RequestBody body: PantryBoxDTO,
    ) {
        pantryService.savePantryBox(userId, body)
    }
}