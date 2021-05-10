package com.alansoft.pokedex.ui.search

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.View
import androidx.appcompat.widget.SearchView
import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.alansoft.pokedex.R
import com.alansoft.pokedex.data.Resource
import com.alansoft.pokedex.data.model.Name
import com.alansoft.pokedex.databinding.FragmentSearchBinding
import com.alansoft.pokedex.ui.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

/**
 * Created by LEE MIN KYU on 2021/05/09
 * Copyright © 2021 Dreamus Company. All rights reserved.
 */
@AndroidEntryPoint
class SearchFragment : BaseFragment<FragmentSearchBinding>() {

    private val viewModel: SearchViewModel by viewModels()
    private val adapter: SearchListAdapter = SearchListAdapter(this::onItemClicked)

    override fun getLayoutId(): Int = R.layout.fragment_search

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_items, menu)
        setSearchMenu(menu)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setHasOptionsMenu(true)
        setRecyclerAdapter()
        setSubscribe()
    }

    private fun setSubscribe() {
        viewModel.results.observe(viewLifecycleOwner, {
            when (it) {
                is Resource.Success -> {
                    adapter.submitList(it.data.pokemons)
                }
                is Resource.Empty -> {
                }
                is Resource.Error -> {
                    val message = if (it.isNetworkError) {
                        "네트워크 연결을 확인 하세요."
                    } else {
                        it.exception.message
                    }
                }
                else -> {
                    // nothing
                }
            }
        })
    }


    private fun setRecyclerAdapter() {
        binding.recyclerView.run {
            setHasFixedSize(true)
            adapter = this@SearchFragment.adapter
        }
    }

    private fun setSearchMenu(menu: Menu) {
        val searchItem = menu.findItem(R.id.action_search)
        (searchItem.actionView as SearchView).run {
            queryHint = "포켓몬을 검색 하세요."
            setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String): Boolean {
                    viewModel.setQuery(query)
                    return false
                }

                override fun onQueryTextChange(newText: String): Boolean {
                    return true
                }
            })
        }
    }

    private fun onItemClicked(item: Name) {
        if (findNavController().currentDestination?.id == R.id.searchFragment) {
            val bundle = bundleOf("name" to item.getName(), "id" to item.id)
            val direction = SearchFragmentDirections.searchToDialog(bundle)
            findNavController().navigate(direction)
        }
    }
}