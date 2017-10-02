package com.coldstart.Jwt

import com.coldstart.Model.User
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import org.apache.log4j.Logger
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import java.util.*
import java.util.concurrent.TimeUnit
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse
import kotlin.collections.HashMap

/**
 * Created by quangio.
 */

internal object JWTUtils {
    private val expiration: Long = 100L
    private val secret = "Hail Hitler"
    private val header = "Authorization"

    private val logger = Logger.getLogger(JWTUtils::class.java)

    fun User.createJwt(): String {
        val claims = HashMap<String, Any>()
        claims.put("roles", this.roles)
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(this.username)
                .setExpiration(Date(Date().time + TimeUnit.HOURS.toMillis(expiration)))
                .signWith(SignatureAlgorithm.HS256, secret).compact()
    }

    fun addAuthentication(response: HttpServletResponse, user: User) {
        val jwt = user.createJwt()
        response.setHeader("Access-Control-Allow-Origin", "http://localhost:3000")
        response.writer.write(jwt)
        response.writer.flush()
        response.writer.close()
    }

    fun getAuthentication(request: HttpServletRequest): Authentication? {
        val token = request.getHeader(header) ?: return null

        val tokenBody = Jwts.parser()
                .setSigningKey(secret)
                .parseClaimsJws(token)
                .body

        val username: String = tokenBody.subject
        @Suppress("UNCHECKED_CAST")
        val roles = tokenBody["roles"] as List<String>

        val res = roles.mapTo(LinkedList<GrantedAuthority>()) { SimpleGrantedAuthority(it) }

        logger.info(username + " logged in with authorities " + res)
        return UsernamePasswordAuthenticationToken(username, null, res)
    }
}