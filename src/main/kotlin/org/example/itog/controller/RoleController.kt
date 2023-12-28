package org.example.itog.controller

import org.example.itog.model.UserRole
import org.example.itog.repositories.RoleRepository
import org.example.itog.repositories.UserRepository
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.servlet.ModelAndView

@Controller
@RequestMapping("role")
class RoleController(
    val userRepository: UserRepository,
    val roleRepo:RoleRepository

) {

    @GetMapping
    fun index() : ModelAndView {
        val users = userRepository.findAll()
        val modelAndView = ModelAndView("role-edit")
        modelAndView.addObject("users", users)
        modelAndView.addObject("roles", roleRepo.findAll())
        return modelAndView
    }

    @PostMapping
    @RequestMapping("/create")
    fun createRole(@RequestParam name:String) : String {
        roleRepo.save(UserRole().apply { this.name = name })
        return "redirect:/role"
    }

    @PostMapping
    @RequestMapping("/change", params = ["delete"])
    fun deleteRole(@RequestParam roleId:Long) : String {
        roleRepo.deleteById(roleId)
        return "redirect:/role"
    }

    @PostMapping
    @RequestMapping("/change", params = ["edit"])
    fun updateRole(@RequestParam roleId:Long, @RequestParam name:String) : String {
        //roleRepo.deleteById(roleId)
        roleRepo.findById(roleId).get().let {
            it.name = name
            roleRepo.save(it)
        }
        return "redirect:/role"
    }


    @PostMapping
    @RequestMapping("/change-user-role")
    fun changeUserRole(
        @RequestParam("userId") userId: Long,
        @RequestParam("roleId") roleId: Long?
    ): String {
        val user = userRepository.findById(userId).orElse(null)
        //val role = roleRepository.findById(roleId).orElse(null)

        if (user != null && roleId != null) {
            user.role = roleRepo.findById(roleId).get()
            userRepository.save(user)
        }

        return "redirect:/role-edit"
    }
}