package com.example.pudge

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class PudgeApplication

fun main(args: Array<String>) {
    runApplication<PudgeApplication>(*args)
}
