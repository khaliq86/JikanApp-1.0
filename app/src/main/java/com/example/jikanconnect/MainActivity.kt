package com.example.jikanconnect

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.Toast
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
    private lateinit var progressBar: ProgressBar
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)

        viewModel.fetchAnimeListStatus()
        progressBar = findViewById<ProgressBar>(R.id.progressBar)

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
                hideProgressBar()
        }
        val button = findViewById<Button>(R.id.searchButton)
        val search = findViewById<EditText>(R.id.searchEditText)

        button.setOnClickListener {
            val animeTitle = search.text.toString()
            if(animeTitle.isNotEmpty()){
                showProgressBar()
                viewModel.fetchAnimeList(animeTitle)
            }
            else{
                Toast.makeText(this, "Masukkan nama anime terlebih dahulu", Toast.LENGTH_SHORT).show()
            }
        }
    }
    private fun showProgressBar() {
        progressBar.visibility = View.VISIBLE
    }

    private fun hideProgressBar() {
        progressBar.visibility = View.GONE
    }
}

