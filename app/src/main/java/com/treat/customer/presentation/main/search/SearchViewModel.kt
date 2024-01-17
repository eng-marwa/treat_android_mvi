package com.treat.customer.presentation.main.search

import androidx.lifecycle.ViewModel
import com.treat.customer.data.model.GenderData
import com.treat.customer.data.model.ServiceCategoriesData
import com.treat.customer.data.model.ServiceTypeData
import java.util.ArrayList

class SearchViewModel() : ViewModel() {
    var selectedItems: MutableList<ServiceCategoriesData> = mutableListOf()
    var gender: GenderData? = null
    var serviceType: ServiceTypeData? = null

    fun availableTimes() {
    }
}