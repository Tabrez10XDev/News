package com.example.news.Room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(
    entities = [Article::class],
    version = 1
)
@TypeConverters(Converters::class)
abstract class ArticleDB : RoomDatabase() {

    abstract fun getArticleDao() : ArticleDao

    companion object{
        @Volatile
        private var instance: ArticleDB ?= null

        private val LOCK = Any()

        operator fun invoke(context: Context) = instance ?: synchronized(LOCK){
            instance?: createDB(context).also{
                instance=it
            }
        }

        private fun createDB(context: Context)=
            Room.databaseBuilder(
                context.applicationContext,
                ArticleDB::class.java,
            "article_db.db")
                .build()
    }
}