package com.heythere.adminserverdemo

import org.springframework.data.repository.CrudRepository
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Repository
import org.springframework.stereotype.Service
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RestController
import javax.persistence.*

data class UserResponseDto(
        val username: String,
        val posts: List<PostResponseDto> = ArrayList()
){
    companion object{
        fun toMapper(user: User) = UserResponseDto(user.username, user.posts.map {
            post -> PostResponseDto.toMapper(post) })
    }
}

@Entity
data class User(
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        val id: Long = 1,
        val username: String,
        val password: String,
        @OneToMany(mappedBy = "author")
        val posts: List<Post> = ArrayList()
) : BaseTimeEntity()

@RestController
class UserController (private val userService: UserService){
    @GetMapping
    fun getAllUser() = ResponseEntity.ok(userService.getAllUser())

    @PostMapping
    fun registerUser() = ResponseEntity.ok(userService.save(User(username = "test", password = "test")))

}

@Service
class UserService(private val userRepository: UserRepository) {
    fun getAllUser() = userRepository.findAll().map {
        user ->  UserResponseDto.toMapper(user)
    }
    fun getUser(id:Long) = userRepository.findById(id).get()

    fun save(user: User) = userRepository.save(user).username
}

@Repository
interface UserRepository : CrudRepository<User, Long>

