package de.slapps.pantry_mate.pantry

import de.slapps.pantry_mate.pantry.model.Pantry
import kotlinx.coroutines.flow.Flow
import org.springframework.data.repository.kotlin.CoroutineCrudRepository
import org.springframework.stereotype.Repository

@Repository
interface PantryRepository : CoroutineCrudRepository<Pantry, Int> {

    fun findAllByUserId(userId: Int): Flow<Pantry>
}