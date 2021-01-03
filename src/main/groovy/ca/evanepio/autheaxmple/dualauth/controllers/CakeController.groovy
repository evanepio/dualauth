package ca.evanepio.autheaxmple.dualauth.controllers

import org.keycloak.KeycloakPrincipal
import org.keycloak.KeycloakSecurityContext
import org.keycloak.representations.AccessToken
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

import java.security.Principal

@RestController
@RequestMapping("cake")
class CakeController {

    @GetMapping("greeting")
    String greeting(Principal principal) {
        KeycloakPrincipal<KeycloakSecurityContext> kp = (KeycloakPrincipal<KeycloakSecurityContext>) principal
        AccessToken token = kp.getKeycloakSecurityContext().getToken()

        String email = token.getEmail();

        return "Have some cake, ${token.getName()}! $email"
    }
}
