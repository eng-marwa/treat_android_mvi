package com.treat.customer.presentation.main.search

import androidx.lifecycle.ViewModel
import com.treat.customer.data.model.GenderData
import com.treat.customer.data.model.ServiceTypeData

class SearchViewModel() : ViewModel() {
    var gender: GenderData? = null
    var serviceType: ServiceTypeData? = null

    fun availableTimes() {
    }
}