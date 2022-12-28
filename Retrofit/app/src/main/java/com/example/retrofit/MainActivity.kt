package com.example.retrofit

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.retrofit.adapter.MoviesAdapter
import com.example.retrofit.api.ApiClient
import com.example.retrofit.api.ApiService
import com.example.retrofit.databinding.ActivityMainBinding
import com.example.retrofit.response.MovieListResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class MainActivity : AppCompatActivity() {

    private lateinit var binding:ActivityMainBinding
    private val movieAdapter by lazy { MoviesAdapter() }
    private val api : ApiService by lazy {
        ApiClient().getClient().create(ApiService::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.apply {
            prgBarMovie.visibility = View.VISIBLE

            var callMovieApi = api.getPopularMovie(1)
            callMovieApi.enqueue(object : Callback<MovieListResponse>{
                override fun onResponse(
                    call: Call<MovieListResponse>,
                    response: Response<MovieListResponse>
                ) {
                    prgBarMovie.visibility = View.GONE
                    when(response.code()){
                        //Successful response
                        in 200..299->{
                            Log.d("Response Code", " success messages : ${response.code()}")
                            response.body()?.let { itBody ->
                                itBody.results.let { itData ->
                                    if (itData.isNotEmpty()){
                                        movieAdapter.differ.submitList(itData)
                                        rvMovie.apply {
                                            layoutManager=LinearLayoutManager(this@MainActivity)
                                            adapter=movieAdapter
                                        }
                                    }
                                }
                            }
                        }
                        //Redirection message
                        in 300..399->{
                            Log.d("Response Code", "Redirection messages : ${response.code()}")
                        }
                        //Client Error responses
                        in 400..499->{
                            Log.d("Response Code", "Redirection messages : ${response.code()}")
                        }
                        //Server error responses
                        in 500..599->{
                            Log.d("Response Code", "Redirection messages : ${response.code()}")
                        }

                    }
                }

                override fun onFailure(call: Call<MovieListResponse>, t: Throwable) {
                    prgBarMovie.visibility = View.GONE
                    Log.e("onFailure", "Err : ${t.message}")
                }

            })


        }
    }
}