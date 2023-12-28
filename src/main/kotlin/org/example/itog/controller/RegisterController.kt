package org.example.itog.controller

import org.example.itog.model.User
import org.example.itog.repositories.RoleRepository
import org.example.itog.repositories.UserRepository
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam

@Controller
@RequestMapping("register")
class RegisterController(val repo: UserRepository,
                         val hasher: PasswordEncoder = BCryptPasswordEncoder(),
    val roleRepo:RoleRepository
) {

    @GetMapping
    fun index() : String {
        return "register"
    }

    @PostMapping
    fun register(@RequestParam login:String, @RequestParam password:String) : String {
        val neww = User()
        neww.login = login
        neww.role = roleRepo.findByName("User")
        neww.pass = hasher.encode(password)
        repo.save(neww)
        return "redirect:login"
    }
}