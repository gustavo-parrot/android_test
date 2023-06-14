package io.parrotsoftware.qa_data.bd.entities

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class ProductEntity(
    @PrimaryKey val id: String,
    val name: String,
    val description: String,
    val image: String,
    val price: Float,
    val isAvailable: Boolean,
    @Embedded val category: CategoryEntity,
)
