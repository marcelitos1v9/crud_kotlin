package com.fatec.crud_kotlin_estoque.routes

import com.fatec.crud_kotlin_estoque.domain.models.Product
import com.fatec.crud_kotlin_estoque.services.ProductService
import io.github.jan.supabase.SupabaseClient
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.productRoutes(supabase: SupabaseClient) {
    val productService = ProductService(supabase)

    route("/products") {
        get {
            try {
                val products = productService.getAllProducts()
                call.respond(HttpStatusCode.OK, products)
            } catch (e: Exception) {
                call.respond(HttpStatusCode.InternalServerError, mapOf("error" to (e.message ?: "Unknown error")))
            }
        }

        get("/{id}") {
            val id = call.parameters["id"] ?: return@get call.respond(HttpStatusCode.BadRequest, "Missing ID")
            try {
                val product = productService.getProductById(id)
                if (product != null) {
                    call.respond(HttpStatusCode.OK, product)
                } else {
                    call.respond(HttpStatusCode.NotFound, "Product not found")
                }
            } catch (e: Exception) {
                call.respond(HttpStatusCode.InternalServerError, mapOf("error" to (e.message ?: "Unknown error")))
            }
        }

        post {
            try {
                val product = call.receive<Product>()
                val createdProduct = productService.createProduct(product)
                call.respond(HttpStatusCode.Created, createdProduct)
            } catch (e: Exception) {
                call.respond(HttpStatusCode.BadRequest, mapOf("error" to (e.message ?: "Invalid payload or error")))
            }
        }

        put("/{id}") {
            val id = call.parameters["id"] ?: return@put call.respond(HttpStatusCode.BadRequest, "Missing ID")
            try {
                val product = call.receive<Product>()
                val updatedProduct = productService.updateProduct(id, product)
                
                if (updatedProduct != null) {
                    call.respond(HttpStatusCode.OK, updatedProduct)
                } else {
                    call.respond(HttpStatusCode.NotFound, "Product not found")
                }
            } catch (e: Exception) {
                call.respond(HttpStatusCode.InternalServerError, mapOf("error" to (e.message ?: "Unknown error")))
            }
        }

        delete("/{id}") {
            val id = call.parameters["id"] ?: return@delete call.respond(HttpStatusCode.BadRequest, "Missing ID")
            try {
                productService.deleteProduct(id)
                call.respond(HttpStatusCode.NoContent)
            } catch (e: Exception) {
                call.respond(HttpStatusCode.InternalServerError, mapOf("error" to (e.message ?: "Unknown error")))
            }
        }
    }
}

