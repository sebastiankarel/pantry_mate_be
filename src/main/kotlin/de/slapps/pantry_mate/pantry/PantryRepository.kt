package de.slapps.pantry_mate.pantry

import de.slapps.pantry_mate.pantry.model.Pantry
import kotlinx.coroutines.flow.Flow
import org.springframework.data.r2dbc.repository.Query
import org.springframework.data.repository.kotlin.CoroutineCrudRepository
import org.springframework.stereotype.Repository

@Repository
interface PantryRepository : CoroutineCrudRepository<Pantry, Int> {

    @Query("SELECT * FROM pantry_mate.pantry WHERE pantry_mate.pantry.user_id = :userId JOIN pantry_mate.item ON pantry_mate.item.pantry_id = pantry_mate.pantry.id")
    fun findAllByUserId(userId: Int): Flow<Pantry>
}