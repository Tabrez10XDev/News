package com.example.news.Room

class NewsRepository(private val db:articleDB) {
    suspend fun upsert(item:article)=db.upsertArticle().upsert(item)

    fun getAll(item:article)=db.getArticle().getAll()
}