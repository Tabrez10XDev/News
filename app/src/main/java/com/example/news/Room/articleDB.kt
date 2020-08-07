package com.example.news.Room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(
    entities = [article::class],
    version = 1
)
abstract class articleDB : RoomDatabase() {
abstract fun getArticle():articleDAO
    companion object{
        private var instance : articleDB?= null
        val lock = Any()
        operator fun invoke(context: Context)= instance?: synchronized(lock){
            instance?: createDB(context).also { instance= it




            }
        }
        private fun createDB(context : Context)= Room.databaseBuilder(context.applicationContext,articleDB::class.java,"article.db").build()
    }
}