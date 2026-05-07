package com.fatec.crud_kotlin_estoque.plugins

import com.fatec.crud_kotlin_estoque.routes.productRoutes
import com.fatec.crud_kotlin_estoque.routes.stockRoutes
import io.github.jan.supabase.SupabaseClient
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Application.configureRouting(supabase: SupabaseClient?) {
    routing {
        get("/") {
            call.respondText("MergeSkills API is running!")
        }

        get("/api/health") {
            call.respondText("""{"status":"ok"}""", io.ktor.http.ContentType.Application.Json)
        }

        if (supabase != null) {
            productRoutes(supabase)
            stockRoutes(supabase)
        }
    }
}

