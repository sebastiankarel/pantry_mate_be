package de.slapps.pantry_mate.user

import de.slapps.pantry_mate.user.model.dto.UserCreatedDTO
import de.slapps.pantry_mate.user.model.dto.CreateUserDTO
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
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
    suspend fun createUser(
        @RequestBody body: CreateUserDTO,
    ): ResponseEntity<UserCreatedDTO> {
        return ResponseEntity(userService.createNewUser(body), HttpStatus.CREATED)
    }

    @DeleteMapping
    suspend fun deleteUser(@RequestParam userId: Int): ResponseEntity<Unit> {
        return ResponseEntity.ok(userService.deleteUser(userId))
    }
}