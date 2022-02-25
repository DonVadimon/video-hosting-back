package com.example.pudge.users

import com.example.pudge.users.dto.CreateUserDto
import com.example.pudge.users.exceptions.UserAlreadyExistException
import com.example.pudge.users.exceptions.UserNotFoundException
import org.springframework.data.repository.findByIdOrNull
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service
import java.util.*


@Service
class UserService(private val userRepository: UserRepository) : UserDetailsService {
    fun getAllUsers(): Iterable<UserEntity> = this.userRepository.findAll()

    fun getById(id: Long): UserEntity = this.userRepository.findByIdOrNull(id) ?: throw UserNotFoundException()

    fun getByUsername(username: String): UserEntity =
        this.userRepository.findByUsername(username) ?: throw UserNotFoundException()

    fun createUser(user: CreateUserDto): UserEntity {
        if (this.userRepository.findByUsername(user.username) !== null) {
            throw UserAlreadyExistException()
        }
        return this.userRepository.save(UserEntity(user.username, user.password))
    }

    fun deleteUser(id: Long): UserEntity {
        val deletedUser = this.getById(id)
        this.userRepository.deleteById(id)
        return deletedUser
    }

    override fun loadUserByUsername(username: String?): UserDetails {
        val u = this.getByUsername(username ?: "")
        if (Objects.isNull(u)) {
            throw UsernameNotFoundException("User is not found $username")
        }
        return User(u.username, u.password, true, true, true, true, HashSet())
    }
}
