package com.example.submission.core.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.submission.core.domain.model.Movie
import com.example.submission.databinding.ItemMovieBinding

class MovieAdapter : RecyclerView.Adapter<MovieAdapter.MovieViewHolder>() {

    private val listData = ArrayList<Movie>()

    private var onItemClick: ((Movie) -> Unit)? = null

    fun setData(newList: List<Movie>?) {
        if (newList == null) return
        listData.clear()
        listData.addAll(newList)
        notifyDataSetChanged()
    }

    fun setOnItemClick(callback: (Movie) -> Unit) {
        this.onItemClick = callback
    }

    inner class MovieViewHolder(private val binding: ItemMovieBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(data: Movie) {
            binding.tvTitle.text = data.title
            binding.tvDate.text = data.releaseDate
            binding.tvRating.text = data.voteAverage.toString()
            Glide.with(binding.root.context)
                .load("https://image.tmdb.org/t/p/w500${data.posterPath}")
                .into(binding.ivPoster)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val binding = ItemMovieBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MovieViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        holder.bind(listData[position])
        holder.itemView.setOnClickListener {
            onItemClick?.invoke(listData[position])
        }
    }

    override fun getItemCount(): Int = listData.size
}