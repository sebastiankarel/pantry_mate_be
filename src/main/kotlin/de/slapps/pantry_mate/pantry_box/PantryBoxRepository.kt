package de.slapps.pantry_mate.pantry_box

import de.slapps.pantry_mate.pantry_box.model.PantryBox
import kotlinx.coroutines.flow.Flow
import org.springframework.data.repository.kotlin.CoroutineCrudRepository
import org.springframework.stereotype.Repository

@Repository
interface PantryBoxRepository : CoroutineCrudRepository<PantryBox, Int> {

    fun findAllByPantryId(pantryId: Int): Flow<PantryBox>
}