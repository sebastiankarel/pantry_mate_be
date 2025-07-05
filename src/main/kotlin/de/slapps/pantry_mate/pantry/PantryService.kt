package de.slapps.pantry_mate.pantry

import de.slapps.pantry_mate.pantry.model.dto.PantryDTO
import de.slapps.pantry_mate.pantry.model.dto.PantryListDTO
import kotlinx.coroutines.flow.toList
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

@Component
class PantryService {

    @Autowired
    private lateinit var pantryRepository: PantryRepository

    suspend fun getPantriesForUser(userId: Int): PantryListDTO? {
        return PantryListDTO(0, listOf()) // TODO
    }
}