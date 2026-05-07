package com.fatec.crud_kotlin_estoque.services

import com.fatec.crud_kotlin_estoque.domain.models.StockItem
import com.fatec.crud_kotlin_estoque.domain.models.StockSummary
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.postgrest.postgrest

class StockService(private val supabase: SupabaseClient) {

    suspend fun getAllStockItems(): List<StockItem> {
        return supabase.postgrest["stock_items"].select().decodeList<StockItem>()
    }

    suspend fun getStockSummary(): List<StockSummary> {
        return supabase.postgrest["stock_summary"].select().decodeList<StockSummary>()
    }

    suspend fun getStockItemById(id: String): StockItem? {
        return supabase.postgrest["stock_items"].select {
            filter { eq("id", id) }
        }.decodeSingleOrNull<StockItem>()
    }

    suspend fun createStockItem(stockItem: StockItem): StockItem {
        return supabase.postgrest["stock_items"].insert(stockItem) {
            select()
        }.decodeSingle<StockItem>()
    }

    suspend fun updateStockItem(id: String, stockItem: StockItem): StockItem? {
        return supabase.postgrest["stock_items"].update(stockItem) {
            filter { eq("id", id) }
            select()
        }.decodeSingleOrNull<StockItem>()
    }

    suspend fun deleteStockItem(id: String) {
        supabase.postgrest["stock_items"].delete {
            filter { eq("id", id) }
        }
    }
}
