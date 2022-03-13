package com.example.pudge.domain.dto

data class CommonAuthResponse(val user: UserView? = null, val errorMessage: String? = null)
