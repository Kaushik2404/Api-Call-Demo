package com.example.apicalldemo

import com.example.apicalldemo.modal.DemoModal
import retrofit2.http.GET
import retrofit2.http.Query


interface ApiInterface {
    @GET("3/discover/movie?sort_by=popularity.desc")
    suspend fun getPopularMovies(
        @Query("api_key") apiKey: String? = "57a4269b6c0098d52f01d65572e57972",
    ): DemoModal

}