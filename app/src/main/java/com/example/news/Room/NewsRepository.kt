package com.example.news.Room

import com.example.news.api.RetrofitInstance

class NewsRepository(
    val db : ArticleDB
) {
    suspend fun upsert(article: Article) = db.getArticleDao().upsert(article)

    fun getSavedNews() = db.getArticleDao().getAllArticles()

    suspend fun deleteArticle(article: Article) = db.getArticleDao().deleteArticles(article)

    suspend fun getBreakingNews(countryCode : String, pageNumber : Int)=
        RetrofitInstance.api.getBreakingNews(countryCode, pageNumber )

    suspend fun searchForNews(searchQuery : String , pageNumber: Int)=
        RetrofitInstance.api.searchForNews(searchQuery,pageNumber)
}