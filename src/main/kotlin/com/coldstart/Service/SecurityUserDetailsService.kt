package com.coldstart.Service

import com.coldstart.Model.User
import com.coldstart.Repository.UserRepo
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service

/**
 * Created by quangio.
 */

@Service
class SecurityUserDetailsService @Autowired
constructor(private val userRepo: UserRepo) : UserDetailsService {
    override fun loadUserByUsername(s: String): UserDetails {
        val u: User = userRepo.findByUsername(s) ?: throw(UsernameNotFoundException("Username not found"))
        return SecurityUserDetails(u)
    }
}