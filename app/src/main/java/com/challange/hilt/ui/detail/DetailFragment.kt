package com.challange.hilt.ui.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.challange.hilt.R
import com.challange.hilt.ui.models.EarthQuakesUiModel
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions


class DetailFragment : SupportMapFragment(), OnMapReadyCallback {
    private lateinit var mMap: GoogleMap

    private val quakesUiModel: EarthQuakesUiModel by lazy { arguments?.getParcelable("quake") as EarthQuakesUiModel }

    override fun onCreateView(p0: LayoutInflater, p1: ViewGroup?, p2: Bundle?): View? {
        (activity as AppCompatActivity?)?.supportActionBar?.setDisplayHomeAsUpEnabled(true)
        return super.onCreateView(p0, p1, p2)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        this.getMapAsync(this)
    }

    override fun onMapReady(map: GoogleMap?) {
        mMap = map as GoogleMap;

        mMap.run {
            val toShow =
                "\n" + requireContext().resources.getString(R.string.eqId) + " " + quakesUiModel.eqid +
                        "\n" + requireContext().resources.getString(R.string.date) + " " + quakesUiModel.datetime +
                        "\n" + requireContext().resources.getString(R.string.latitude) + " " + quakesUiModel.lat +
                        "\n" + requireContext().resources.getString(R.string.longitude) + " " + quakesUiModel.lng +
                        "\n" + requireContext().resources.getString(R.string.magnitude) + " " + quakesUiModel.magnitude +
                        "\n" + requireContext().resources.getString(R.string.depth) + " " + quakesUiModel.depth

            val positionToMark = LatLng(quakesUiModel.lat, quakesUiModel.lng)
            val markerOptions = MarkerOptions()
                .position(positionToMark)
                .title(quakesUiModel.eqid)

            addMarker(markerOptions)
            moveCamera(CameraUpdateFactory.newLatLng(positionToMark))
            setOnMarkerClickListener {
                Toast.makeText(requireContext(), toShow, Toast.LENGTH_LONG).show()
                true
            }
            val cameraPosition =
                CameraPosition.Builder().target(LatLng(quakesUiModel.lat, quakesUiModel.lng))
                    .zoom(4.0f).build()
            val cameraUpdate = CameraUpdateFactory.newCameraPosition(cameraPosition)
            moveCamera(cameraUpdate)
        }
    }
}
