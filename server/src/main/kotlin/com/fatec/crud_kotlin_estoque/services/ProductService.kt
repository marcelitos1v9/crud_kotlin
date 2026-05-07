package com.fatec.crud_kotlin_estoque.services

import com.fatec.crud_kotlin_estoque.domain.models.Product
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.postgrest.postgrest

class ProductService(private val supabase: SupabaseClient) {

    suspend fun getAllProducts(): List<Product> {
        return supabase.postgrest["products"].select().decodeList<Product>()
    }

    suspend fun getProductById(id: String): Product? {
        return supabase.postgrest["products"].select {
            filter { eq("id", id) }
        }.decodeSingleOrNull<Product>()
    }

    suspend fun createProduct(product: Product): Product {
        return supabase.postgrest["products"].insert(product) {
            select()
        }.decodeSingle<Product>()
    }

    suspend fun updateProduct(id: String, product: Product): Product? {
        return supabase.postgrest["products"].update(product) {
            filter { eq("id", id) }
            select()
        }.decodeSingleOrNull<Product>()
    }

    suspend fun deleteProduct(id: String) {
        supabase.postgrest["products"].delete {
            filter { eq("id", id) }
        }
    }
}
