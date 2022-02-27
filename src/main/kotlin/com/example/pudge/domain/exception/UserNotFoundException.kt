package com.example.pudge.domain.exception

class UserNotFoundException(override val message: String = "User not found") :
    Exception(message)
