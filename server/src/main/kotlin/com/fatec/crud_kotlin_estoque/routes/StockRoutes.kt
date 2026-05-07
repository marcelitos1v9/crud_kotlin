package com.fatec.crud_kotlin_estoque.routes

import com.fatec.crud_kotlin_estoque.domain.models.StockItem
import com.fatec.crud_kotlin_estoque.services.StockService
import io.github.jan.supabase.SupabaseClient
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.stockRoutes(supabase: SupabaseClient) {
    val stockService = StockService(supabase)

    route("/stock") {
        get {
            try {
                val stockItems = stockService.getAllStockItems()
                call.respond(HttpStatusCode.OK, stockItems)
            } catch (e: Exception) {
                call.respond(HttpStatusCode.InternalServerError, mapOf("error" to (e.message ?: "Unknown error")))
            }
        }

        get("/summary") {
            try {
                val summaries = stockService.getStockSummary()
                call.respond(HttpStatusCode.OK, summaries)
            } catch (e: Exception) {
                call.respond(HttpStatusCode.InternalServerError, mapOf("error" to (e.message ?: "Unknown error")))
            }
        }

        get("/{id}") {
            val id = call.parameters["id"] ?: return@get call.respond(HttpStatusCode.BadRequest, "Missing ID")
            try {
                val stockItem = stockService.getStockItemById(id)
                if (stockItem != null) {
                    call.respond(HttpStatusCode.OK, stockItem)
                } else {
                    call.respond(HttpStatusCode.NotFound, "Stock item not found")
                }
            } catch (e: Exception) {
                call.respond(HttpStatusCode.InternalServerError, mapOf("error" to (e.message ?: "Unknown error")))
            }
        }

        post {
            try {
                val stockItem = call.receive<StockItem>()
                val createdItem = stockService.createStockItem(stockItem)
                call.respond(HttpStatusCode.Created, createdItem)
            } catch (e: Exception) {
                call.respond(HttpStatusCode.BadRequest, mapOf("error" to (e.message ?: "Invalid payload or error")))
            }
        }

        put("/{id}") {
            val id = call.parameters["id"] ?: return@put call.respond(HttpStatusCode.BadRequest, "Missing ID")
            try {
                val stockItem = call.receive<StockItem>()
                val updatedItem = stockService.updateStockItem(id, stockItem)
                
                if (updatedItem != null) {
                    call.respond(HttpStatusCode.OK, updatedItem)
                } else {
                    call.respond(HttpStatusCode.NotFound, "Stock item not found")
                }
            } catch (e: Exception) {
                call.respond(HttpStatusCode.InternalServerError, mapOf("error" to (e.message ?: "Unknown error")))
            }
        }

        delete("/{id}") {
            val id = call.parameters["id"] ?: return@delete call.respond(HttpStatusCode.BadRequest, "Missing ID")
            try {
                stockService.deleteStockItem(id)
                call.respond(HttpStatusCode.NoContent)
            } catch (e: Exception) {
                call.respond(HttpStatusCode.InternalServerError, mapOf("error" to (e.message ?: "Unknown error")))
            }
        }
    }
}

