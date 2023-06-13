package io.parrotsoftware.qa_data.bd.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class CategoryEntity(
    @ColumnInfo(name = "category_id") @PrimaryKey val id: String,
    @ColumnInfo(name = "category_name") val name: String,
    val position: Int
)
