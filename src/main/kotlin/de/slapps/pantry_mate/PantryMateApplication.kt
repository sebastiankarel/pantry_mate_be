package de.slapps.pantry_mate

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class PantryMateApplication

fun main(args: Array<String>) {
	runApplication<PantryMateApplication>(*args)
}
