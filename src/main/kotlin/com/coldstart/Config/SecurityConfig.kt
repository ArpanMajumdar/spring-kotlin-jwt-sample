package com.coldstart.Config

import com.coldstart.Jwt.JWTAuthenticationFilter
import com.coldstart.Jwt.JWTLoginFilter
import org.springframework.boot.autoconfigure.security.SecurityProperties
import org.springframework.context.annotation.Configuration
import org.springframework.core.annotation.Order
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter

/**
 * Created by quangio.
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity
@Order(SecurityProperties.ACCESS_OVERRIDE_ORDER)
class SecurityConfig : WebSecurityConfigurerAdapter() {
    override fun configure(http: HttpSecurity) {
        http.
                csrf()
                    .disable()
                .antMatcher("/**").authorizeRequests()
                    .antMatchers("/user/*", "/browser/**").permitAll()
                    .anyRequest().authenticated()
                    .antMatchers("/metrics").hasAuthority("ADMIN")
                .and()
                    .addFilterBefore(JWTLoginFilter("/user/login", authenticationManager()),
                        UsernamePasswordAuthenticationFilter::class.java)
                    .addFilterBefore(JWTAuthenticationFilter(), UsernamePasswordAuthenticationFilter::class.java)
                    .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
    }
}
