package com.example.news.Room

class NewsRepository(private val db:articleDB) {
    suspend fun upsert(item:article)=db.getArticle().upsert(item)

    fun getAll()=db.getArticle().getAll()
}