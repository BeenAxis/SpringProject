package org.example.itog.controller

import org.example.itog.model.Tag
import org.example.itog.repositories.TagRepository
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.servlet.ModelAndView

@Controller
@RequestMapping("/tag")
class TagController(
    val tagRepo:TagRepository
) {
    @GetMapping
    fun index() : ModelAndView {
        val modelAndView = ModelAndView("tags")
        modelAndView.addObject("tags", tagRepo.findAll())
        return modelAndView
    }

    @PostMapping
    @RequestMapping("/create")
    fun createRole(@RequestParam name:String) : String {
        tagRepo.save(Tag().apply { this.name = name })
        return "redirect:/tag"
    }

    @PostMapping
    @RequestMapping("/change", params = ["delete"])
    fun deleteRole(@RequestParam roleId:Long) : String {
        tagRepo.deleteById(roleId)
        return "redirect:/tag"
    }
    @PostMapping
    @RequestMapping("/change", params = ["edit"])
    fun updateRole(@RequestParam roleId:Long, @RequestParam name:String) : String {
        //roleRepo.deleteById(roleId)
        tagRepo.findById(roleId).get().let {
            it.name = name
            tagRepo.save(it)
        }
        return "redirect:/tag"
    }
}