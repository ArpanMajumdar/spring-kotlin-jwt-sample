package com.coldstart.Config

import com.coldstart.Jwt.JWTAuthenticationFilter
import com.coldstart.Jwt.JWTLoginFilter
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
import org.springframework.security.web.session.SessionManagementFilter


/**
 * Created by quangio.
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity
//@Order(SecurityProperties.DEFAULT_FILTER_ORDER)
class SecurityConfig : WebSecurityConfigurerAdapter() {
    override fun configure(http: HttpSecurity) {
        //http.cors().disable()
        http.
                csrf()
                    .disable()
                .antMatcher("/**").authorizeRequests()
                    .antMatchers("/user/**", "/browser/**", "/public/**").permitAll()
                    .anyRequest().authenticated()
                    .antMatchers("/metrics").hasAuthority("ADMIN")
                .and()
                .addFilterBefore(CorsFilter(), SessionManagementFilter::class.java)
                    .addFilterBefore(JWTLoginFilter("/user/login", authenticationManager()),
                        UsernamePasswordAuthenticationFilter::class.java)
                    .addFilterBefore(JWTAuthenticationFilter(), UsernamePasswordAuthenticationFilter::class.java)
                    .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
    }
}
