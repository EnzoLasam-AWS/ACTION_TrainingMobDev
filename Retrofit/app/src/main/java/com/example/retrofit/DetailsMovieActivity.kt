package com.example.retrofit

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import coil.load
import coil.size.Scale
import com.example.retrofit.api.ApiClient
import com.example.retrofit.api.ApiService
import com.example.retrofit.databinding.ActivityDetailsMovieBinding
import com.example.retrofit.response.MovieDetails
import com.example.retrofit.utils.Constants.POSTER_BASE_URL
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailsMovieActivity : AppCompatActivity() {

    private lateinit var binding : ActivityDetailsMovieBinding
    private val api : ApiService by lazy {
        ApiClient().getClient().create(ApiService::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailsMovieBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val movieId = intent.getIntExtra("id",1)
        binding.apply {
            //show loading
            prgBarMovies.visibility = View.VISIBLE
            val callDetailMovieApi = api.getMovieDetails(movieId)
            callDetailMovieApi.enqueue(object : Callback<MovieDetails>{
                override fun onResponse(
                    call: Call<MovieDetails>,
                    response: Response<MovieDetails>
                ) {
                    Log.e("onFailure", "Err : ${response.code()}")
                    prgBarMovies.visibility = View.GONE
                    when(response.code()) {
                        //Successful response
                        in 200..299 -> {
                            response.body().let { itBody ->
                                val imagePoster = POSTER_BASE_URL + itBody!!.poster_path
                                moviePoster.load(imagePoster) {
                                    crossfade(true)
                                    placeholder(R.drawable.poster_placeholder)
                                    scale(Scale.FILL)
                                }
                                imgBackground.load(imagePoster) {
                                    crossfade(true)
                                    placeholder(R.drawable.poster_placeholder)
                                    scale(Scale.FILL)
                                }

                                movieName.text = itBody.title
                                tagLine.text = itBody.tagline
                                mvDateReleased.text = itBody.release_date
                                mvRating.text = itBody.vote_average.toString()
                                mvRuntime.text = itBody.runtime.toString()
                                mvBudget.text = itBody.budget.toString()
                                mvRevenue.text = itBody.revenue.toString()
                                mvOverview.text = itBody.overview
                            }
                        }

                        //Redirection message
                        in 300..399 -> {
                            Log.d("Response Code", "Redirection messages : ${response.code()}")
                        }
                        //Client Error responses
                        in 400..499 -> {
                            Log.d("Response Code", "Redirection messages : ${response.code()}")
                        }
                        //Server error responses
                        in 500..599 -> {
                            Log.d("Response Code", "Redirection messages : ${response.code()}")
                        }
                    }
                }

                override fun onFailure(call: Call<MovieDetails>, t: Throwable) {
                    prgBarMovies.visibility = View.GONE
                    Log.e("onFailure", "Err : ${t.message}")
                }


            })

        }

    }
}