package de.slapps.pantry_mate.pantry

import de.slapps.pantry_mate.EmptyPantryBoxException
import de.slapps.pantry_mate.PantryAlreadyExistsException
import de.slapps.pantry_mate.PantryNotFoundException
import de.slapps.pantry_mate.UnknownErrorException
import de.slapps.pantry_mate.pantry.model.PantryBox
import de.slapps.pantry_mate.UserNotFoundException
import de.slapps.pantry_mate.pantry.model.Pantry
import de.slapps.pantry_mate.pantry.model.dto.CreatePantryBoxDTO
import de.slapps.pantry_mate.pantry.model.dto.CreatePantryDTO
import de.slapps.pantry_mate.pantry.model.dto.PantryBoxAddedDTO
import de.slapps.pantry_mate.pantry.model.dto.PantryBoxDTO
import de.slapps.pantry_mate.pantry.model.dto.PantryCreatedDTO
import de.slapps.pantry_mate.pantry.model.dto.PantryDTO
import de.slapps.pantry_mate.pantry.model.dto.PantryListDTO
import de.slapps.pantry_mate.user.UserRepository
import kotlinx.coroutines.flow.toList
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

@Component
class PantryService {

    @Autowired
    private lateinit var pantryRepository: PantryRepository
    @Autowired
    private lateinit var pantryBoxRepository: PantryBoxRepository
    @Autowired
    private lateinit var userRepository: UserRepository

    suspend fun getPantryBoxesForUser(userId: Int): PantryListDTO? {
        return if (userRepository.existsById(userId)) {
            pantryRepository.findAllByUserId(userId)
                .toList()
                .mapNotNull { pantry ->
                    pantry.id?.let { pantryId ->
                        PantryDTO(
                            id = pantryId,
                            name = pantry.name,
                            boxes = getPantryBoxesForPantry(pantryId),
                        )
                    }
                }
                .let { PantryListDTO(it) }
        } else {
            throw UserNotFoundException()
        }
    }

    private suspend fun getPantryBoxesForPantry(
        pantryId: Int,
    ) = pantryBoxRepository.findAllByPantryId(pantryId)
        .toList()
        .mapNotNull { pantryBox ->
            pantryBox.id?.let { id ->
                PantryBoxDTO(
                    id = id,
                    itemName = pantryBox.name,
                    quantity = pantryBox.quantity,
                    createdOn = pantryBox.createdOn,
                )
            }
        }

    suspend fun createNewPantry(
        userId: Int,
        createPantryDTO: CreatePantryDTO,
    ): PantryCreatedDTO {
        if (userRepository.existsById(userId)) {
            val existingPantry = pantryRepository.findByName(createPantryDTO.name)
            return if (existingPantry == null) {
                val pantry = Pantry(
                    name = createPantryDTO.name,
                    userId = userId,
                )
                pantryRepository.save(pantry).id?.let { pantryId ->
                    PantryCreatedDTO(
                        pantryId = pantryId,
                        pantryName = createPantryDTO.name,
                    )
                } ?: throw UnknownErrorException()
            } else {
                throw PantryAlreadyExistsException()
            }
        } else {
            throw UserNotFoundException()
        }
    }

    suspend fun addPantryBox(
        userId: Int,
        pantryId: Int,
        createPantryBoxDTO: CreatePantryBoxDTO,
    ): PantryBoxAddedDTO {
        return if (userRepository.existsById(userId)) {
            if (createPantryBoxDTO.quantity > 0) {
                if (pantryRepository.existsById(pantryId)) {
                    pantryBoxRepository.save(
                        PantryBox(
                            name = createPantryBoxDTO.itemName,
                            quantity = createPantryBoxDTO.quantity,
                            pantryId = pantryId,
                            createdOn = createPantryBoxDTO.createdOn,
                        )
                    ).id?.let {
                        PantryBoxAddedDTO(
                            id = it,
                            name = createPantryBoxDTO.itemName,
                        )
                    } ?: throw UnknownErrorException()
                } else {
                    throw PantryNotFoundException()
                }
            } else {
                throw EmptyPantryBoxException()
            }
        } else {
            throw UserNotFoundException()
        }
    }
}