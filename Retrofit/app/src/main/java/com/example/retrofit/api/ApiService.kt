package com.example.retrofit.api

import com.example.retrofit.response.MovieListResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("movie/popular")
    fun getPopularMovie(@Query("page") page : Int) : Call<MovieListResponse>

}