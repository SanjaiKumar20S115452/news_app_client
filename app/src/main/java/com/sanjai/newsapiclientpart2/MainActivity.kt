package com.sanjai.newsapiclientpart2

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.sanjai.newsapiclientpart2.databinding.ActivityMainBinding
import com.sanjai.newsapiclientpart2.presentation.adapter.NewsAdapter
import com.sanjai.newsapiclientpart2.presentation.viewmodel.NewsViewModel
import com.sanjai.newsapiclientpart2.presentation.viewmodel.NewsViewModelFactory
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    @Inject
    lateinit var factory: NewsViewModelFactory
    @Inject
    lateinit var adapter: NewsAdapter
    lateinit var viewModel: NewsViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navHostFragment = supportFragmentManager.findFragmentById(R.id.fragment) as NavHostFragment
        val navController = navHostFragment.navController
        binding.bnvNews.setupWithNavController(navController)

        viewModel = ViewModelProvider(this@MainActivity,factory)[NewsViewModel::class.java]

    }
}