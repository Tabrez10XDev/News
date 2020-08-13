package com.example.news.Frags

import android.os.Bundle
import android.util.Log.d
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.AbsListView
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.news.app.MainActivity

import com.example.news.R
import com.example.news.VM.NewsVM
import com.example.news.adapters.NewsAdapter
import com.example.news.util.Constants.Companion.QUERY_PAGE_SIZE
import com.example.news.util.Resource
import kotlinx.android.synthetic.main.fragment_breaking.*


class Breaking : Fragment(R.layout.fragment_breaking) {
    lateinit var viewModel : NewsVM
    lateinit var newsAdapter : NewsAdapter
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        viewModel = (activity as MainActivity).viewModel

        setupRecyclerView()

        newsAdapter.setOnItemClickListener {
            val bundle = Bundle().apply {
                putSerializable("article",it)
            }
            findNavController().navigate(R.id.action_breaking_to_articleFragment,bundle)
        }
        viewModel.breakingNews.observe(viewLifecycleOwner, Observer { response ->
            when (response){
            is Resource.Succes ->{
                hideProgressBar()
                response.data?.let {newsResponse ->
                    newsAdapter.differ.submitList(newsResponse.articles.toList())
                    val totalPages = newsResponse.totalResults/ QUERY_PAGE_SIZE + 2
                    isLastPage =viewModel.breakingNewsPage == totalPages
                    if(isLastPage){
                        rvBreakingNews.setPadding(0,0,0,0)
                    }

                }
        }
                is Resource.Error -> {

                    hideProgressBar()
                    response.message?.let{
                        d("Lj-Breaking","Error: $it")
                        Toast.makeText(activity,"Error $it",Toast.LENGTH_SHORT).show()
                    }
                }
                is Resource.Loading->{
                    showProgressBar()
                }
        }
        })
    }
    //

    var isLoading = false
    var isLastPage = false
    var isScrolling = false

    var scrollListener = object :RecyclerView.OnScrollListener(){
        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
            super.onScrolled(recyclerView, dx, dy)

            val layoutManager = recyclerView.layoutManager as LinearLayoutManager
            val firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition()
            val visibleItemCount = layoutManager.childCount
            val totalItemCount = layoutManager.itemCount

            val isNotLoadingAndNotLastPage = !isLoading && !isLastPage

            val isAtLastItem = firstVisibleItemPosition + visibleItemCount >= totalItemCount

            val isNotAtBeginning =firstVisibleItemPosition >= 0
            val isTotalMoreThanVisible = totalItemCount >= QUERY_PAGE_SIZE
            val shouldPaginate = isNotLoadingAndNotLastPage && isAtLastItem &&isNotAtBeginning && isTotalMoreThanVisible

            if(shouldPaginate) {
                viewModel.getBreakingNews("in")
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
        rvBreakingNews.apply {
            adapter = newsAdapter
            layoutManager = LinearLayoutManager(activity)
            addOnScrollListener(this@Breaking.scrollListener)
        }
    }
}
