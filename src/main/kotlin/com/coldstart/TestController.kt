package com.coldstart

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

import java.security.Principal

/**
 * Created by quangio.
 */

@RestController
class TestController {
    @GetMapping("/user/")
    fun test(principal: Principal?) = principal?.name ?: "You are not logged in"
}
