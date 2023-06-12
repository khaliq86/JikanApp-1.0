package com.example.jikanconnect

import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.jikanconnect.network.ApiClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainViewModel : ViewModel() {
    private val _animeList = MutableLiveData<List<Anime>>()
    val animeList: LiveData<List<Anime>> get() = _animeList

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> get() = _isLoading

    fun fetchAnimeList(title: String){
        _isLoading.value = true
        val apiService = ApiClient.createApiService()
        val call = apiService.getAnime(title)
        call.enqueue(object : Callback<AnimeResponse> {
            override fun onResponse(call: Call<AnimeResponse>, response: Response<AnimeResponse>) {
                if (response.isSuccessful) {
                    Log.d("MainActivity", "Response: ${response.body()}")
                    val result = response.body()?.animeList ?: emptyList()
                    _animeList.value = result
                } else {
                    Log.d("MainActivity", "Error: ${response.message()}")
                    _animeList.value = emptyList()
                }
            }

            override fun onFailure(call: Call<AnimeResponse>, t: Throwable) {
                Log.d("MainActivity", "Error: ${t.message}")
                _animeList.value = emptyList()
            }
        })
    }
}


