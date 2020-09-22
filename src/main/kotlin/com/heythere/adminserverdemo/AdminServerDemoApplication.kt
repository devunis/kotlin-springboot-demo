package com.heythere.adminserverdemo

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.data.jpa.repository.config.EnableJpaAuditing

@EnableJpaAuditing
@SpringBootApplication
class AdminServerDemoApplication

fun main(args: Array<String>) {
    runApplication<AdminServerDemoApplication>(*args)
}
