package de.slapps.pantry_mate.pantry

import de.slapps.pantry_mate.pantry.model.dto.CreatePantryBoxDTO
import de.slapps.pantry_mate.pantry.model.dto.CreatePantryDTO
import de.slapps.pantry_mate.pantry.model.dto.PantryBoxAddedDTO
import de.slapps.pantry_mate.pantry.model.dto.PantryCreatedDTO
import de.slapps.pantry_mate.pantry.model.dto.PantryListDTO
import org.springframework.http.HttpStatus
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
        path = ["/list/{userId}"],
        produces = [MediaType.APPLICATION_JSON_VALUE],
    )
    suspend fun getPantryListForUser(@PathVariable userId: Int): ResponseEntity<PantryListDTO> {
        return ResponseEntity.ok(pantryService.getPantryBoxesForUser(userId))
    }

    @PostMapping(
        path = ["/create/{userId}"],
        consumes = [MediaType.APPLICATION_JSON_VALUE],
        produces = [MediaType.APPLICATION_JSON_VALUE],
    )
    suspend fun createNewPantry(
        @PathVariable userId: Int,
        @RequestBody body: CreatePantryDTO,
    ): ResponseEntity<PantryCreatedDTO> {
        return ResponseEntity(
            pantryService.createNewPantry(userId, body),
            HttpStatus.CREATED,
        )
    }

    // TODO delete pantry

    // TODO delete box

    // TODO alter box

    @PostMapping(
        path = ["/add/{userId}/{pantryId}"],
        consumes = [MediaType.APPLICATION_JSON_VALUE],
        produces = [MediaType.APPLICATION_JSON_VALUE],
    )
    suspend fun postPantryBox(
        @PathVariable userId: Int,
        @PathVariable pantryId: Int,
        @RequestBody body: CreatePantryBoxDTO,
    ): ResponseEntity<PantryBoxAddedDTO> {
        return ResponseEntity.ok(pantryService.addPantryBox(userId, pantryId, body))
    }
}