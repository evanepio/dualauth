package ca.evanepio.autheaxmple.dualauth.controllers

import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

import java.security.Principal

@RestController
@RequestMapping("pie")
class PieController {

    @GetMapping("eat")
    String eat(Principal principal) {
        JwtAuthenticationToken jwtToken = (JwtAuthenticationToken) principal
        def email = jwtToken.getTokenAttributes()['preferred_username']
        def name = jwtToken.getTokenAttributes()['name']
        return "Hello and have some pie, $name! $email"
    }

    @GetMapping("bake")
    String bake(Principal principal) {
        JwtAuthenticationToken jwtToken = (JwtAuthenticationToken) principal
        def email = jwtToken.getTokenAttributes()['preferred_username']
        def name = jwtToken.getTokenAttributes()['name']
        return "Baking some pie, $name! $email"
    }
}
