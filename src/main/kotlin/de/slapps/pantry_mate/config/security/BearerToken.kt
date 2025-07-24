package de.slapps.pantry_mate.config.security

import org.springframework.security.authentication.AbstractAuthenticationToken
import org.springframework.security.core.authority.AuthorityUtils

class BearerToken(
    private val token: String,
) : AbstractAuthenticationToken(AuthorityUtils.NO_AUTHORITIES) {

    override fun getCredentials(): String? {
        return token
    }

    override fun getPrincipal(): String? {
        return token
    }
}