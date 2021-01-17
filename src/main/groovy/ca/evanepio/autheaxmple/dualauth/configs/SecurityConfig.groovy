package ca.evanepio.autheaxmple.dualauth.configs

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.annotation.Order
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.oauth2.server.resource.authentication.JwtIssuerAuthenticationManagerResolver

@EnableWebSecurity
class SecurityConfig {

    @Configuration
    @Order(1)
    static class Oauth2SecurityConfig extends WebSecurityConfigurerAdapter {
        @Override
        protected void configure(HttpSecurity http) throws Exception {
            def authenticationManagerResolver = new JwtIssuerAuthenticationManagerResolver(
                    'http://localhost:8080/auth/realms/one',
                    'http://localhost:8080/auth/realms/two'
            )

            http.antMatcher("/pie/**")
                    .cors( cors ->
                            cors.disable()
                    )
                    .sessionManagement( sessionManagement ->
                            sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                    )
                    .authorizeRequests((authorizeRequests) ->
                            authorizeRequests
                                    .antMatchers("/pie/eat/**").authenticated()
                                    .antMatchers( "/pie/bake/**").hasRole('admin')
                                    .anyRequest().permitAll()
                    )
                    .oauth2ResourceServer(oauth2ResourceServer ->
                            oauth2ResourceServer.authenticationManagerResolver(authenticationManagerResolver)
                    )
        }
    }

    @Configuration
    static class InMemorySecurityConfig extends WebSecurityConfigurerAdapter {

        @Bean
        PasswordEncoder passwordEncoder() {
            return new BCryptPasswordEncoder();
        }

        @Override
        protected void configure(HttpSecurity http) throws Exception {
            http
                .antMatcher("/cake/**")
                    .httpBasic().and()
                    .csrf()
                    .disable()
                    .sessionManagement()
                    .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                    .and()
                    .authorizeRequests()
                    .anyRequest()
                    .authenticated()
        }

        @Override
        protected void configure(AuthenticationManagerBuilder auth) throws Exception {
            auth.inMemoryAuthentication()
                    .withUser("user")
                    .password(passwordEncoder().encode("password"))
                    .roles("USER")
        }
    }
}
