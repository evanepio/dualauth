package ca.evanepio.autheaxmple.dualauth.configs

import org.springframework.http.HttpMethod
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.oauth2.server.resource.authentication.JwtIssuerAuthenticationManagerResolver

@EnableWebSecurity
class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        def authenticationManagerResolver = new JwtIssuerAuthenticationManagerResolver(
                'http://localhost:8080/auth/realms/one',
                'http://localhost:8080/auth/realms/two'
        )

        // @formatter:off
        http
                .cors( cors ->
                    cors.disable()
                )
                .sessionManagement( sessionManagement ->
                    sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )
                .authorizeRequests((authorizeRequests) ->
                        authorizeRequests
                                .antMatchers(HttpMethod.GET, "/pie/eat/**", "/cake/eat/**").authenticated()
                                .antMatchers(HttpMethod.GET, "/pie/bake/**", "/cake/bake/**").hasRole('admin')
                                .anyRequest().permitAll()
                )
                .oauth2ResourceServer(oauth2ResourceServer ->
                    oauth2ResourceServer.authenticationManagerResolver(authenticationManagerResolver)
                )
        // @formatter:on
    }
}
