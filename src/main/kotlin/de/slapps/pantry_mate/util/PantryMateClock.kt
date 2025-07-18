package de.slapps.pantry_mate.util

import java.time.LocalDateTime

interface PantryMateClock {

    fun now(): LocalDateTime
}