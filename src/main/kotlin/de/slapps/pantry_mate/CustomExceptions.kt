package de.slapps.pantry_mate

class UnknownErrorException: Exception()

class UserNotFoundException : Exception()
class UserAlreadyExistsException: Exception()

class PantryNotFoundException : Exception()
class PantryAlreadyExistsException : Exception()

class EmptyPantryBoxException : Exception()