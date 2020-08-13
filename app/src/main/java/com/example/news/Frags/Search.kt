package com.example.news.Frags

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.AbsListView
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.news.app.MainActivity

import com.example.news.R
import com.example.news.VM.NewsVM
import com.example.news.adapters.NewsAdapter
import com.example.news.util.Constants
import com.example.news.util.Constants.Companion.SEARCH_NEWS_TIME_DELAY
import com.example.news.util.Resource
import kotlinx.android.synthetic.main.fragment_search.*
import kotlinx.android.synthetic.main.fragment_search.paginationProgressBar
import kotlinx.coroutines.Job
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


class Search : Fragment(R.layout.fragment_search) {

    lateinit var viewModel : NewsVM
    lateinit var newsAdapter : NewsAdapter


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        viewModel = (activity as MainActivity).viewModel
        setupRecyclerView()
        newsAdapter.setOnItemClickListener {
            var bundle = Bundle().apply {
                putSerializable("article",it)
            }
            findNavController().navigate(R.id.action_search_to_articleFragment,bundle)
        }
        var job : Job?= null
        etSearch.addTextChangedListener{editable->
            job?.cancel()
            job = MainScope().launch {
                delay(SEARCH_NEWS_TIME_DELAY)
                editable?.let {
                    if(editable.toString().isNotEmpty()){
                        viewModel.searchForNews(editable.toString() )
                    }
                }
            }
        }
        viewModel.searchNews.observe(viewLifecycleOwner, Observer { response ->
            when (response){
                is Resource.Succes ->{
                    hideProgressBar()
                    response.data?.let {newsResponse ->
                        newsAdapter.differ.submitList(newsResponse.articles.toList())
                        val totalPages = newsResponse.totalResults/ Constants.QUERY_PAGE_SIZE + 2
                        isLastPage =viewModel.searchNewsPage == totalPages
                        if(isLastPage){
                            rvSearchNews.setPadding(0,0,0,0)
                        }

                    }
                }
                is Resource.Error -> {

                    hideProgressBar()
                    response.message?.let{
                        Log.d("Lj-Saved", "Error: $it")
                    }
                }
                is Resource.Loading->{
                    showProgressBar()
                }
            }
        })
    }

    var isLoading = false
    var isLastPage = false
    var isScrolling = false

    var scrollListener = object : RecyclerView.OnScrollListener(){
        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
            super.onScrolled(recyclerView, dx, dy)

            val layoutManager = recyclerView.layoutManager as LinearLayoutManager
            val firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition()
            val visibleItemCount = layoutManager.childCount
            val totalItemCount = layoutManager.itemCount

            val isNotLoadingAndNotLastPage = !isLoading && !isLastPage

            val isAtLastItem = firstVisibleItemPosition + visibleItemCount >= totalItemCount

            val isNotAtBeginning =firstVisibleItemPosition >= 0
            val isTotalMoreThanVisible = totalItemCount >= Constants.QUERY_PAGE_SIZE
            val shouldPaginate = isNotLoadingAndNotLastPage && isAtLastItem &&isNotAtBeginning && isTotalMoreThanVisible

            if(shouldPaginate) {
                viewModel.searchForNews(etSearch.text.toString())
                isScrolling = false
            }

        }

        override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
            super.onScrollStateChanged(recyclerView, newState)
            if(newState == AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL){
                isScrolling = true
            }
        }

    }


    private fun hideProgressBar(){
        paginationProgressBar.visibility = View.INVISIBLE
        isLoading = false
    }

    private fun showProgressBar(){
        paginationProgressBar.visibility = View.VISIBLE
        isLoading = true
    }
    private fun setupRecyclerView(){
        newsAdapter = NewsAdapter()
        rvSearchNews.apply {
            adapter = newsAdapter
            layoutManager = LinearLayoutManager(activity)
            addOnScrollListener(this@Search.scrollListener)
        }
    }
}
