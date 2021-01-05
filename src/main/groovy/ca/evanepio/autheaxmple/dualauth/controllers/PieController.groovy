package ca.evanepio.autheaxmple.dualauth.controllers

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

import java.security.Principal

@RestController
@RequestMapping("pie")
class PieController {

    @GetMapping("greeting")
    String greeting(Principal principal) {
        return "Hello and have some pie, Rando Calrissian!"
    }
}
