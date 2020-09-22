package com.heythere.adminserverdemo

import org.springframework.data.repository.CrudRepository
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Controller
import org.springframework.stereotype.Repository
import org.springframework.stereotype.Service
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RestController
import javax.persistence.*

class PostResponseDto(
        val title : String,
        val content : String,
        val author : String
){
    companion object {
        fun toMapper(post: Post) = PostResponseDto(post.title, post.content, post.author.username) }

}

@Entity
data class Post(
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        val id : Long ? = null,
        val title : String,
        val content : String,
        @ManyToOne(fetch = FetchType.LAZY)
        @JoinColumn
        val author : User
)

@Controller
class PostController(private val postService: PostService, private val userService: UserService){
    @GetMapping("/post")
    fun getAllPost() = ResponseEntity.ok(postService.getAllPost())

    @PostMapping("/post")
    fun savePost() = ResponseEntity.ok(postService.savePost(
            Post(title = "test", content = "test", author = userService.getUser(1))))
}

@Service
class PostService(private val postRepository: PostRepository){
    fun getAllPost() = postRepository.findAll().map {
        post -> PostResponseDto.toMapper(post)
    }

    fun savePost(post : Post) = postRepository.save(post).title
}

@Repository
interface PostRepository : CrudRepository<Post, Long>