package com.coldstart.Service

import com.coldstart.Model.User
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.stereotype.Component
import java.util.*

/**
 * Created by quangio.
 */
@Component
class SecurityUserDetails() : UserDetails {
    lateinit var user: User

    constructor(user: User) : this() {
        @Suppress("LeakingThis")
        this.user = user
    }

    override fun getAuthorities(): MutableCollection<out GrantedAuthority>
            = user.roles.mapTo(LinkedList<GrantedAuthority>()) { SimpleGrantedAuthority(it) }

    override fun getPassword(): String = user.password

    override fun getUsername(): String = user.username

    override fun isAccountNonExpired(): Boolean = true

    override fun isAccountNonLocked(): Boolean = true

    override fun isCredentialsNonExpired(): Boolean = true

    override fun isEnabled(): Boolean = true
}
