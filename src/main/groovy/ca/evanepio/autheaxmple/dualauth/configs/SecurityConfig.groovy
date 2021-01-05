package ca.evanepio.autheaxmple.dualauth.configs

import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.http.HttpMethod
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.oauth2.jwt.JwtDecoder
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder

@EnableWebSecurity
class SecurityConfig extends WebSecurityConfigurerAdapter {


    @Value("\${spring.security.oauth2.resourceserver.jwt.jwk-set-uri}") String jwkSetUri

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // @formatter:off
        http
                .authorizeRequests((authorizeRequests) ->
                        authorizeRequests
                                .antMatchers(HttpMethod.GET, "/pie/**").authenticated()
                                .antMatchers(HttpMethod.POST, "/cake/**").authenticated()
                                .anyRequest().permitAll()
                )
                .oauth2ResourceServer().jwt()
        // @formatter:on
    }

    @Bean
    JwtDecoder jwtDecoder() {
        return NimbusJwtDecoder.withJwkSetUri(this.jwkSetUri).build();
    }
}
