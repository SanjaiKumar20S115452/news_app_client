package com.sanjai.newsapiclientpart2

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AbsListView
import android.widget.SearchView
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.sanjai.newsapiclientpart2.data.util.Resource
import com.sanjai.newsapiclientpart2.databinding.FragmentNewsBinding
import com.sanjai.newsapiclientpart2.presentation.adapter.NewsAdapter
import com.sanjai.newsapiclientpart2.presentation.viewmodel.NewsViewModel
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class NewsFragment : Fragment() {

    private lateinit var binding: FragmentNewsBinding
    private lateinit var viewModel: NewsViewModel
    private lateinit var adapter: NewsAdapter
    private val country: String = "us"
    private var page: Int = 1
    private var pages = 0
    private var isScrolling = false
    private var isLoading = false
    private var isLastPage = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_news, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentNewsBinding.bind(view)
        viewModel = (activity as MainActivity).viewModel
        adapter = (activity as MainActivity).adapter
        adapter.setOnItemClickListener {
            val bundle = Bundle().apply {
                putSerializable("selected_article",it)
            }
            findNavController().navigate(R.id.action_newsFragment_to_infoFragment,bundle)
        }
        initRecyclerView()
        displayNewsHeadlines()
        setSearchView()
    }

    private fun initRecyclerView() {
        binding.rvNews.adapter = adapter
        binding.rvNews.layoutManager = LinearLayoutManager(requireContext())
        binding.rvNews.addOnScrollListener(this@NewsFragment.onScrollListener)
    }

    private fun displayNewsHeadlines() {
        viewModel.getNewsHeadlines(country, page)
        viewModel.newsHeadlines.observe(viewLifecycleOwner, Observer { response->
            when(response) {
                is Resource.Success -> {
                    response.data?.let {
                        hideProgressBar()
                        adapter.differ.submitList(it.articles.toList())
                        if(it.totalResults%20 == 0) {
                            pages = it.totalResults / 20
                        }else {
                            pages = it.totalResults/20 + 1
                        }
                        isLastPage = page == pages

                    }
                }
                is Resource.Loading -> {
                    showProgressBar()
                }
                is Resource.Error -> {
                    response.message?.let {
                        Toast.makeText(requireContext(), "An Error Occurred: $it", Toast.LENGTH_SHORT).show()
                    }
                }
            }

        })
    }


    private fun showProgressBar() {
        isLoading = true
        binding.progressBar.visibility = View.VISIBLE
    }

    private fun hideProgressBar() {
        isLoading = false
        binding.progressBar.visibility = View.INVISIBLE
    }

    private val onScrollListener = object: RecyclerView.OnScrollListener() {
        override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
            super.onScrollStateChanged(recyclerView, newState)
            if(newState == AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL) {
                isScrolling = true
            }
        }

        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
            super.onScrolled(recyclerView, dx, dy)
            val layoutManager = binding.rvNews.layoutManager as LinearLayoutManager
            val sizeOfTheCurrentList = layoutManager.itemCount // Total size of the list available in the RV
            val visibleListItem = layoutManager.childCount // Visible item count while the user is seeing the screen!
            val topPosition = layoutManager.findFirstVisibleItemPosition()
            val hasReachedToEnd = topPosition+visibleListItem >= sizeOfTheCurrentList
            val shouldPaginate = !isLastPage && !isLoading && hasReachedToEnd && isScrolling
            if(shouldPaginate) {
                page++
                viewModel.getNewsHeadlines(country,page)
                isScrolling = false
            }
        }
    }

    private fun setSearchView() {
        binding.svNews.setOnQueryTextListener(object: SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(p0: String?): Boolean {
                viewModel.searchNews(country,page,p0.toString())
                searchNews()
                return false
            }

            override fun onQueryTextChange(p0: String?): Boolean {
                MainScope().launch {
                    delay(2000)
                    viewModel.searchNews(country,page,p0.toString())
                    searchNews()
                }
                return false
            }
        })
        binding.svNews.setOnCloseListener(object: SearchView.OnCloseListener{
            override fun onClose(): Boolean {
                initRecyclerView()
                displayNewsHeadlines()
                return false
            }

        })
    }

    private fun searchNews() {
        if(view != null) {
            viewModel.searchNews.observe(viewLifecycleOwner, Observer { response ->
                when (response) {
                    is Resource.Success -> {
                        response.data?.let {
                            hideProgressBar()
                            adapter.differ.submitList(it.articles.toList())
                            if (it.totalResults % 20 == 0) {
                                pages = it.totalResults / 20
                            } else {
                                pages = it.totalResults / 20 + 1
                            }
                            isLastPage = page == pages

                        }
                    }
                    is Resource.Loading -> {
                        showProgressBar()
                    }
                    is Resource.Error -> {
                        response.message?.let {
                            Toast.makeText(
                                requireContext(),
                                "An Error Occurred: $it",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
                }

            })
        }
    }
}