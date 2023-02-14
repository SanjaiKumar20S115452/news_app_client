package com.sanjai.newsapiclientpart2

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.sanjai.newsapiclientpart2.databinding.FragmentSavedBinding
import com.sanjai.newsapiclientpart2.presentation.adapter.NewsAdapter
import com.sanjai.newsapiclientpart2.presentation.viewmodel.NewsViewModel

class SavedFragment : Fragment() {
    private lateinit var binding: FragmentSavedBinding
    private lateinit var adapter: NewsAdapter
    private lateinit var viewModel: NewsViewModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_saved, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentSavedBinding.bind(view)
        viewModel = (activity as MainActivity).viewModel
        adapter = (activity as MainActivity).adapter
        adapter.setOnItemClickListener {
            val bundle = Bundle().apply {
                putSerializable("selected_article",it)
            }
            findNavController().navigate(R.id.action_savedFragment_to_infoFragment,bundle)
        }
        initRecyclerView()
        viewModel.getSavedNews().observe(viewLifecycleOwner, Observer {
            adapter.differ.submitList(it)
        })

        val itemTouchHelperCallback = object: ItemTouchHelper.SimpleCallback(
            ItemTouchHelper.UP or ItemTouchHelper.DOWN,
            ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
        ) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return true
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.adapterPosition
                val article = adapter.differ.currentList[position] // GETTING THE EXACT POSITION ARTICLE WHEN THE USER SWIPES
                viewModel.deleteArticle(article)
                Snackbar.make(view,"Deleted Successfully!!",Snackbar.LENGTH_LONG).apply {
                    setAction("UNDO") {
                        viewModel.saveNews(article)
                    }
                    show()
                }
            }

        }

        ItemTouchHelper(itemTouchHelperCallback).apply {
            attachToRecyclerView(binding.rvSaved)
        }
    }

    private fun initRecyclerView() {
        binding.rvSaved.layoutManager = LinearLayoutManager(requireContext())
        binding.rvSaved.adapter = adapter
    }
}