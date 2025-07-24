package de.slapps.pantry_mate.errors

class UserNotFoundException : Exception()
class UserAlreadyExistsException: Exception()

class InvalidCredentialsException : Exception()
class InvalidTokenException : Exception()

class PantryNotFoundException : Exception()
class PantryAlreadyExistsException : Exception()

class PantryBoxNotFoundException : Exception()
