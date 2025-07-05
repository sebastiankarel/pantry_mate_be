package de.slapps.pantry_mate

import kotlinx.coroutines.reactive.awaitSingle
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.AfterEach
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.r2dbc.core.DatabaseClient
import org.springframework.r2dbc.core.await
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.DynamicPropertyRegistry
import org.springframework.test.context.DynamicPropertySource
import org.testcontainers.containers.PostgreSQLContainer
import org.testcontainers.containers.wait.strategy.Wait
import org.testcontainers.junit.jupiter.Testcontainers
import java.io.IOException

@SpringBootTest(classes = [PantryMateApplication::class])
@Testcontainers
@ActiveProfiles("test")
abstract class AbstractIntegrationTest {

    @Autowired
    lateinit var databaseClient: DatabaseClient

    @AfterEach
    @Throws(IOException::class, InterruptedException::class)
    fun restoreDatabase() {
        runBlocking {
            val tableNames = databaseClient
                .sql("SELECT table_name FROM information_schema.tables WHERE table_schema='pantry_mate' AND table_type='BASE_TABLE'")
                .map { row, _ ->
                    row.get("table_name") as String
                }
                .all()
                .collectList()
                .awaitSingle()
                .filter { it != "flyway_schema_history" }
                .joinToString { "voyager.$it" }
            databaseClient
                .sql("TRUNCATE $tableNames")
                .await()
        }
    }

    companion object {
        @JvmStatic
        private val postgresContainer = PostgreSQLContainer("postgres:16-alpine")
            .withUsername("postgres")
            .withPassword("12345")
            // add init script here if needed
            .also { it.waitingFor(Wait.forListeningPort()) }

        @DynamicPropertySource
        @JvmStatic
        fun postgresProperties(registry: DynamicPropertyRegistry) {
            postgresContainer.start()
            val jdbcUrl = postgresContainer.jdbcUrl
            registry.add("spring.flayway.url") { "$jdbcUrl&currentSchema=pantry_mate"}
            registry.add("spring.r2dbc.url") { jdbcUrl.replace("jdbc", "r2dbc") }
        }
    }
}