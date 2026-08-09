package com.yourname.tasbehapp.domain.model

data class Tasbeh(
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
