package de.slapps.pantry_mate.user

import de.slapps.pantry_mate.user.model.dto.CreateUserDTO
import de.slapps.pantry_mate.user.model.dto.UserCreatedDTO
import de.slapps.pantry_mate.util.PantryMateClock
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.just
import io.mockk.mockk
import io.mockk.runs
import kotlinx.coroutines.reactor.mono
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest
import org.springframework.boot.test.context.TestConfiguration
import org.springframework.context.annotation.Bean
import org.springframework.http.MediaType
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.test.context.support.WithMockUser
import org.springframework.security.test.web.reactive.server.SecurityMockServerConfigurers.csrf
import org.springframework.test.web.reactive.server.WebTestClient

@WithMockUser
@WebFluxTest(UserController::class)
class UserControllerTest {

    @Autowired
    lateinit var webTestClient: WebTestClient

    @Autowired
    lateinit var userServiceMock: UserService

    @Test
    fun `on create user, service is called with correct arguments`() = runTest {
        // given
        val requestBody = CreateUserDTO(
            username = "JohnDoe89",
            email = "j.doe@yahoo.com",
            password = "78Hz65GVFE_5G",
        )
        val expResponseBody = UserCreatedDTO(
            userId = 1,
            username = "JohnDoe89",
            email = "j.doe@yahoo.com",
        )
        // when
        webTestClient
            .mutateWith(csrf())
            .post()
            .uri("/api/user/create")
            .accept(MediaType.ALL)
            .header("Content-Type", "application/json;charset=UTF-8")
            .body(mono { requestBody }, CreateUserDTO::class.java)
            .exchange()
        // then
            .expectStatus().isCreated
            .expectBody(UserCreatedDTO::class.java)
            .isEqualTo(expResponseBody)
        coVerify(exactly = 1) { userServiceMock.createNewUser(requestBody) }
    }

    @Test
    fun `on delete user, service is called with correct arguments`() = runTest {
        // given
        val requestBody = CreateUserDTO(
            username = "JohnDoe89",
            email = "j.doe@yahoo.com",
            password = "78Hz65GVFE_5G",
        )
        // when
        webTestClient
            .mutateWith(csrf())
            .delete()
            .uri("/api/user?userId=1")
            .exchange()
            .expectStatus().isOk
        coVerify(exactly = 1) { userServiceMock.deleteUser(1) }
    }

    @TestConfiguration
    class UserTestConfig {
        @Bean
        fun getUserService(): UserService {
            return mockk<UserService> {
                coEvery { createNewUser(any()) } answers {
                    UserCreatedDTO(
                        userId = 1,
                        username = "JohnDoe89",
                        email = "j.doe@yahoo.com",
                    )
                }
                coEvery { deleteUser(any()) } just runs
            }
        }
        @Bean
        fun getUserRepository(): UserRepository = mockk()
        @Bean
        fun getPasswordEncoder(): PasswordEncoder = mockk()
        @Bean
        fun getClock(): PantryMateClock = mockk()
    }
}