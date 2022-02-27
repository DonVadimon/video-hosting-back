package com.example.pudge.domain.exception

class UserAlreadyExistException(override val message: String = "User with this username already exists") :
    Exception(message)
