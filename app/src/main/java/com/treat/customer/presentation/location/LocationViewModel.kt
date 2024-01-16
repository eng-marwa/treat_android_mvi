package com.treat.customer.presentation.location

import android.content.Context
import android.location.LocationManager
import androidx.lifecycle.viewModelScope
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.LocationSettingsRequest
import com.google.android.gms.location.Priority
import com.treat.customer.base.BaseViewModel
import com.treat.customer.data.datasource.remote.api.NetworkStatus
import com.treat.customer.data.model.GenderResponse
import com.treat.customer.data.model.ProfileResponse
import com.treat.customer.domain.usecases.auth.UpdateLocationUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class LocationViewModel(private val updateLocationUseCase: UpdateLocationUseCase) :
    BaseViewModel() {
    fun isGpsEnabled(context: Context): Boolean {
        val locationManager = context.getSystemService(Context.LOCATION_SERVICE) as LocationManager
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
    }

    fun checkLocationSettings(context: Context) {
        val locationRequest =
            LocationRequest.Builder(Priority.PRIORITY_HIGH_ACCURACY, 10000).apply {
            }.build()

        val builder = LocationSettingsRequest.Builder()
            .addLocationRequest(locationRequest)

        val client = LocationServices.getSettingsClient(context)
        val task = client.checkLocationSettings(builder.build())

    }

    val location =
        MutableStateFlow<ViewState<ProfileResponse>>(ViewState.Empty())

    fun updateLocation(latitude: Double, longitude: Double) {
        viewModelScope.launch {
            updateLocationUseCase(latitude, longitude).collect {
                val state = when (it.status) {
                    is NetworkStatus.Success -> BaseViewModel.ViewState.Loaded(data = it.payload?.data!!)
                    is NetworkStatus.Failure -> BaseViewModel.ViewState.Failed(it.error)
                    else -> BaseViewModel.ViewState.Empty()

                }
                location.value = state
            }
        }

    }


}
