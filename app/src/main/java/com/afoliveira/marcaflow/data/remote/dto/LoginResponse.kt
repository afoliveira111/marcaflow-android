package com.afoliveira.marcaflow.data.remote.dto


data class LoginResponse(
    val token: String,
    val expiresAt: String,
    val user: UserDto,
    val business: BusinessDto
)

data class UserDto(
    val id: String,
    val name: String,
    val email: String,
    val role: String
)

data class BusinessDto(
    val id: String,
    val name: String,
    val slug: String
)