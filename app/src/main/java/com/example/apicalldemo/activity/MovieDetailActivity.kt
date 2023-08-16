package com.example.apicalldemo.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bumptech.glide.Glide
import com.example.apicalldemo.R
import com.example.apicalldemo.databinding.ActivityMovieDetailBinding

class MovieDetailActivity : AppCompatActivity() {
    lateinit var binding: ActivityMovieDetailBinding
    lateinit var Movie_Detail:String
    lateinit var Movie_Img:String
    lateinit var Movie_Rate:String
    lateinit var Movie_Name:String


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityMovieDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        Movie_Name=intent.getStringExtra("MOVIE_NAME").toString()
        Movie_Rate=intent.getStringExtra("MOVIE_RATE").toString()
        Movie_Img=intent.getStringExtra("MOVIE_IMG").toString()
        Movie_Detail=intent.getStringExtra("MOVIE_DETAIL").toString()

        setData()
    }

    private fun setData() {
        Glide.with(this).load("https://image.tmdb.org/t/p/w500"+Movie_Img)
            .into(binding.movieImg)
        binding.movieDetail.text=Movie_Detail
        binding.movieNameDetail.text=Movie_Name
        binding.movieRate.text=Movie_Rate
    }
}