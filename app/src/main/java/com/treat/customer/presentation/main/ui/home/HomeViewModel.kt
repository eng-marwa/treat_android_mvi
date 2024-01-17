package com.treat.customer.presentation.main.ui.home

import androidx.lifecycle.viewModelScope
import com.treat.customer.base.BaseViewModel
import com.treat.customer.data.datasource.remote.api.NetworkStatus
import com.treat.customer.data.model.BranchesResponse
import com.treat.customer.data.model.GenderData
import com.treat.customer.data.model.GenderResponse
import com.treat.customer.data.model.HomeSliderResponse
import com.treat.customer.data.model.ServiceCategoriesData
import com.treat.customer.data.model.ServiceCategoriesResponse
import com.treat.customer.data.model.ServiceTypeData
import com.treat.customer.data.model.ServiceTypesResponse
import com.treat.customer.data.model.SpecificServicesResponse
import com.treat.customer.domain.usecases.home.GetAllServiceCategoriesUseCase
import com.treat.customer.domain.usecases.home.GetBranchesByServiceType
import com.treat.customer.domain.usecases.home.GetGenderByServiceTypeUseCase
import com.treat.customer.domain.usecases.home.GetHomeSliderUseCase
import com.treat.customer.domain.usecases.home.GetServiceCategoriesUseCase
import com.treat.customer.domain.usecases.home.GetServiceTypesUseCase
import com.treat.customer.domain.usecases.home.GetSpecificServicesUseCase
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class HomeViewModel(
    private val getHomeSliderUseCase: GetHomeSliderUseCase,
    private val getServiceTypesUseCase: GetServiceTypesUseCase,
    private val getServiceCategoriesUseCase: GetServiceCategoriesUseCase,
    private val getSpecificServicesUseCase: GetSpecificServicesUseCase,
    private val getAllServiceCategoriesUseCase: GetAllServiceCategoriesUseCase,
    private val getBranchesUseCase: GetBranchesByServiceType,
    private val getGenderByServiceTypeUseCase: GetGenderByServiceTypeUseCase
) : BaseViewModel() {
    var genderId: String? = null
    var serviceType: ServiceTypeData? = null
    val homeSliderStatus = MutableStateFlow<ViewState<HomeSliderResponse>>(ViewState.Empty())
    val serviceTypeStatus = MutableStateFlow<ViewState<ServiceTypesResponse>>(ViewState.Empty())
    val specificServicesStatus =
        MutableStateFlow<ViewState<SpecificServicesResponse>>(ViewState.Empty())
    val serviceCategoriesStatus =
        MutableStateFlow<ViewState<ServiceCategoriesResponse>>(ViewState.Empty())
    val allCategoriesStatus =
        MutableStateFlow<ViewState<ServiceCategoriesResponse>>(ViewState.Empty())
    val genderByService =
        MutableStateFlow<ViewState<GenderResponse>>(ViewState.Empty())

    val branches =
        MutableStateFlow<ViewState<BranchesResponse>>(ViewState.Empty())

    fun getHomeSlider() {
        viewModelScope.launch {
            getHomeSliderUseCase().collect {
                val state = when (it.status) {
                    is NetworkStatus.Success -> ViewState.Loaded(data = it.payload?.data!!)
                    is NetworkStatus.Failure -> ViewState.Failed(it.error)
                    else -> ViewState.Empty()
                }
                homeSliderStatus.value = state
            }
        }
    }

    fun getServiceTypes() {
        viewModelScope.launch {
            getServiceTypesUseCase().collect {
                val state = when (it.status) {
                    is NetworkStatus.Success -> ViewState.Loaded(data = it.payload?.data!!)
                    is NetworkStatus.Failure -> ViewState.Failed(it.error)
                    else -> ViewState.Empty()
                }
                serviceTypeStatus.value = state
            }
        }
    }

    fun getServiceCategories(selectedService: String) {
        viewModelScope.launch {
            getServiceCategoriesUseCase.invoke(selectedService).collect {
                val state = when (it.status) {
                    is NetworkStatus.Success -> ViewState.Loaded(data = it.payload?.data!!)
                    is NetworkStatus.Failure -> ViewState.Failed(it.error)
                    else -> ViewState.Empty()
                }
                serviceCategoriesStatus.value = state
            }
        }
    }

    fun getSpecificService(serviceCategoriesData: ServiceCategoriesData, gender: GenderData) {
        viewModelScope.launch {
            getSpecificServicesUseCase.invoke(serviceCategoriesData.id!!, gender?.id!!).collect {
                val state = when (it.status) {
                    is NetworkStatus.Success -> ViewState.Loaded(data = it.payload?.data!!)
                    is NetworkStatus.Failure -> ViewState.Failed(it.error)
                    else -> ViewState.Empty()
                }
                specificServicesStatus.value = state
            }
        }
    }

    fun getAllCategories() {
        viewModelScope.launch {
            getAllServiceCategoriesUseCase.invoke().collect {
                val state = when (it.status) {
                    is NetworkStatus.Success -> ViewState.Loaded(data = it.payload?.data!!)
                    is NetworkStatus.Failure -> ViewState.Failed(it.error)
                    else -> ViewState.Empty()
                }
                allCategoriesStatus.value = state
            }
        }
    }

    fun getBranches(
        serviceCategoryIds: ArrayList<String>?,
        serviceType: String?,
        gender: String?,
        lat: String?,
        lng: String?,
        date: String?,
    ) {
        viewModelScope.launch {
            getBranchesUseCase.invoke(serviceCategoryIds, serviceType, gender, lat, lng,date).collect {
                val state = when (it.status) {
                    is NetworkStatus.Success -> ViewState.Loaded(data = it.payload?.data!!)
                    is NetworkStatus.Failure -> ViewState.Failed(it.error)
                    else -> ViewState.Empty()
                }
                branches.value = state
            }
        }
    }

    fun getGenderByService(serviceTypeData: String) {
        viewModelScope.launch {
            getGenderByServiceTypeUseCase.invoke(serviceTypeData).collect {
                val state = when (it.status) {
                    is NetworkStatus.Success -> ViewState.Loaded(data = it.payload?.data!!)
                    is NetworkStatus.Failure -> ViewState.Failed(it.error)
                    else -> ViewState.Empty()
                }
                genderByService.value = state
            }
        }
    }

    fun callGenderAndServiceCategorySync(serviceTypeData: ServiceTypeData) {
        viewModelScope.launch {
            val call1 = async { getGenderByService(serviceTypeData.id!!) }
            val call2 = async { getServiceCategories(serviceTypeData.id!!) }
            try {
                call1.await()
                call2.await()
            } catch (ex: Exception) {
                ex.printStackTrace()
            }
        }
    }


}