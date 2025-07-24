package de.slapps.pantry_mate.util

import java.time.Instant
import java.time.LocalDateTime

interface PantryMateClock {

    fun now(): LocalDateTime

    fun instant(): Instant
}