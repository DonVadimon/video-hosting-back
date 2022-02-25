package com.example.pudge.users.exceptions

class UserNotFoundException(override val message: String = "User not found") :
    Exception(message)
