package org.example.itog.controller

import org.example.itog.model.Message
import org.example.itog.repositories.MessageRepository
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.servlet.ModelAndView

@Controller
@RequestMapping("message")
class MessageController(
    val msgRepo:MessageRepository
) {

    @GetMapping
    fun index() : ModelAndView {
        val mav = ModelAndView("message")
        mav.addObject("messages", msgRepo.findAll())
        return mav
    }

    @PostMapping
    @RequestMapping("/send")
    fun sendMsg(msg:String) : String {
        val msg = Message().apply {
            this.content = msg
        }
        msgRepo.save(msg)
        return "redirect:/message"
    }
}