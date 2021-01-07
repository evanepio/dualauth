package ca.evanepio.autheaxmple.dualauth.configs

import ca.evanepio.autheaxmple.dualauth.KeycloakRealmRoleConverter
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.core.convert.converter.Converter
import org.springframework.http.HttpMethod
import org.springframework.security.authentication.AbstractAuthenticationToken
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.oauth2.jwt.Jwt
import org.springframework.security.oauth2.jwt.JwtDecoder
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter

@EnableWebSecurity
class SecurityConfig extends WebSecurityConfigurerAdapter {


    @Value("\${spring.security.oauth2.resourceserver.jwt.jwk-set-uri}") String jwkSetUri

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // @formatter:off
        http
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.NEVER).and()
                .authorizeRequests((authorizeRequests) ->
                        authorizeRequests
                                .antMatchers(HttpMethod.GET, "/pie/eat/**", "/cake/eat/**").hasRole('user')
                                .antMatchers(HttpMethod.GET, "/pie/bake/**", "/cake/bake/**").hasRole('admin')
                                .anyRequest().permitAll()
                )
                .oauth2ResourceServer().jwt().jwtAuthenticationConverter(jwtAuthenticationConverter())
        // @formatter:on
    }

    @Bean
    JwtDecoder jwtDecoder() {
        return NimbusJwtDecoder.withJwkSetUri(this.jwkSetUri).build()
    }

    private Converter<Jwt, ? extends AbstractAuthenticationToken> jwtAuthenticationConverter() {
        JwtAuthenticationConverter jwtConverter = new JwtAuthenticationConverter()
        jwtConverter.setJwtGrantedAuthoritiesConverter(new KeycloakRealmRoleConverter())
        return jwtConverter
    }
}
