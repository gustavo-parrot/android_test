package io.parrotsoftware.qatest.data.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class CategoryEntity(
    @PrimaryKey
    @ColumnInfo(name = "category_id")
    val id: String,
    @ColumnInfo(name = "category_name")
    val name: String,
    val position: Int
)
