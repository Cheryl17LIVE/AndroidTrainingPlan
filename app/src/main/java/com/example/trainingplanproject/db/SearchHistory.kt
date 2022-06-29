package com.example.trainingplanproject.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "search_history")
data class SearchHistory(
    @PrimaryKey val id : Int,
    @ColumnInfo(name = "search_word") val searchWord: String
    )
