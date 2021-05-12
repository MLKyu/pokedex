package com.alansoft.pokedex.map

import android.os.Bundle
import android.util.Log
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

    private lateinit var mMap: GoogleMap

    private var locations: List<Location?>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_maps)
        savedInstanceState?.getParcelableArrayList<Location?>("location")?.let {
            locations = it
        }
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)


    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        this.pokeMarker()
    }

    fun pokeMarker() {
        locations?.forEachIndexed { index, location ->
            location?.let {
                val lat = it.lat?.toDouble() ?: return@let
                val lan = it.lan?.toDouble() ?: return@let
                val latlng = LatLng(lat, lan)
                Log.d("latlnglatlnglatlng", latlng.toString())
                val marker = MarkerOptions().position(latlng).title("")
                mMap.addMarker(marker)

                if (index == 0) {
                    mMap.moveCamera(CameraUpdateFactory.newLatLng(latlng))
                }
            }
        }

    }
}