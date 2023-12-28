package org.example.itog.controller

import jakarta.websocket.server.PathParam
import org.example.itog.model.Comment
import org.example.itog.model.Forum
import org.example.itog.model.Post
import org.example.itog.repositories.ForumRepository
import org.example.itog.repositories.PostRepository
import org.example.itog.repositories.UserRepository
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.servlet.ModelAndView
import java.security.Principal
import kotlin.jvm.optionals.getOrElse
import kotlin.jvm.optionals.getOrNull

@Controller
@RequestMapping("/forum")
class ForumController(
    val forumRepo:ForumRepository,
    val postRepo:PostRepository,
    val userRepo:UserRepository,
    val commentRepo:UserRepository,
) {

    @GetMapping
    fun index() : ModelAndView {
        val modelAndView = ModelAndView("forums")
        modelAndView.addObject("forums", forumRepo.findAll())
        return modelAndView
    }

    @GetMapping
    @RequestMapping("/{id}")
    fun getForum(@PathVariable id:Long) : ModelAndView {
        val modelAndView = ModelAndView("forum")
        modelAndView.addObject("forum", forumRepo.findById(id).getOrElse {
            throw IllegalArgumentException("No forum $id")
        })
        return modelAndView
    }

    @PostMapping
    @RequestMapping("/add_comment")
    fun addComment(
        @RequestParam forumId:Long,
        @RequestParam postId:Long,
        @RequestParam content:String
    ) : String {
        val user = userRepo.getByLogin(SecurityContextHolder.getContext().authentication.name)
        val post = postRepo.findById(postId).get()
        user!!.comments.add(Comment().apply {
            this.user = user
            this.post = post
            this.content = content
        })
        userRepo.save(user)
        return "redirect:/forum/${forumId}"
    }

    @PostMapping
    @RequestMapping("/post")
    fun createPost(
        @RequestParam forumId: Long,
        @RequestParam title: String,
        @RequestParam content: String
    ) : String {
        //println("Cred: ${SecurityContextHolder.getContext().authentication.name}")
        val user = userRepo.getByLogin(SecurityContextHolder.getContext().authentication.name)
        //println("USere: $user")
        user!!.posts.add(Post().apply {
            this.title = title
            this.content = content
            this.user = user
            this.forum = forumRepo.findById(forumId).get()
        })
        userRepo.save(user)
        return "redirect:/forum/${forumId}"
    }

    @PostMapping
    @RequestMapping("/create")
    fun create(@RequestParam name:String, @RequestParam desc:String) : String {
        forumRepo.save(Forum().apply {
            this.name = name
            this.description = desc
        })
        return "redirect:/forums"
    }
}