package com.example.retrofit.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.size.Scale
import com.example.retrofit.DetailsMovieActivity
import com.example.retrofit.R
import com.example.retrofit.databinding.ItemMoviesBinding

import com.example.retrofit.response.MovieListResponse
import com.example.retrofit.utils.Constants.POSTER_BASE_URL

class MoviesAdapter : RecyclerView.Adapter<MoviesAdapter.ViewHolder>() {

    private lateinit var binding: ItemMoviesBinding
    private lateinit var context: Context


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        binding = ItemMoviesBinding.inflate(inflater, parent, false)
        context = parent.context
        return ViewHolder()
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(differ.currentList[position])
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }

    inner class ViewHolder : RecyclerView.ViewHolder(binding.root){
        fun bind(item : MovieListResponse.Result){
            binding.apply {
                movieName.text = item.title
                movieRate.text = item.vote_average.toString()
                val moviePosterURL = POSTER_BASE_URL + item?.poster_path
                moviePoster.load(moviePosterURL){
                    crossfade(true)
                    placeholder(R.drawable.poster_placeholder)
                    scale(Scale.FILL)
                }
                movieLang.text = item.original_language
                root.setOnClickListener{
                    val intent = Intent(context, DetailsMovieActivity::class.java)
                    intent.putExtra("id", item?.id)
                    context.startActivity(intent)
                }
            }
        }

    }
    private val differCallback = object : DiffUtil.ItemCallback<MovieListResponse.Result>(){
        override fun areItemsTheSame(
            oldItem: MovieListResponse.Result,
            newItem: MovieListResponse.Result
        ): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(
            oldItem: MovieListResponse.Result,
            newItem: MovieListResponse.Result
        ): Boolean {
            return oldItem==newItem
        }

    }

    val differ = AsyncListDiffer(this, differCallback)

}