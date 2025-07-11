package de.slapps.pantry_mate.user

import de.slapps.pantry_mate.user.model.dto.CreateUserResponseDTO
import de.slapps.pantry_mate.user.model.dto.UserDTO
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/user")
class UserController(
    private val userService: UserService,
) {

    @PostMapping(
        path = ["/create"],
        consumes = [MediaType.APPLICATION_JSON_VALUE],
        produces = [MediaType.APPLICATION_JSON_VALUE],
    )
    suspend fun createNewUser(
        @RequestBody body: UserDTO,
    ): ResponseEntity<CreateUserResponseDTO> {
        return ResponseEntity(userService.createNewUser(body), HttpStatus.CREATED)
    }

    @DeleteMapping(path = ["/{userId}"])
    suspend fun deleteUser(@PathVariable userId: Int): ResponseEntity<Unit> {
        return ResponseEntity.ok(userService.deleteUser(userId))
    }
}