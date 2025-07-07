package de.slapps.pantry_mate.user

import de.slapps.pantry_mate.user.model.User
import de.slapps.pantry_mate.user.model.dto.UserCreatedResponseDTO
import de.slapps.pantry_mate.user.model.dto.UserDTO
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import java.time.LocalDateTime

@Component
class UserService {

    @Autowired
    private lateinit var userRepository: UserRepository

    suspend fun createNewUser(userDTO: UserDTO): UserCreatedResponseDTO {
        val user = User(
            username = userDTO.username,
            email = userDTO.email,
            password = userDTO.password, // TODO HASH
            createdOn = LocalDateTime.now(),
        )
        return userRepository.save(user).id?.let {
            UserCreatedResponseDTO(it)
        } ?: throw IllegalStateException("User not created!") // TODO
    }
}
