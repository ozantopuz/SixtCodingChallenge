package com.ozantopuz.sixtcodingchallenge.ui.map

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.LatLngBounds
import com.google.android.gms.maps.model.MarkerOptions
import com.ozantopuz.sixtcodingchallenge.MainViewModel
import com.ozantopuz.sixtcodingchallenge.R
import com.ozantopuz.sixtcodingchallenge.data.entity.Car
import com.ozantopuz.sixtcodingchallenge.databinding.FragmentMapBinding
import com.ozantopuz.sixtcodingchallenge.util.delegate.viewBinding
import com.ozantopuz.sixtcodingchallenge.util.extension.toast
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MapFragment : Fragment(R.layout.fragment_map), OnMapReadyCallback {

    private val binding: FragmentMapBinding by viewBinding()
    private val viewModel: MainViewModel by lazy {
        ViewModelProvider(requireActivity()).get(
            MainViewModel::class.java
        )
    }

    private lateinit var googleMap: GoogleMap
    private val mapFragment: SupportMapFragment by lazy {
        childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
    }

    private val cars: ArrayList<Car> = arrayListOf()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setHasOptionsMenu(true)

        with(viewModel) {
            carsLiveData.observe(viewLifecycleOwner, { list ->
                cars.addAll(list)
                binding.textView.isVisible = cars.isNullOrEmpty()
                binding.relativeLayout.isVisible = !cars.isNullOrEmpty()
                if (!cars.isNullOrEmpty()) mapFragment.getMapAsync(this@MapFragment)
            })
            errorLiveData.observe(viewLifecycleOwner, { message -> context.toast(message) })
        }
    }

    override fun onMapReady(map: GoogleMap) {
        googleMap = map

        val latLngBoundsBuilder: LatLngBounds.Builder = LatLngBounds.Builder()

        cars.forEach { car ->
            if (car.latitude != null && car.longitude != null) {
                val name = "${car.name?.uppercase()} | ${car.modelName?.uppercase()}"
                val position = LatLng(car.latitude, car.longitude)
                latLngBoundsBuilder.include(position)

                googleMap.addMarker(
                    MarkerOptions()
                        .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ORANGE))
                        .position(position)
                        .title(name)
                )
            }
        }

        val bounds: LatLngBounds = latLngBoundsBuilder.build()
        googleMap.animateCamera(CameraUpdateFactory.newLatLngBounds(bounds, LAT_LNG_BOUNDS_PADDING))
    }

    companion object {
        private const val LAT_LNG_BOUNDS_PADDING = 250
    }
}