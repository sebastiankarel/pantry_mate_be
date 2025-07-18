package de.slapps.pantry_mate.user

import de.slapps.pantry_mate.errors.UserAlreadyExistsException
import de.slapps.pantry_mate.errors.UserNotFoundException
import de.slapps.pantry_mate.user.model.User
import de.slapps.pantry_mate.user.model.dto.UserCreatedDTO
import de.slapps.pantry_mate.user.model.dto.CreateUserDTO
import de.slapps.pantry_mate.user.model.toUserCreatedDTO
import de.slapps.pantry_mate.util.PantryMateClock
import de.slapps.pantry_mate.util.toSHA256
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.dao.DuplicateKeyException
import org.springframework.stereotype.Component

@Component
class UserService {

    @Autowired
    private lateinit var userRepository: UserRepository
    @Autowired
    private lateinit var clock: PantryMateClock

    suspend fun createNewUser(userDTO: CreateUserDTO): UserCreatedDTO {
        val user = User(
            username = userDTO.username,
            email = userDTO.email,
            password = userDTO.password.toSHA256(),
            createdOn = clock.now(),
        )
        return try {
            userRepository.save(user).toUserCreatedDTO()
        } catch (e: DuplicateKeyException) {
            e.printStackTrace()
            throw UserAlreadyExistsException()
        }
    }

    suspend fun deleteUser(userId: Int) {
        if (userRepository.existsById(userId)) {
            userRepository.deleteById(userId)
        } else {
            throw UserNotFoundException()
        }
    }
}
