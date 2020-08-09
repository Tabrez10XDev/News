package com.example.news.VM

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.news.Room.NewsRepository
import com.example.news.models.NewsResponse
import com.example.news.util.Resource
import kotlinx.coroutines.launch
import retrofit2.Response

class NewsVM(
    val newsRepository: NewsRepository
) : ViewModel() {

val breakingNews : MutableLiveData<Resource<NewsResponse>> = MutableLiveData()
    val breakingNewsPage = 1

    init {
        getBreakingNews("us")
    }

    fun getBreakingNews(countryCode : String) = viewModelScope.launch {
        breakingNews.postValue(Resource.Loading())
        val response = newsRepository.getBreakingNews(countryCode, breakingNewsPage)
        breakingNews.postValue(handleBreakingNewsResponse(response))
    }

    private fun handleBreakingNewsResponse(response: Response<NewsResponse>) : Resource<NewsResponse> {
        if(response.isSuccessful){
            response.body()?.let {
                return Resource.Succes(it)
            }
        }
        return Resource.Error(response.message())

    }
}