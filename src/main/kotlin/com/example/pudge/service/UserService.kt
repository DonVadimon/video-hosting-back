package com.example.pudge.service

import com.example.pudge.domain.dto.CreateUserDto
import com.example.pudge.domain.entity.UserEntity
import com.example.pudge.domain.exception.UserAlreadyExistException
import com.example.pudge.domain.exception.UserNotFoundException
import com.example.pudge.repository.UserRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import javax.validation.ValidationException


@Service
class UserService(private val userRepository: UserRepository, private val passwordEncoder: PasswordEncoder) :
    UserDetailsService {
    fun getAll(): Iterable<UserEntity> = this.userRepository.findAll()

    fun getById(id: Long): UserEntity = this.userRepository.findByIdOrNull(id) ?: throw UserNotFoundException()

    fun getByUsername(username: String): UserEntity =
        this.userRepository.findByUsername(username) ?: throw UserNotFoundException()

    fun createUser(dto: CreateUserDto?): UserEntity {
        if (dto == null) {
            throw ValidationException("No register data")
        }

        if (dto.username?.let { this.userRepository.findByUsername(it) } !== null) {
            throw UserAlreadyExistException()
        }

        if (dto.password != dto.passwordRepeat) {
            throw ValidationException("Passwords don't match")
        }

        val user = User(dto.username, passwordEncoder.encode(dto.password), HashSet())

        return this.userRepository.save(UserEntity(username = user.username, password = user.password))
    }

    fun deleteUser(id: Long): UserEntity {
        val deletedUser = this.getById(id)
        this.userRepository.deleteById(id)
        return deletedUser
    }

    override fun loadUserByUsername(username: String): UserDetails {
        val user = this.getByUsername(username)
        return User(user.username, user.password, true, true, true, true, HashSet())
    }
}
