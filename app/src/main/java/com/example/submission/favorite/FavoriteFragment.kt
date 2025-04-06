package com.example.submission.favorite

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.submission.R
import com.example.submission.core.ui.MovieAdapter
import com.example.submission.core.ui.ViewModelFactory
import com.example.submission.databinding.FragmentFavoriteBinding
import com.example.submission.detail.DetailFragment

class FavoriteFragment : Fragment() {

    private var _binding: FragmentFavoriteBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: FavoriteViewModel
    private lateinit var adapter: MovieAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFavoriteBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val factory = ViewModelFactory.getInstance(requireContext())
        viewModel = ViewModelProvider(this, factory)[FavoriteViewModel::class.java]

        adapter = MovieAdapter().apply {
            setOnItemClick { selectedMovie ->
                val bundle = Bundle().apply {
                    putParcelable(DetailFragment.EXTRA_MOVIE, selectedMovie)
                }
                findNavController().navigate(R.id.action_favoriteFragment_to_detailFragment, bundle)
            }
        }

        binding.rvFavorite.apply {
            layoutManager = LinearLayoutManager(requireContext())
            setHasFixedSize(true)
            this.adapter = this@FavoriteFragment.adapter
        }

        viewModel.favoriteMovies.observe(viewLifecycleOwner) { list ->
            Log.d("FAV_DEBUG", "Jumlah favorite: ${list.size}")
            list.forEach {
                Log.d("FAV_DEBUG", "🎬 ${it.title} - Favorite: ${it.isFavorite}")
            }
            adapter.submitList(list)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

