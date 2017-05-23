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

    fun createJwt(user: User): String {
        val claims = HashMap<String, Any>()
        claims.put("roles", user.roles)
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(user.username)
                .setExpiration(Date(Date().time + TimeUnit.HOURS.toMillis(expiration)))
                .signWith(SignatureAlgorithm.HS256, secret).compact()
    }

    fun addAuthentication(response: HttpServletResponse, user: User) {
        val jwt = createJwt(user)
        response.writer.write(jwt)
        response.writer.flush()
        response.writer.close()
    }

    fun getAuthentication(request: HttpServletRequest): Authentication? {
        val token = request.getHeader(header) ?: return null
        val username = Jwts.parser()
                .setSigningKey(secret)
                .parseClaimsJws(token)
                .body
                .subject
        @Suppress("UNCHECKED_CAST")
        val roles = Jwts.parser()
                .setSigningKey(secret)
                .parseClaimsJws(token)
                .body["roles"] as List<String>

        val res = roles.mapTo(LinkedList<GrantedAuthority>()) { SimpleGrantedAuthority(it) }

        logger.info(res)

        return if (username == null)
            null
        else
            UsernamePasswordAuthenticationToken(username, null, res)
    }
}