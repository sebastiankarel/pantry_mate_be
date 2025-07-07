package de.slapps.pantry_mate.user

import de.slapps.pantry_mate.user.model.dto.UserDTO
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/pm_user")
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
    ) {
        userService.createNewUser(body)
    }
}