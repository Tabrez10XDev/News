package com.example.news.VM

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.ConnectivityManager.*
import android.net.NetworkCapabilities.*
import android.os.Build
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.news.Room.Article
import com.example.news.Room.NewsRepository
import com.example.news.app.NewsApplication
import com.example.news.models.NewsResponse
import com.example.news.util.Resource
import kotlinx.coroutines.launch
import retrofit2.Response

class NewsVM(

        app : Application,
        val newsRepository: NewsRepository
) : AndroidViewModel(app) {

val breakingNews : MutableLiveData<Resource<NewsResponse>> = MutableLiveData()
    var breakingNewsPage = 1

    val searchNews : MutableLiveData<Resource<NewsResponse>> = MutableLiveData()
    var searchNewsPage = 1

    var breakingNewsResponse : NewsResponse ?= null
    var searchNewsResponse : NewsResponse ?= null



    init {
        getBreakingNews("in")
    }

    private suspend fun safeBreakingNews(countryCode: String){
        breakingNews.postValue(Resource.Loading())
        try {
            if(hasInternetConnection()){
                val response = newsRepository.getBreakingNews(countryCode, breakingNewsPage)
                breakingNews.postValue(handleBreakingNewsResponse(response))
            }
            else{
                breakingNews.postValue(Resource.Error("No Internet Connection"))
            }
        }catch(t : Throwable){
            breakingNews.postValue(Resource.Error("Catchable"))
        }
    }

    private suspend fun safeSearchNews(searchQuery: String){
        searchNews.postValue(Resource.Loading())
        try {
            if(hasInternetConnection()){
                val response = newsRepository.searchForNews(searchQuery,searchNewsPage)
                searchNews.postValue(handleSearchNewsResponse(response))
            }
            else{
                searchNews.postValue(Resource.Error("No Internet Connection"))
            }
        }catch(t : Throwable){
            searchNews.postValue(Resource.Error("Catchable"))
        }
    }


    fun getBreakingNews(countryCode : String) = viewModelScope.launch {
        safeBreakingNews(countryCode)
    }

    fun searchForNews(searchQuery : String) = viewModelScope.launch {
       safeSearchNews(searchQuery)
    }

    private fun handleBreakingNewsResponse(response: Response<NewsResponse>) : Resource<NewsResponse> {
        if(response.isSuccessful){
            response.body()?.let {
                breakingNewsPage++
                if(breakingNewsResponse == null){
                    breakingNewsResponse = it
                }
                else{
                    val oldArticles = breakingNewsResponse?.articles
                    val newArticles = it.articles
                    oldArticles?.addAll(newArticles)
                }
                return Resource.Succes(breakingNewsResponse ?: it)
            }
        }
        return Resource.Error(response.message())

    }

    private fun handleSearchNewsResponse(response: Response<NewsResponse>) : Resource<NewsResponse> {
        if(response.isSuccessful){
            response.body()?.let {
                searchNewsPage++
                if(searchNewsResponse == null){
                    searchNewsResponse = it
                }
                else{
                    val oldArticles = searchNewsResponse?.articles
                    val newArticles = it.articles
                    oldArticles?.addAll(newArticles)
                }
                return Resource.Succes(searchNewsResponse ?: it)
            }
        }
        return Resource.Error(response.message())

    }
    private fun hasInternetConnection(): Boolean{
        val connectivityManager = getApplication<NewsApplication>().getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            val activeNetworks = connectivityManager.activeNetwork ?: return false
            val capabilities = connectivityManager.getNetworkCapabilities(activeNetworks) ?: return false
            return when {
                capabilities.hasTransport(TRANSPORT_WIFI) -> true
                capabilities.hasTransport(TRANSPORT_CELLULAR)-> true
                capabilities.hasTransport(TRANSPORT_ETHERNET)->true
                else -> false
            }
        }
        else{
            connectivityManager.activeNetworkInfo?.run {
                return when(type){
                    TYPE_WIFI-> true
                    TYPE_MOBILE->true
                    TYPE_ETHERNET->true
                    else -> false
                }

            }
        }
        return false
    }

    fun saveArticle(article: Article) = viewModelScope.launch {
        newsRepository.upsert(article)
    }

    fun getSavedNews()= newsRepository.getSavedNews()

    fun deleteArticle(article: Article) = viewModelScope.launch {
        newsRepository.deleteArticle(article)
    }
}