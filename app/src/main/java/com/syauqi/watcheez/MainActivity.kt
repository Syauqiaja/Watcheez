package com.syauqi.watcheez

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.syauqi.watcheez.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding : ActivityMainBinding
    private lateinit var navController : NavController
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.bottomAppBar)

        setNavigation()
    }

    private fun setNavigation() {
        val navView : BottomNavigationView = binding.bottomNav
        navController = findNavController(R.id.main_container_view)
        val appBarConfiguration = AppBarConfiguration.Builder(
            R.id.home_fragment, R.id.search_fragment, R.id.favorite_fragment
        ).build()

        navController.addOnDestinationChangedListener{_, destination, _ ->
            binding.bottomAppBar.visibility = if(listFragmentBottomBar.contains(destination.id)) View.VISIBLE else View.GONE
        }

        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
    }

    companion object{
        val listFragmentBottomBar = listOf(
            R.id.home_fragment,
            R.id.search_fragment,
            R.id.favorite_fragment,
        )
    }
}