package ca.evanepio.autheaxmple.dualauth.controllers

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("ouch")
class InsecureController {

    @GetMapping("greeting")
    String greeting(@RequestParam name) {
        return "Hello, $name!"
    }
}
