package de.slapps.pantry_mate.login

import de.slapps.pantry_mate.config.security.JWTService
import de.slapps.pantry_mate.login.model.LoginRequestDTO
import de.slapps.pantry_mate.login.model.LoginResponseDTO
import de.slapps.pantry_mate.user.PMUserDetailsService
import de.slapps.pantry_mate.user.model.PMUserDetails
import de.slapps.pantry_mate.user.model.User
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.reactor.mono
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest
import org.springframework.boot.test.context.TestConfiguration
import org.springframework.context.annotation.Bean
import org.springframework.http.MediaType
import org.springframework.security.core.userdetails.ReactiveUserDetailsService
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.test.context.support.WithMockUser
import org.springframework.security.test.web.reactive.server.SecurityMockServerConfigurers.csrf
import org.springframework.test.web.reactive.server.WebTestClient
import java.time.LocalDateTime
import java.time.Month

@WithMockUser
@WebFluxTest(LoginController::class)
class LoginControllerTest {

    @Autowired
    lateinit var webTestClient: WebTestClient

    @Autowired
    lateinit var userDetailsService: ReactiveUserDetailsService

    @Autowired
    lateinit var jwtService: JWTService

    @Autowired
    lateinit var passwordEncoder: PasswordEncoder

    @Test
    fun `on login, service is called with correct arguments`() = runTest {
        // given
        val loginRequestBody = LoginRequestDTO(
            username = "JohnDoe89",
            password = "sbdf7w7rz2grbd",
        )
        val expResponseBody = LoginResponseDTO(
            token = "ey293u346592zr3hgofzg34rz239ze29p3p9283zrp92z43p9rzh23p9rh2p93hp247zr2hpfhfp9u3h",
        )
        // when
        webTestClient
            .mutateWith(csrf())
            .post()
            .uri("/api/login")
            .accept(MediaType.ALL)
            .header("Content-Type", "application/json;charset=UTF-8")
            .body(mono { loginRequestBody }, LoginRequestDTO::class.java)
            .exchange()
        // then
            .expectStatus().isOk
            .expectBody(LoginResponseDTO::class.java)
            .isEqualTo(expResponseBody)

        verify(exactly = 1) { userDetailsService.findByUsername("JohnDoe89") }
        verify(exactly = 1) { passwordEncoder.matches("sbdf7w7rz2grbd", "sbdf7w7rz2grbd") }
        verify(exactly = 1) { jwtService.generateToken("JohnDoe89") }
    }

    @TestConfiguration
    class LoginTestConfig {

        @Bean
        fun getUserDetailsService(): ReactiveUserDetailsService {
            return mockk<PMUserDetailsService> {
                every { findByUsername("JohnDoe89") } answers {
                    mono {
                        PMUserDetails(
                            User(
                                id = 1,
                                username = "JohnDoe89",
                                email = "j.doe89@yahoo.com",
                                password = "sbdf7w7rz2grbd",
                                createdOn = LocalDateTime.of(2025, Month.of(7), 16, 11, 21),
                            ),
                        )
                    }
                }
            }
        }

        @Bean
        fun getJWTService(): JWTService {
            return mockk<JWTService> {
                every { generateToken("JohnDoe89") } answers {
                    "ey293u346592zr3hgofzg34rz239ze29p3p9283zrp92z43p9rzh23p9rh2p93hp247zr2hpfhfp9u3h"
                }
            }
        }

        @Bean
        fun getPasswordEncoder(): PasswordEncoder {
            return mockk<PasswordEncoder> {
                every { matches(any(), any()) } answers { call ->
                    val raw = call.invocation.args[0] as String
                    val encoded = call.invocation.args[1] as String
                    raw == encoded // raw and ecoded are same in test
                }
            }
        }
    }
}