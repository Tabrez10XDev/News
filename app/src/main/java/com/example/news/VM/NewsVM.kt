package com.example.news.VM

import androidx.lifecycle.ViewModel
import com.example.news.Room.NewsRepository

class NewsVM(
    val newsRepository: NewsRepository
) : ViewModel() {
}