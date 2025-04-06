package com.example.submission.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.example.submission.core.domain.model.Movie
import com.example.submission.databinding.FragmentDetailBinding

class DetailFragment : Fragment() {

    private var _binding: FragmentDetailBinding? = null
    private val binding get() = _binding!!

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

        val movie = arguments?.getParcelable<Movie>(EXTRA_MOVIE)
        if (movie != null) showDetail(movie)
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

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}