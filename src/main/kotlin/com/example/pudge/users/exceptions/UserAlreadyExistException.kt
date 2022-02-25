package com.example.pudge.users.exceptions

class UserAlreadyExistException(override val message: String = "User with this username already exists") :
    Exception(message)
