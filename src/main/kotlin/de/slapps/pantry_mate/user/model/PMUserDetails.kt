package de.slapps.pantry_mate.user.model

import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.userdetails.UserDetails

class PMUserDetails(private val user: User) : UserDetails {

    override fun getAuthorities(): Collection<GrantedAuthority?>? {
        return listOf()
    }

    override fun getPassword(): String? {
        return user.password
    }

    override fun getUsername(): String? {
        return user.username
    }
}