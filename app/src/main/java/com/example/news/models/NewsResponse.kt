package com.example.news.models

import com.example.news.Room.Article

data class NewsResponse(
    val articles: MutableList<Article>,
    val status: String,
    val totalResults: Int
)