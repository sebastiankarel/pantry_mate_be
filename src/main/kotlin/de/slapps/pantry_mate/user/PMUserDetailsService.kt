package de.slapps.pantry_mate.user

import de.slapps.pantry_mate.user.model.PMUserDetails
import kotlinx.coroutines.reactor.mono
import org.springframework.security.core.userdetails.ReactiveUserDetailsService
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service
import reactor.core.publisher.Mono

@Service
class PMUserDetailsService(
    private val userRepository: UserRepository,
) : ReactiveUserDetailsService {

    override fun findByUsername(username: String?): Mono<UserDetails?>? {
        return mono {
            userRepository.findByUsername(username ?: "")?.let { user ->
                PMUserDetails(user)
            } ?: throw UsernameNotFoundException("The user $username does not exist.")
        }
    }

}