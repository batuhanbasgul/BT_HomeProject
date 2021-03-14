package com.example.bt_homeproject.db.service

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.location.*
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.bt_homeproject.db.data_model.Gps
import com.example.bt_homeproject.utils.Constants.gpsProvider


object GpsUtils : LocationListener{
    private lateinit var locationManager: LocationManager
    private lateinit var location : Location
    private var gps: Gps? = null


    fun getCoordinates(context: Context): Gps? {
        val activity: Activity = context as Activity
        if(checkLocationServicePermission(context)){
            locationManager = activity.getSystemService(Context.LOCATION_SERVICE) as LocationManager
            location = locationManager.getLastKnownLocation(gpsProvider)!!
            onLocationChanged(location)
        }else{
            gps=null
        }
        return gps
    }

    override fun onLocationChanged(location: Location) {
        gps = Gps(location.longitude,location.latitude,location.altitude)
    }

    private fun checkLocationServicePermission(context: Context):Boolean{
        val activity: Activity = context as Activity
        return if(ContextCompat.checkSelfPermission(activity, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED){
            true
        }else{
            ActivityCompat.requestPermissions(activity, arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), 100)
            false
        }
    }
}