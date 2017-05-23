package com.coldstart

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication

@SpringBootApplication
class TodolistApplication

fun main(args: Array<String>) {
    SpringApplication.run(TodolistApplication::class.java, *args)
}
