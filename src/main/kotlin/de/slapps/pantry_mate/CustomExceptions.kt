package de.slapps.pantry_mate

class UnknownErrorException: Exception()

class UserNotFoundException : Exception()
class UserAlreadyExistsException: Exception()

class EmptyPantryBoxException : Exception()