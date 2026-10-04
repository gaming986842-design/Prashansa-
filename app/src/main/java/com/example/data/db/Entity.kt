package com.example.data.db

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "saved_words")
data class SavedWordEntity(
    @PrimaryKey val wordId: String,
    val isFavorite: Boolean = false,
    val isLearned: Boolean = false,
    val note: String = "",
    val updatedAt: Long = System.currentTimeMillis()
)

@Entity(tableName = "quiz_scores")
data class QuizScoreEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val score: Int,
    val totalQuestions: Int,
    val percentage: Int,
    val timestamp: Long = System.currentTimeMillis()
)
