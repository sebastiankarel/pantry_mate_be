package de.slapps.pantry_mate.config.security

import kotlinx.coroutines.reactor.mono
import org.springframework.http.HttpHeaders
import org.springframework.security.core.Authentication
import org.springframework.security.web.server.authentication.ServerAuthenticationConverter
import org.springframework.stereotype.Component
import org.springframework.web.server.ServerWebExchange
import reactor.core.publisher.Mono

@Component
class AuthConverter : ServerAuthenticationConverter {

    override fun convert(exchange: ServerWebExchange?): Mono<Authentication?>? {
        return mono {
            exchange
                ?.request
                ?.headers
                ?.get(HttpHeaders.AUTHORIZATION)
                ?.firstOrNull { it.startsWith(BEARER_HEADER_PREFIX) }
                ?.removePrefix(BEARER_HEADER_PREFIX)
                ?.let { BearerToken(it) }
        }
    }

    companion object {
        private const val BEARER_HEADER_PREFIX = "Bearer "
    }
}