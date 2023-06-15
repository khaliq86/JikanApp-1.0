package com.example.jikanconnect

import android.util.Log
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.jikanconnect.network.ApiClient
import com.google.android.material.snackbar.Snackbar
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.lang.Integer.min

class MainViewModel : ViewModel() {

    private val _animeList = MutableLiveData<List<Anime>>()
    val animeList: LiveData<List<Anime>> get() = _animeList

    fun fetchAnimeListStatus(){
        val apiService = ApiClient.createApiService()
        val call = apiService.getAnimeStatus("complete")
        call.enqueue(object : Callback<AnimeResponse> {
            override fun onResponse(call: Call<AnimeResponse>, response: Response<AnimeResponse>) {
                if (response.isSuccessful) {
                    Log.d("MainActivity", "Response: ${response.body()}")
                    val result = response.body()?.animeList ?: emptyList()
                    val randomAnime = 15
                    val shufledList = result.shuffled()
                    val randomAnimeList = shufledList.subList(0, min(randomAnime, shufledList.size))
                    _animeList.value = randomAnimeList
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

    fun fetchAnimeList(title: String){
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


