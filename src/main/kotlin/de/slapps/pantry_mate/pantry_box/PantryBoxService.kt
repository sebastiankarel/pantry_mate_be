package de.slapps.pantry_mate.pantry_box

import de.slapps.pantry_mate.errors.PantryBoxNotFoundException
import de.slapps.pantry_mate.errors.UserNotFoundException
import de.slapps.pantry_mate.pantry.model.toDTO
import de.slapps.pantry_mate.pantry_box.model.PantryBox
import de.slapps.pantry_mate.pantry_box.model.dto.CreatePantryBoxDTO
import de.slapps.pantry_mate.pantry_box.model.dto.PantryBoxDTO
import de.slapps.pantry_mate.pantry_box.model.dto.PantryBoxListDTO
import de.slapps.pantry_mate.pantry_box.model.toDTO
import de.slapps.pantry_mate.util.PantryMateClock
import kotlinx.coroutines.flow.toList
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.dao.DataIntegrityViolationException
import org.springframework.stereotype.Component

@Component
class PantryBoxService {

    @Autowired
    private lateinit var pantryBoxRepository: PantryBoxRepository
    @Autowired
    private lateinit var clock: PantryMateClock

    suspend fun getPantryBoxesForPantryId(
        pantryId: Int,
    ): PantryBoxListDTO {
        return pantryBoxRepository.findAllByPantryId(pantryId)
            .toList()
            .map { pantryBox ->
                pantryBox.toDTO()
            }
            .let { pantryBoxes ->
                PantryBoxListDTO(
                    pantryId = pantryId,
                    pantryBoxes = pantryBoxes,
                )
            }
    }

    suspend fun createNewPantryBox(
        pantryId: Int,
        createPantryBoxDTO: CreatePantryBoxDTO
    ): PantryBoxDTO {
        val pantryBox = PantryBox(
            name = createPantryBoxDTO.name,
            quantity = createPantryBoxDTO.quantity,
            quantityUnit = createPantryBoxDTO.quantityUnit,
            pantryId = pantryId,
            createdOn = clock.now(),
        )
        return try {
            pantryBoxRepository.save(pantryBox).toDTO()
        } catch (e: DataIntegrityViolationException) {
            e.printStackTrace()
            throw UserNotFoundException()
        }
    }

    suspend fun updatePantryBoxQuantity(
        pantryBoxId: Int,
        quantity: Int,
    ): PantryBoxDTO {
        return pantryBoxRepository.findById(pantryBoxId)?.let {
            pantryBoxRepository.save(it.copy(quantity = quantity)).toDTO()
        } ?: throw PantryBoxNotFoundException()
    }

    suspend fun deletePantryBox(pantryBoxId: Int) {
        if (pantryBoxRepository.existsById(pantryBoxId)) {
            pantryBoxRepository.deleteById(pantryBoxId)
        } else {
            throw PantryBoxNotFoundException()
        }
    }
}