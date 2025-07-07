package de.slapps.pantry_mate.pantry

import de.slapps.pantry_mate.pantry.model.PantryBox
import de.slapps.pantry_mate.pantry.model.dto.PantryBoxDTO
import de.slapps.pantry_mate.pantry.model.dto.PantryContentDTO
import kotlinx.coroutines.flow.toList
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

@Component
class PantryService {

    @Autowired
    private lateinit var pantryBoxRepository: PantryBoxRepository

    suspend fun getPantryBoxesForUser(userId: Int): PantryContentDTO? {
        return pantryBoxRepository.findAllByUserId(userId)
            .toList()
            .map { pantryBox ->
                PantryBoxDTO(
                    name = pantryBox.name,
                    quantity = pantryBox.quantity,
                    createdOn = pantryBox.createdOn,
                )
            }
            .let { boxList ->
                PantryContentDTO(
                    userId = userId,
                    pantryBoxes = boxList,
                )
            }
    }

    suspend fun savePantryBox(
        userId: Int,
        pantryBoxDTO: PantryBoxDTO,
    ) {
        val pantryBox = PantryBox(
            name = pantryBoxDTO.name,
            quantity = pantryBoxDTO.quantity,
            userId = userId,
            createdOn = pantryBoxDTO.createdOn,
        )
        pantryBoxRepository.save(pantryBox)
    }
}