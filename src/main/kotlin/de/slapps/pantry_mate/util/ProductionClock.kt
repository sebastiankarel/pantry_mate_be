package de.slapps.pantry_mate.util

import org.springframework.stereotype.Component
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.ZoneOffset
import java.util.TimeZone

@Component
class ProductionClock : PantryMateClock {

    override fun now(): LocalDateTime = LocalDateTime.now(ZoneId.of(DEFAULT_ZONE))

    override fun instant(): Instant {
        val zoneOffset = ZoneId.of(DEFAULT_ZONE).rules.getOffset(Instant.now())
        return now().toInstant(zoneOffset)
    }

    companion object {
        private const val DEFAULT_ZONE = "Europe/Berlin"
    }
}