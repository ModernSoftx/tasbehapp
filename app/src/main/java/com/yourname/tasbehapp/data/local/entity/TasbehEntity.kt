package com.yourname.tasbehapp.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.yourname.tasbehapp.domain.model.Tasbeh

@Entity(tableName = "tasbeh_table")
data class TasbehEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val name: String,
    val arabicText: String? = null,
    val currentCount: Int = 0,
    val targetCount: Int = 33,
    val totalLaps: Int = 0,
    val category: String = "Custom",
    val description: String? = null,
    val lastUsed: Long = System.currentTimeMillis(),
    val createdAt: Long = System.currentTimeMillis(),
    val isActive: Boolean = false,
    val isFavourite: Boolean = false
)

// Extension functions for conversion
fun TasbehEntity.toDomain(): Tasbeh {
    return Tasbeh(
        id = id,
        name = name,
        arabicText = arabicText,
        currentCount = currentCount,
        targetCount = targetCount,
        totalLaps = totalLaps,
        category = category,
        description = description,
        lastUsed = lastUsed,
        createdAt = createdAt,
        isActive = isActive,
        isFavourite = isFavourite
    )
}

fun Tasbeh.toEntity(): TasbehEntity {
    return TasbehEntity(
        id = id,
        name = name,
        arabicText = arabicText,
        currentCount = currentCount,
        targetCount = targetCount,
        totalLaps = totalLaps,
        category = category,
        description = description,
        lastUsed = lastUsed,
        createdAt = createdAt,
        isActive = isActive,
        isFavourite = isFavourite
    )
}
