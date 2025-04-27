package com.example.submission.detail

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.submission.R
import com.example.submission.core.domain.model.Movie

class DetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        val movie = intent.getParcelableExtra<Movie>(DetailFragment.EXTRA_MOVIE)
        if (savedInstanceState == null && movie != null) {
            val detailFragment = DetailFragment().apply {
                arguments = Bundle().apply {
                    putParcelable(DetailFragment.EXTRA_MOVIE, movie)
                }
            }
            supportFragmentManager.beginTransaction()
                .replace(R.id.detail_container, detailFragment)
                .commit()
        }
    }
}
