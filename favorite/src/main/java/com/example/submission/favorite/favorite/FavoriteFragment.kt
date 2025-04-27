package com.example.submission.favorite.favorite

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.submission.R
import com.example.submission.core.ui.MovieAdapter
import com.example.submission.detail.DetailActivity
import com.example.submission.detail.DetailFragment
import com.example.submission.favorite.databinding.FragmentFavoriteBinding
import org.koin.android.ext.android.getKoin
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.context.loadKoinModules

class FavoriteFragment : Fragment() {

    private var _binding: FragmentFavoriteBinding? = null
    private val binding get() = _binding!!

    private val viewModel by viewModel<FavoriteViewModel>()
    private lateinit var adapter: MovieAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        if (getKoin().getOrNull<FavoriteViewModel>() == null) {
            loadKoinModules(favoriteModule)
        }
        _binding = FragmentFavoriteBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter = MovieAdapter().apply {
            setOnItemClick { selectedMovie ->
                val intent = Intent(requireContext(), DetailActivity::class.java)
                intent.putExtra(DetailFragment.EXTRA_MOVIE, selectedMovie)
                startActivity(intent)
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

