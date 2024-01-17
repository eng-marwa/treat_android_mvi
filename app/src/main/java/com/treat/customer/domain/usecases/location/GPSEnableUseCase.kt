package com.treat.customer.domain.usecases.location

import android.content.Context
import android.location.LocationManager

class GPSEnableUseCase() {
    fun isGpsEnabled(context: Context): Boolean {
        val locationManager = context.getSystemService(Context.LOCATION_SERVICE) as LocationManager
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
    }

}