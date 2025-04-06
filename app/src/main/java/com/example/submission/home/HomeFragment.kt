package com.example.submission.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.submission.R
import com.example.submission.core.data.Resource
import com.example.submission.core.ui.MovieAdapter
import com.example.submission.core.ui.ViewModelFactory
import com.example.submission.databinding.FragmentHomeBinding
import com.example.submission.detail.DetailFragment

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: HomeViewModel
    private lateinit var movieAdapter: MovieAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val factory = ViewModelFactory.getInstance(requireContext())
        viewModel = ViewModelProvider(this, factory)[HomeViewModel::class.java]

        movieAdapter = MovieAdapter().apply {
            setOnItemClick { selectedMovie ->
                val bundle = Bundle().apply {
                    putParcelable(DetailFragment.EXTRA_MOVIE, selectedMovie)
                }
                findNavController().navigate(R.id.action_homeFragment_to_detailFragment, bundle)
            }
        }

        binding.rvMovie.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = movieAdapter
            setHasFixedSize(true)
        }

        viewModel.movies.observe(viewLifecycleOwner) { resource ->
            when (resource) {
                is Resource.Loading -> {
                    binding.progressBar.visibility = View.VISIBLE
                }

                is Resource.Success -> {
                    binding.progressBar.visibility = View.GONE
                    resource.data?.let { movieList ->
                        movieAdapter.submitList(movieList)
                    }
                }

                is Resource.Error -> {
                    binding.progressBar.visibility = View.GONE
                    Toast.makeText(
                        requireContext(),
                        "Error: ${resource.message}",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
