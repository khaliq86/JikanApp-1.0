package com.example.jikanconnect

import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import coil.load
import com.bumptech.glide.Glide

class DetailActivity : AppCompatActivity() {
    private lateinit var imageView: ImageView
    private lateinit var judulTextView: TextView
    private lateinit var synopsisTextView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        imageView = findViewById(R.id.imageView)
        judulTextView = findViewById(R.id.judul)
        synopsisTextView = findViewById(R.id.synopsis)

        var intent = intent
        var nama = intent.getStringExtra("Nama")
        var sinopsis=intent.getStringExtra("Sinopsis")
        var gambar=intent.getStringExtra("Gambar")

        val actionBar: ActionBar? = supportActionBar
        actionBar?.setTitle("Detail Anime")

        Glide.with(imageView.context).apply{
            load(gambar)
                .into(imageView)
        }
        judulTextView.text=nama
        synopsisTextView.text=sinopsis

    }
}
