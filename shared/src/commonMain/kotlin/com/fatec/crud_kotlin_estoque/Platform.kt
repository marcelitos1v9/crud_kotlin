package com.fatec.crud_kotlin_estoque

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform
