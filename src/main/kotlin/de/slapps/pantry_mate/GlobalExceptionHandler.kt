package de.slapps.pantry_mate

import de.slapps.pantry_mate.errors.PantryAlreadyExistsException
import de.slapps.pantry_mate.errors.PantryBoxNotFoundException
import de.slapps.pantry_mate.errors.PantryNotFoundException
import de.slapps.pantry_mate.errors.UserAlreadyExistsException
import de.slapps.pantry_mate.errors.UserNotFoundException
import de.slapps.pantry_mate.errors.InvalidCredentialsException
import de.slapps.pantry_mate.errors.InvalidTokenException
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler

@ControllerAdvice
class GlobalExceptionHandler {

    @ExceptionHandler(UserNotFoundException::class)
    fun handleUserNotFoundException(exception: UserNotFoundException): ResponseEntity<String> {
        return ResponseEntity("The user does not exist.", HttpStatus.NOT_FOUND)
    }

    @ExceptionHandler(UserAlreadyExistsException::class)
    fun handleUserAlreadyExistsException(exception: UserAlreadyExistsException): ResponseEntity<String> {
        return ResponseEntity("The user already exists.", HttpStatus.CONFLICT)
    }

    @ExceptionHandler(InvalidCredentialsException::class)
    fun handleInvalidCredentialsException(
        exception: InvalidCredentialsException,
    ): ResponseEntity<String> {
        return ResponseEntity("The credentials incorrect.", HttpStatus.UNAUTHORIZED)
    }

    @ExceptionHandler(InvalidTokenException::class)
    fun handleInvalidTokenException(exception: InvalidTokenException): ResponseEntity<String> {
        return ResponseEntity("The token is invalid or expired.", HttpStatus.UNAUTHORIZED)
    }

    @ExceptionHandler(PantryNotFoundException::class)
    fun handlePantryNotFoundException(exception: PantryNotFoundException): ResponseEntity<String> {
        return ResponseEntity("The pantry does not exist.", HttpStatus.BAD_REQUEST)
    }

    @ExceptionHandler(PantryAlreadyExistsException::class)
    fun handlePantryAlreadyExistsException(exception: PantryAlreadyExistsException): ResponseEntity<String> {
        return ResponseEntity("The ppantry already exists.", HttpStatus.BAD_REQUEST)
    }

    @ExceptionHandler(PantryBoxNotFoundException::class)
    fun handleIllegalStateException(exception: PantryBoxNotFoundException): ResponseEntity<String> {
        return ResponseEntity("The pantry box does not exist.", HttpStatus.BAD_REQUEST)
    }

    @ExceptionHandler(IllegalStateException::class)
    fun handleIllegalStateException(exception: IllegalStateException): ResponseEntity<String> {
        return ResponseEntity(exception.message, HttpStatus.BAD_REQUEST)
    }
}