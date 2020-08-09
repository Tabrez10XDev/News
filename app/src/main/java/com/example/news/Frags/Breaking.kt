package com.example.news.Frags

import android.os.Bundle
import android.util.Log.d
import androidx.fragment.app.Fragment
import android.view.View
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.news.MainActivity

import com.example.news.R
import com.example.news.VM.NewsVM
import com.example.news.adapters.NewsAdapter
import com.example.news.util.Resource
import kotlinx.android.synthetic.main.fragment_breaking.*


class Breaking : Fragment(R.layout.fragment_breaking) {
    lateinit var viewModel : NewsVM
    lateinit var newsAdapter : NewsAdapter
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        viewModel = (activity as MainActivity).viewModel

        setupRecyclerView()
        viewModel.breakingNews.observe(viewLifecycleOwner, Observer { response ->
            when (response){
            is Resource.Succes ->{
                hideProgressBar()
                response.data?.let {newsResponse ->
                    newsAdapter.differ.submitList(newsResponse.articles)

                }
        }
                is Resource.Error -> {

                    hideProgressBar()
                    response.message?.let{
                        d("Lj-Breaking","Error: $it")
                    }
                }
                is Resource.Loading->{
                    showProgressBar()
                }
        }
        })
    }

    private fun hideProgressBar(){
        paginationProgressBar.visibility = View.INVISIBLE
    }

    private fun showProgressBar(){
        paginationProgressBar.visibility = View.VISIBLE
    }
    private fun setupRecyclerView(){
        newsAdapter = NewsAdapter()
        rvBreakingNews.apply {
            adapter = newsAdapter
            layoutManager = LinearLayoutManager(activity)
        }
    }
}
