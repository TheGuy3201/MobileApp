package com.example.productapp.data
import androidx.room.PrimaryKey
import androidx.room.Entity

// Represents a product entity in the Room database
@Entity(tableName = "products")
data class Product(
    @PrimaryKey val id: Int,
    val name: String,
    val price: Double,
    val quantity: Int,
    val deliveryDate: String,
    val category: String,
    val isFavorite: Boolean
)