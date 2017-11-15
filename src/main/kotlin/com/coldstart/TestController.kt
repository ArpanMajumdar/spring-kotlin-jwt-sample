package com.coldstart

import com.coldstart.Model.User
import com.coldstart.Repository.UserRepo
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController
import java.security.Principal

/**
 * Created by quangio.
 */

@RestController
class TestController @Autowired
constructor(private val userRepo: UserRepo) {
    @GetMapping("/user/")
    fun test(principal: Principal?) = principal?.name ?: "You are not logged in"

    @PostMapping("/user/register")
    fun register(@RequestBody user: User) : ResponseEntity<*> { // You should hash users' passwords
        if (userRepo.findByUsername(user.username) != null) {
            return ResponseEntity("user existed", HttpStatus.CONFLICT)
        }
        userRepo.insert(User(username = user.username, password = user.password))
        return ResponseEntity.ok("created")
    }
}
