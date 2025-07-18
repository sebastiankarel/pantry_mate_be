package de.slapps.pantry_mate.pantry

import de.slapps.pantry_mate.errors.PantryNotFoundException
import de.slapps.pantry_mate.errors.UserNotFoundException
import de.slapps.pantry_mate.pantry.model.Pantry
import de.slapps.pantry_mate.pantry.model.dto.PantryDTO
import de.slapps.pantry_mate.pantry.model.dto.PantryListDTO
import de.slapps.pantry_mate.pantry.model.toDTO
import de.slapps.pantry_mate.util.PantryMateClock
import kotlinx.coroutines.flow.toList
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.dao.DataIntegrityViolationException
import org.springframework.stereotype.Component

@Component
class PantryService {

    @Autowired
    private lateinit var pantryRepository: PantryRepository
    @Autowired
    private lateinit var clock: PantryMateClock

    suspend fun getPantriesForUser(userId: Int): PantryListDTO {
        return pantryRepository.findAllByUserId(userId)
            .toList()
            .map { pantry ->
                pantry.toDTO()
            }.let { pantryDTOList ->
                PantryListDTO(pantryDTOList)
            }
    }

    suspend fun createNewPantry(
        userId: Int,
        pantryName: String,
    ): PantryDTO {
        val pantry = Pantry(
            name = pantryName,
            userId = userId,
            createdOn = clock.now(),
        )
        return try {
            pantryRepository.save(pantry).toDTO()
        } catch (e: DataIntegrityViolationException) {
            e.printStackTrace()
            throw UserNotFoundException()
        }
    }

    suspend fun deletePantry(pantryId: Int) {
        if (pantryRepository.existsById(pantryId)) {
            pantryRepository.deleteById(pantryId)
        } else {
            throw PantryNotFoundException()
        }
    }
}