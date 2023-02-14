package com.sanjai.newsapiclientpart2.presentation.viewmodel

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import androidx.lifecycle.*
import com.sanjai.newsapiclientpart2.data.model.APIResponse
import com.sanjai.newsapiclientpart2.data.model.Article
import com.sanjai.newsapiclientpart2.data.util.Resource
import com.sanjai.newsapiclientpart2.domain.usecase.*
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import java.lang.Exception

class NewsViewModel(
    private val getNewsHeadlinesUseCase: GetNewsHeadlinesUseCase,
    private val app: Application,
    private val getSearchedNewsUseCase: GetSearchedNewsUseCase,
    private val saveNewsUseCase: SaveNewsUseCase,
    private val getSavedNewsUseCase: GetSavedNewsUseCase,
    private val deleteSavedNewsUseCase: DeleteSavedNewsUseCase
): AndroidViewModel(app) {
    val newsHeadlines: MutableLiveData<Resource<APIResponse>> = MutableLiveData()

    fun getNewsHeadlines(country: String,page: Int) {
        viewModelScope.launch {
            newsHeadlines.postValue(Resource.Loading())
            try {
                if (isNetworkAvailable(app)) {
                    viewModelScope.launch {
                        val apiResult = getNewsHeadlinesUseCase.execute(country, page)
                        newsHeadlines.postValue(apiResult)
                    }
                } else {
                    newsHeadlines.postValue(Resource.Error("No internet connection!"))
                }
            } catch (e: Exception) {
                newsHeadlines.postValue(Resource.Error(e.message.toString()))
            }
        }
    }

    private fun isNetworkAvailable(context: Context?):Boolean{
        if (context == null) return false
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            val capabilities = connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
            if (capabilities != null) {
                when {
                    capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> {
                        return true
                    }
                    capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> {
                        return true
                    }
                    capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> {
                        return true
                    }
                }
            }
        } else {
            val activeNetworkInfo = connectivityManager.activeNetworkInfo
            if (activeNetworkInfo != null && activeNetworkInfo.isConnected) {
                return true
            }
        }
        return false
    }

    //SEARCH
    val searchNews: MutableLiveData<Resource<APIResponse>> = MutableLiveData()

    fun searchNews(
        country: String,
        page: Int,
        searchQuery: String
    ) = viewModelScope.launch {
        searchNews.postValue(Resource.Loading())
        try {
            if (isNetworkAvailable(app)) {
                val response = getSearchedNewsUseCase.execute(country, page, searchQuery)
                searchNews.postValue(response)
            } else {
                searchNews.postValue(Resource.Error("No internet connection available!!"))
            }
        }catch (e:Exception) {
            searchNews.postValue(Resource.Error(e.message.toString()))
        }
    }

    //LOCAL DATABASE

    fun saveNews(article: Article) = viewModelScope.launch {
        saveNewsUseCase.execute(article)
    }


    //Get Saved News Use Case
    fun getSavedNews() = liveData {
        getSavedNewsUseCase.execute().collect {
            emit(it)
        }
    }

    //Delete Article or News
    fun deleteArticle(article: Article) = viewModelScope.launch {
        deleteSavedNewsUseCase.execute(article)
    }

}