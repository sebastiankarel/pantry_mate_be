package de.slapps.pantry_mate.config

import de.slapps.pantry_mate.config.converters.QuantityUnitReadingConverter
import de.slapps.pantry_mate.config.converters.QuantityUnitWritingConverter
import io.r2dbc.spi.ConnectionFactory
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.r2dbc.config.AbstractR2dbcConfiguration
import org.springframework.data.r2dbc.convert.R2dbcCustomConversions

@Configuration
class R2DBCConfig(
    private val connectionFactory: ConnectionFactory
): AbstractR2dbcConfiguration() {

    override fun connectionFactory(): ConnectionFactory = connectionFactory

    @Bean
    override fun r2dbcCustomConversions(): R2dbcCustomConversions {
        return R2dbcCustomConversions(
            storeConversions,
            listOf(QuantityUnitWritingConverter(), QuantityUnitReadingConverter()),
        )
    }
}