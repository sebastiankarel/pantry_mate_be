package de.slapps.pantry_mate.pantry

import de.slapps.pantry_mate.pantry.model.PantryBox
import kotlinx.coroutines.flow.Flow
import org.springframework.data.repository.kotlin.CoroutineCrudRepository
import org.springframework.stereotype.Repository

@Repository
interface PantryBoxRepository : CoroutineCrudRepository<PantryBox, Int> {

    fun findAllByUserId(userId: Int): Flow<PantryBox>
}