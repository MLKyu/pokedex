package com.alansoft.pokedex.ui.dialog

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat.startActivities
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.alansoft.pokedex.R
import com.alansoft.pokedex.data.Resource
import com.alansoft.pokedex.databinding.FragmentDetailBinding
import com.alansoft.pokedex.map.MapsActivity

/**
 * Created by LEE MIN KYU on 2021/05/09
 * Copyright © 2021 Dreamus Company. All rights reserved.
 */
class DetailDialog : DialogFragment() {
    private val args: DetailDialogArgs by navArgs()
    lateinit var binding: FragmentDetailBinding
    private val viewModel: DetailViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_detail, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bindView()
        setSubscribe()
    }

    private fun setSubscribe() {
        viewModel.run {
            results.observe(viewLifecycleOwner, {
                when (it) {
                    is Resource.Loading -> {
                    }
                    is Resource.Success -> {
                        showMap()
                    }
                    else -> {
                        // nothing
                    }
                }
            })
        }
    }

    private fun bindView() {
        with(binding) {
            data = args.item
            dialogBtn.setOnClickListener {
                dismiss()
            }
            map.setOnClickListener {
                this@DetailDialog.viewModel.requestLocation(args.item.id)
            }
        }
    }

    private fun showMap() {
        val intent = Intent(activity, MapsActivity::class.java)
        context?.let {
            startActivities(it, arrayOf(intent))
        }
        dismiss()
    }
}