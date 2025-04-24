package com.example.submission.core.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.submission.core.databinding.ItemMovieBinding
import com.example.submission.core.domain.model.Movie

class MovieAdapter : ListAdapter<Movie, MovieAdapter.ListViewHolder>(DIFF_CALLBACK) {

    private var onItemClick: ((Movie) -> Unit)? = null

    fun setOnItemClick(callback: (Movie) -> Unit) {
        this.onItemClick = callback
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val binding = ItemMovieBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return ListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val data = getItem(position)
        holder.bind(data)
    }

    inner class ListViewHolder(private val binding: ItemMovieBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(data: Movie) {
            with(binding) {
                Glide.with(itemView.context)
                    .load("https://image.tmdb.org/t/p/w500${data.posterPath}")
                    .into(ivPoster)

                tvTitle.text = data.title
                tvRating.text = data.voteAverage.toString()
                tvDate.text = data.releaseDate
                tvDesc.text = data.overview
            }
        }

        init {
            itemView.setOnClickListener {
                onItemClick?.invoke(getItem(bindingAdapterPosition))
            }
        }
    }

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Movie>() {
            override fun areItemsTheSame(oldItem: Movie, newItem: Movie): Boolean =
                oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: Movie, newItem: Movie): Boolean =
                oldItem == newItem
        }
    }
}
