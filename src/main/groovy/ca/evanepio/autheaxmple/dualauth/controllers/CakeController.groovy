package ca.evanepio.autheaxmple.dualauth.controllers

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

import java.security.Principal

@RestController
@RequestMapping("cake")
class CakeController {

    @GetMapping("eat")
    String eat(Principal principal) {
        def name = principal.getName()
        return "Hello and have some cake, $name!"
    }

    @GetMapping("bake")
    String bake(Principal principal) {
        def name = principal.getName()
        return "Baking some cake, $name!"
    }
}
