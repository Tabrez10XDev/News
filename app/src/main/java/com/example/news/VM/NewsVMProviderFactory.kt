package com.example.news.VM

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.news.Room.NewsRepository

class NewsVMProviderFactory(val app : Application,
                            val newsRepository: NewsRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return NewsVM(app, newsRepository) as T
    }


}