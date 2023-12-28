package org.example.itog.controller

import org.example.itog.model.Category
import org.example.itog.model.UserRole
import org.example.itog.repositories.CategoryRepository
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.servlet.ModelAndView

@Controller
@RequestMapping("/cat")
class CategoryController(val catRepo:CategoryRepository) {

    @GetMapping
    fun index() : ModelAndView {
        val modelAndView = ModelAndView("category")
        modelAndView.addObject("cats", catRepo.findAll())
        return modelAndView
    }

    @PostMapping
    @RequestMapping("/create")
    fun createRole(@RequestParam name:String) : String {
        catRepo.save(Category().apply { this.name = name })
        return "redirect:/cat"
    }

    @PostMapping
    @RequestMapping("/change", params = ["delete"])
    fun deleteRole(@RequestParam roleId:Long) : String {
        catRepo.deleteById(roleId)
        return "redirect:/cat"
    }
    @PostMapping
    @RequestMapping("/change", params = ["edit"])
    fun updateRole(@RequestParam roleId:Long, @RequestParam name:String) : String {
        //roleRepo.deleteById(roleId)
        catRepo.findById(roleId).get().let {
            it.name = name
            catRepo.save(it)
        }
        return "redirect:/cat"
    }
}