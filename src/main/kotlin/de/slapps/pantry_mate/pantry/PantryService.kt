package de.slapps.pantry_mate.pantry

import de.slapps.pantry_mate.EmptyPantryBoxException
import de.slapps.pantry_mate.pantry.model.PantryBox
import de.slapps.pantry_mate.UserNotFoundException
import de.slapps.pantry_mate.pantry.model.dto.PantryBoxDTO
import de.slapps.pantry_mate.pantry.model.dto.PantryContentDTO
import de.slapps.pantry_mate.user.UserRepository
import kotlinx.coroutines.flow.toList
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

@Component
class PantryService {

    @Autowired
    private lateinit var pantryBoxRepository: PantryBoxRepository
    @Autowired
    private lateinit var userRepository: UserRepository

    suspend fun getPantryBoxesForUser(userId: Int): PantryContentDTO? {
        return if (userRepository.existsById(userId)) {
            pantryBoxRepository.findAllByUserId(userId)
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
        } else {
            throw UserNotFoundException()
        }
    }

    suspend fun savePantryBox(
        userId: Int,
        pantryBoxDTO: PantryBoxDTO,
    ) {
        if (userRepository.existsById(userId)) {
            if (pantryBoxDTO.quantity > 0) {
                val pantryBox = PantryBox(
                    name = pantryBoxDTO.name,
                    quantity = pantryBoxDTO.quantity,
                    userId = userId,
                    createdOn = pantryBoxDTO.createdOn,
                )
                pantryBoxRepository.save(pantryBox)
            } else {
                throw EmptyPantryBoxException()
            }
        } else {
            throw UserNotFoundException()
        }
    }
}