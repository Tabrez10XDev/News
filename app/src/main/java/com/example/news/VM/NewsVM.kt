package com.example.news.VM

import androidx.lifecycle.ViewModel
import com.example.news.Room.NewsRepository
import com.example.news.Room.article
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class NewsVM(private val repository: NewsRepository): ViewModel() {
    fun upsert(item: article)= CoroutineScope(Dispatchers.Main).launch{
        repository.upsert(item)
    }
    fun getAll()= repository.getAll()
}