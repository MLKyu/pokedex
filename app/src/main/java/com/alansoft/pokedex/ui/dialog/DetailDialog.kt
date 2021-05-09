package com.alansoft.pokedex.ui.dialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.DialogFragment
import com.alansoft.pokedex.R
import com.alansoft.pokedex.databinding.FragmentDetailBinding

/**
 * Created by LEE MIN KYU on 2021/05/09
 * Copyright © 2021 Dreamus Company. All rights reserved.
 */
class DetailDialog : DialogFragment() {
    protected lateinit var binding: FragmentDetailBinding

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
        // TODO : Ui setup button1.setOnClickListener { setNavResult("Ok") dismiss() } button2.setOnClickListener { setNavResult("Cancel") dismiss() } } private fun setNavResult(answer: String) { // TODO : Reteurn selected result to the previous navigation entry }

    }
}