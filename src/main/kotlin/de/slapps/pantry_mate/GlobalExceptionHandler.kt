package de.slapps.pantry_mate

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler

@ControllerAdvice
class GlobalExceptionHandler {

    @ExceptionHandler(UnknownErrorException::class)
    fun handleUserUnknownErrorException(exception: UnknownErrorException): ResponseEntity<String> {
        return ResponseEntity("An unknown error occurred.", HttpStatus.BAD_REQUEST)
    }

    @ExceptionHandler(UserNotFoundException::class)
    fun handleUserNotFoundException(exception: UserNotFoundException): ResponseEntity<String> {
        return ResponseEntity("The user does not exist.", HttpStatus.NOT_FOUND)
    }

    @ExceptionHandler(UserAlreadyExistsException::class)
    fun handleUserAlreadyExistsException(exception: UserAlreadyExistsException): ResponseEntity<String> {
        return ResponseEntity("The user already exists.", HttpStatus.CONFLICT)
    }

    @ExceptionHandler(EmptyPantryBoxException::class)
    fun handleEmptyPantryBoxException(exception: EmptyPantryBoxException): ResponseEntity<String> {
        return ResponseEntity("Quantity must be at least 1.", HttpStatus.BAD_REQUEST)
    }
}