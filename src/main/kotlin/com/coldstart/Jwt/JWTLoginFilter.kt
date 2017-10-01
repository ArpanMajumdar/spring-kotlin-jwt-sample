package com.coldstart.Jwt

import com.coldstart.Service.SecurityUserDetails
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.AuthenticationException
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter
import org.springframework.security.web.util.matcher.AntPathRequestMatcher
import java.io.IOException
import javax.servlet.FilterChain
import javax.servlet.ServletException
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

/**
 * Created by quangio.
 */
class JWTLoginFilter internal constructor(s: String, authenticationManager: AuthenticationManager) : AbstractAuthenticationProcessingFilter(AntPathRequestMatcher(s)) {
    init {
        setAuthenticationManager(authenticationManager)
    }

    @Throws(AuthenticationException::class, IOException::class, ServletException::class)
    override fun attemptAuthentication(
            req: HttpServletRequest, res: HttpServletResponse): Authentication {
        val cred: AccountCredentials? = jacksonObjectMapper().readValue(req.inputStream)

        return authenticationManager.authenticate(
                UsernamePasswordAuthenticationToken(
                        cred?.username,
                        cred?.password,
                        emptyList<GrantedAuthority>()
                )
        )
    }

    @Throws(IOException::class, ServletException::class)
    override fun successfulAuthentication(req: HttpServletRequest,
                                          res: HttpServletResponse, chain: FilterChain?, auth: Authentication) {
        JWTUtils.addAuthentication(res, (auth.principal as SecurityUserDetails).user)
    }
}
