package com.example.submission.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.submission.R
import com.example.submission.core.domain.model.Movie
import com.example.submission.core.ui.ViewModelFactory
import com.example.submission.databinding.FragmentDetailBinding

class DetailFragment : Fragment() {

    private var _binding: FragmentDetailBinding? = null
    private val binding get() = _binding!!

    private lateinit var detailViewModel: DetailViewModel

    companion object {
        const val EXTRA_MOVIE = "extra_movie"
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val factory = ViewModelFactory.getInstance(requireContext())
        detailViewModel = ViewModelProvider(this, factory)[DetailViewModel::class.java]

        val movie = arguments?.getParcelable<Movie>(EXTRA_MOVIE)
        movie?.let {
            detailViewModel.setSelectedMovie(it)
        }

        detailViewModel.selectedMovie.observe(viewLifecycleOwner) { movie ->
            showDetail(movie)
            updateFavoriteIcon(movie.isFavorite)
        }

        binding.fabFavorite.setOnClickListener {
            detailViewModel.toggleFavorite()
        }
    }

    private fun showDetail(movie: Movie) {
        with(binding) {
            tvTitle.text = movie.title
            tvReleaseDate.text = "Release Date: ${movie.releaseDate}"
            tvRating.text = "Rating: ${movie.voteAverage}"
            tvOverview.text = movie.overview
            Glide.with(requireContext())
                .load("https://image.tmdb.org/t/p/w500${movie.posterPath}")
                .into(ivPoster)
        }
    }

    private fun updateFavoriteIcon(isFavorite: Boolean) {
        binding.fabFavorite.setImageResource(
            if (isFavorite) R.drawable.ic_favorite else R.drawable.ic_favorite_off
        )
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
