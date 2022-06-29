package com.example.trainingplanproject.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "search_history")
data class SearchHistoryData(
    @PrimaryKey(autoGenerate = true) val id: Int? = null,
    @ColumnInfo(name = "query") val query: String
)
