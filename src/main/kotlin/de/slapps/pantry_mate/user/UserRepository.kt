package de.slapps.pantry_mate.user

import de.slapps.pantry_mate.user.model.User
import org.springframework.data.repository.kotlin.CoroutineCrudRepository
import org.springframework.stereotype.Repository

@Repository
interface UserRepository : CoroutineCrudRepository<User, Int> {

    suspend fun findByUsername(userName: String): User?
}
