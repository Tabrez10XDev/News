package com.example.news

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders

import kotlinx.android.synthetic.main.activity_main.*
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import com.example.news.Room.NewsRepository
import com.example.news.Room.article
import com.example.news.Room.articleDB
import com.example.news.VM.NewsVM
import com.example.news.VM.NewsVMF
import kotlinx.android.synthetic.main.fragment_breaking.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        bottomNavigationView.setupWithNavController(fragment.findNavController())
    }
}
