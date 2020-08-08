package com.example.news.Frags

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.news.MainActivity

import com.example.news.R
import com.example.news.VM.NewsVM


class ArticleFragment : Fragment(R.layout.fragment_article) {
    lateinit var viewModel : NewsVM
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        viewModel = (activity as MainActivity).viewModel
    }
}
