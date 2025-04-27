package com.example.submission

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.example.submission.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navHostFragment = supportFragmentManager
            .findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        val navController = navHostFragment.navController
        binding.bottomNav.setupWithNavController(navController)
        binding.bottomNav.setOnItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.homeFragment -> {
                    navController.navigate(R.id.homeFragment)
                    true
                }
                R.id.favoriteFragment -> {
                    try {
                        val intent = Intent().setClassName(
                            this,
                            "com.example.submission.favorite.favorite.FavoriteActivity"
                        )
                        startActivity(intent)
                        true
                    } catch (e: Exception) {
                        Toast.makeText(this, "Favorite feature is not ready.", Toast.LENGTH_SHORT).show()
                        false
                    }
                }
                else -> false
            }
        }
    }
}

