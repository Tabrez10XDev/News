package com.example.news.Frags

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.View
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.news.MainActivity

import com.example.news.R
import com.example.news.VM.NewsVM
import com.example.news.adapters.NewsAdapter
import com.example.news.util.Resource
import kotlinx.android.synthetic.main.fragment_saved.*


class Saved : Fragment(R.layout.fragment_saved) {
    lateinit var viewModel : NewsVM
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        viewModel = (activity as MainActivity).viewModel

    }


}
