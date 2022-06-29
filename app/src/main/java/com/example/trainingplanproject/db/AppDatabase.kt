package com.example.trainingplanproject.db

import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.trainingplanproject.MyApplication

@Database(entities = [SearchHistoryData::class], version = 1)
abstract class AppDatabase : RoomDatabase() {

    abstract fun searchHistoryDao(): SearchHistoryDao

    companion object {
        val instance: AppDatabase by lazy {
            Room.databaseBuilder(
                MyApplication.application,
                AppDatabase::class.java, "AppDatabase.db")
                .build()
        }
    }
}