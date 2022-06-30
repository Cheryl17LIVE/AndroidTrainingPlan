package com.example.trainingplanproject.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface SearchHistoryDao {

    @Query("SELECT * FROM search_history")
    fun getAll(): List<SearchHistoryData>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(word: SearchHistoryData): Long

    @Delete
    fun delete(word: SearchHistoryData)

}