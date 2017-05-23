package com.coldstart

import com.coldstart.Model.User
import com.coldstart.Repository.UserRepo
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.CommandLineRunner
import org.springframework.stereotype.Component

/**
 * Created by quangio.
 */

@Component
class Loader @Autowired
constructor(private val userRepo: UserRepo) : CommandLineRunner {
    override fun run(vararg args: String) {
        userRepo.deleteAll()
        val admin = User(username = "admin", password = "admin", roles = mutableListOf("ADMIN", "MOD"))
        userRepo.save(admin)
        userRepo.save(User("quang", "1"))
        userRepo.save(User("test", "123"))
    }
}