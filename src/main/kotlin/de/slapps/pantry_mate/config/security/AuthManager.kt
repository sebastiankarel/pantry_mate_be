package de.slapps.pantry_mate.config.security

import de.slapps.pantry_mate.errors.InvalidTokenException
import kotlinx.coroutines.reactor.awaitSingleOrNull
import kotlinx.coroutines.reactor.mono
import org.springframework.security.authentication.ReactiveAuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.userdetails.ReactiveUserDetailsService
import org.springframework.stereotype.Component
import reactor.core.publisher.Mono

@Component
class AuthManager(
    private val jwtService: JWTService,
    private val userDetailsService: ReactiveUserDetailsService,
) : ReactiveAuthenticationManager {

    override fun authenticate(authentication: Authentication?): Mono<Authentication?>? {
        return mono {
            (authentication as? BearerToken)?.let { auth ->
                val username = jwtService.getUsername(auth.credentials ?: "")
                userDetailsService.findByUsername(username).awaitSingleOrNull()?.let { userDetails ->
                    if (jwtService.validate(userDetails, auth.credentials ?: "")) {
                        UsernamePasswordAuthenticationToken(
                            userDetails.username,
                            userDetails.password,
                            userDetails.authorities,
                        )
                    } else {
                        throw InvalidTokenException()
                    }
                }
            } ?: run {
                authentication
            }
        }
    }
}