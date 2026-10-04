package com.example.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface BanglaDao {
    @Query("SELECT * FROM saved_words")
    fun getAllSavedWords(): Flow<List<SavedWordEntity>>

    @Query("SELECT * FROM saved_words WHERE wordId = :id LIMIT 1")
    suspend fun getSavedWordById(id: String): SavedWordEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsertSavedWord(entity: SavedWordEntity)

    @Query("DELETE FROM saved_words WHERE wordId = :id")
    suspend fun deleteSavedWord(id: String)

    @Query("SELECT * FROM quiz_scores ORDER BY timestamp DESC")
    fun getAllQuizScores(): Flow<List<QuizScoreEntity>>

    @Insert
    suspend fun insertQuizScore(score: QuizScoreEntity)

    @Query("SELECT COUNT(*) FROM saved_words WHERE isLearned = 1")
    fun getLearnedCount(): Flow<Int>

    @Query("SELECT COUNT(*) FROM saved_words WHERE isFavorite = 1")
    fun getFavoriteCount(): Flow<Int>
}
