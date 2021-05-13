package com.alansoft.pokedex.map

import android.os.Bundle
import androidx.annotation.VisibleForTesting
import androidx.appcompat.app.AppCompatActivity
import com.alansoft.pokedex.R
import com.alansoft.pokedex.data.model.Location
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.LatLngBounds
import com.google.android.gms.maps.model.MarkerOptions
import dagger.hilt.android.AndroidEntryPoint

/**
 * Created by LEE MIN KYU on 2021/05/09
 * Copyright © 2021 Dreamus Company. All rights reserved.
 */
@AndroidEntryPoint
class MapsActivity : AppCompatActivity(), OnMapReadyCallback {

    private var locations: List<Location?>? = null
    private var name: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_maps)
        hideActionbar()
        intent?.run {
            locations = getParcelableArrayListExtra("location")
            name = getStringExtra("name")
        }

        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    override fun onMapReady(googleMap: GoogleMap) {
        pokeMarker(googleMap)
    }

    private fun pokeMarker(map: GoogleMap) {
        val australiaBounds = LatLngBounds.builder()

        map.setMinZoomPreference(6.0f)
        map.setMaxZoomPreference(14.0f)

        locations?.forEach { location ->
            location?.let {
                val lat = it.lat?.toDouble() ?: return@let
                val lan = it.lng?.toDouble() ?: return@let

                val latlng = LatLng(lat, lan)
                australiaBounds.include(latlng)
                val marker = MarkerOptions().position(latlng).title(name)
                map.addMarker(marker)
            }
        }
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(australiaBounds.build().center, 10f))
    }

    private fun hideActionbar() {
        supportActionBar?.hide()
    }
}