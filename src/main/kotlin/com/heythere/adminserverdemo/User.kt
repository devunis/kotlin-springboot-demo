package com.heythere.adminserverdemo

import org.springframework.data.repository.CrudRepository
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Repository
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RestController
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id

@Entity
data class User(
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        val id: Long? = null,
        val username: String,
        val password: String

)

@RestController
class UserController (val userRepository: UserRepository){
    @GetMapping
    fun getAllUser(): ResponseEntity<*> = ResponseEntity.ok(userRepository.findAll())

    @PostMapping
    fun registerUser(): ResponseEntity<*> = ResponseEntity.ok(userRepository.save(User(username = "test", password = "test")))

}

@Repository
interface UserRepository : CrudRepository<User, Long>

