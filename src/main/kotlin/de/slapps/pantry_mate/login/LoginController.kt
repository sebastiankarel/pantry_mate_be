package de.slapps.pantry_mate.login

import de.slapps.pantry_mate.config.security.JWTService
import de.slapps.pantry_mate.errors.InvalidCredentialsException
import de.slapps.pantry_mate.login.model.LoginRequestDTO
import de.slapps.pantry_mate.login.model.LoginResponseDTO
import kotlinx.coroutines.reactor.awaitSingleOrNull
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.security.core.userdetails.ReactiveUserDetailsService
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api")
class LoginController(
    private val userDetailsService: ReactiveUserDetailsService,
    private val jwtService: JWTService,
    private val passwordEncoder: PasswordEncoder,
) {

    @PostMapping(
        path = ["/login"],
        consumes = [MediaType.APPLICATION_JSON_VALUE],
        produces = [MediaType.APPLICATION_JSON_VALUE],
    )
    suspend fun login(@RequestBody loginRequestDTO: LoginRequestDTO): ResponseEntity<LoginResponseDTO> {
        val userDetails =  userDetailsService
            .findByUsername(loginRequestDTO.username)
            ?.awaitSingleOrNull()
        val pwMatches = passwordEncoder.matches(loginRequestDTO.password, userDetails?.password)
        return if (userDetails != null && pwMatches) {
            jwtService.generateToken(userDetails.username)?.let { token ->
                ResponseEntity.ok(LoginResponseDTO(token))
            } ?: throw IllegalStateException("Failed to login. Try again later.")
        } else {
            throw InvalidCredentialsException()
        }
    }
}