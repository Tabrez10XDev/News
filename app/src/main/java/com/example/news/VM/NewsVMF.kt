package com.example.news.VM

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.news.Room.NewsRepository

@Suppress("UNCHECKED_CAST")
class NewsVMF(private val repository: NewsRepository): ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return NewsVM(repository) as T
    }
}