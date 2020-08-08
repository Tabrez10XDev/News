package com.example.news

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders

import kotlinx.android.synthetic.main.activity_main.*
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import com.example.news.Room.ArticleDB
import com.example.news.Room.NewsRepository
import com.example.news.VM.NewsVM
import com.example.news.VM.NewsVMProviderFactory
import kotlinx.android.synthetic.main.fragment_breaking.*

class MainActivity : AppCompatActivity() {

    lateinit var viewModel: NewsVM
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        val repository = NewsRepository(ArticleDB(this))
        val viewModelProviderFactory = NewsVMProviderFactory(repository)
        viewModel = ViewModelProvider(this,viewModelProviderFactory).get(NewsVM::class.java)
        bottomNavigationView.setupWithNavController(fragment.findNavController())
    }
}
