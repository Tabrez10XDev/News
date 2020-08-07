package com.example.news.models

import com.example.news.Room.Article

data class NewsResponse(
    val articles: List<Article>,
    val status: String,
    val totalResults: Int
)