package de.slapps.pantry_mate.util

import org.springframework.stereotype.Component
import java.time.LocalDateTime
import java.time.ZoneId

@Component
class ProductionClock : PantryMateClock {

    override fun now(): LocalDateTime = LocalDateTime.now(ZoneId.of(DEFAULT_ZONE))

    companion object {
        private const val DEFAULT_ZONE = "Europe/Berlin"
    }
}