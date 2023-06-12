package com.example.jikanconnect

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.jikanconnect.network.ApiClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    companion object{
        val INTENT = "OBJECT_INTENT"
    }

    private lateinit var viewModel: MainViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)

        //loading
        viewModel.isLoading.observe(this) { isLoading ->
            if (isLoading) {
                progressBar.visibility = View.VISIBLE
                recyclerView.visibility = View.GONE
            } else {
                progressBar.visibility = View.GONE
                recyclerView.visibility = View.VISIBLE
            }
        }

        viewModel.animeList.observe(this) { animeList ->
            val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)

            recyclerView.setOnClickListener {
                val intent = Intent(this, DetailActivity::class.java)
                intent.putExtra(INTENT, 1)
                startActivity(intent)
            }
            val adapter = MainAdapter(animeList, this)
            recyclerView.adapter = adapter
            recyclerView.layoutManager =
                StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL)
        }
        val button = findViewById<Button>(R.id.searchButton)
        val search = findViewById<EditText>(R.id.searchEditText)

        button.setOnClickListener {
            viewModel.fetchAnimeList(search.text.toString())
        }
    }
}
