package de.slapps.pantry_mate.config

import org.flywaydb.core.Flyway
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class FlywayConfiguration(
    @Value("\${spring.flyway.url}") private val url: String,
    @Value("\${spring.r2dbc.username}") private val user: String,
    @Value("\${spring.r2dbc.password}") private val password: String
) {

    @Bean(initMethod = "migrate")
    fun flyway() = Flyway
        .configure()
        .dataSource(url, user, password)
        .locations("db/migration")
        .baselineOnMigrate(true)
        .validateMigrationNaming(true)
        .load()
}