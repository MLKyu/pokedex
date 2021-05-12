package com.alansoft.pokedex.map

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.alansoft.pokedex.R
import com.alansoft.pokedex.data.model.Location
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions

/**
 * Created by LEE MIN KYU on 2021/05/09
 * Copyright © 2021 Dreamus Company. All rights reserved.
 */
class MapsActivity : AppCompatActivity(), OnMapReadyCallback {


    private var locations: List<Location?>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_maps)
        locations = intent?.getParcelableArrayListExtra("location")

        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)


    }

    override fun onMapReady(googleMap: GoogleMap) {
        this.pokeMarker(googleMap)
    }

    private fun pokeMarker(map: GoogleMap) {
        locations?.forEachIndexed { index, location ->
            location?.let {
                val lat = it.lat?.toDouble() ?: return@let
                val lan = it.lng?.toDouble() ?: return@let

                val latlng = LatLng(lat, lan)
                val marker = MarkerOptions().position(latlng).title(latlng.toString())
                map.addMarker(marker)

                if (index == 0) {
                    map.moveCamera(CameraUpdateFactory.newLatLng(latlng))
                }
            }
        }

    }
}