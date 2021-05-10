package com.alansoft.pokedex.ui.dialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.DialogFragment
import androidx.navigation.fragment.navArgs
import com.alansoft.pokedex.R
import com.alansoft.pokedex.databinding.FragmentDetailBinding
import com.alansoft.pokedex.util.toast

/**
 * Created by LEE MIN KYU on 2021/05/09
 * Copyright © 2021 Dreamus Company. All rights reserved.
 */
class DetailDialog : DialogFragment() {
    private val args: DetailDialogArgs by navArgs()
    lateinit var binding: FragmentDetailBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_detail, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        bindView()
        return binding.root
    }

    private fun bindView() {
        toast(args.bundle.getLong("id").toString())
        with(binding) {
            name.text = args.bundle.getString("name")
            dialogBtn.setOnClickListener {
                dismiss()
            }
        }
    }
}