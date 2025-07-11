package de.slapps.pantry_mate.user

import de.slapps.pantry_mate.UserAlreadyExistsException
import de.slapps.pantry_mate.UserNotFoundException
import de.slapps.pantry_mate.user.model.User
import de.slapps.pantry_mate.user.model.dto.CreateUserResponseDTO
import de.slapps.pantry_mate.user.model.dto.UserDTO
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import java.time.LocalDateTime

@Component
class UserService {

    @Autowired
    private lateinit var userRepository: UserRepository

    suspend fun createNewUser(userDTO: UserDTO): CreateUserResponseDTO {
        val existingUser = userRepository.findByUsername(userDTO.username)
        return if (existingUser == null) {
            val user = User(
                username = userDTO.username,
                email = userDTO.email,
                password = userDTO.password, // TODO HASH
                createdOn = LocalDateTime.now(),
            )
            userRepository.save(user).id?.let {
                CreateUserResponseDTO(
                    userName = userDTO.username,
                    userId = it,
                )
            } ?: throw UnknownError()
        } else {
            throw UserAlreadyExistsException()
        }
    }

    suspend fun deleteUser(userId: Int){
        return if (userRepository.existsById(userId)) {
            userRepository.deleteById(userId)
        } else {
            throw UserNotFoundException()
        }
    }
}
