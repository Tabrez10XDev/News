package com.example.news.Room

import com.example.news.api.RetrofitInstance

class NewsRepository(
    val db : ArticleDB
) {
    suspend fun getBreakingNews(countryCode : String, pageNumber : Int)=
        RetrofitInstance.api.getBreakingNews(countryCode, pageNumber )
}